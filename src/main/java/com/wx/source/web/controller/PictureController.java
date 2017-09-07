package com.wx.source.web.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.google.gson.Gson;
import com.wx.common.bean.Picture;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;
import com.wx.source.biz.PictureBiz;

import net.sf.json.JSONObject;

@RestController
public class PictureController {

	@Resource(name ="pictureBizImpl")
	private PictureBiz pictureBiz;

	@Resource(name ="accessTokenZpBizImpl")
	private AccessTokenZpBiz atzb;

	/**
	 * 查找图片素材
	 * @param picture
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/back/findPicture.action")
	public void findAllPicture(Picture picture, HttpServletRequest req,HttpSession session, HttpServletResponse resp) throws IOException {
		List<Picture> list = pictureBiz.findPicture(picture);
		
		session.setAttribute("pictureList",list);
		
		int count = list.size();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);

		Gson gson = new Gson();
		String jsonstr = gson.toJson(map);
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		out.println(jsonstr);
		out.flush();
		out.close();
	}

	/**
	 * 删除图片素材
	 * 
	 * @param picture
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/back/delPicture.action")
	public JsonModel delPicture(Picture picture, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonModel jsonModel = new JsonModel();
		try {
			pictureBiz.delPicture(picture);
			jsonModel.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}

	// 获取上传图片到Tomcat   再调用thumbUpload方法上传到微信   获取微信返回的 mediaId
	@RequestMapping("/back/addPicture.action") // 注解@RequestParam前端file中设置的值
												// 详见http://825635381.iteye.com/blog/2196911
	public JsonModel keyMsgToReplyImg(Picture picture, HttpServletRequest request, HttpServletResponse resp,
			@RequestParam("file") CommonsMultipartFile file2) throws Exception {
		JsonModel jm = new JsonModel();

		// 媒体id设置为全局变量
		String mediaId = null;

		String msgType = "thumb";

	//	long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				// 截取文件后缀名
				int t = file.getOriginalFilename().lastIndexOf(".");
				String last = ".jpg";
				if (file != null) {
					String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

					// Tomcat中的news项目的路径：C:\tomocat\apache-tomcat-7.0.47\webapps\news
					String tomcatwebroot = request.getServletContext().getRealPath("/"); // news路径
					File newsroot = new File(tomcatwebroot); // 取news的父路径
					// C:\tomocat\apache-tomcat-7.0.47\webapps
					File tomcatRootFile = newsroot.getParentFile();
					String filepath = tomcatRootFile + "/wechat_uploadpics/"; // C:\tomocat\apache-tomcat-7.0.47\webapps/wechat_uploadpics/

					String webUrl = "../wechat_uploadpics/"; // 网页上访问图片的路径

					DateFormat df = new SimpleDateFormat("yyyy/MM/");
					String timeDir = df.format(new Date());
					filepath = filepath + timeDir + fileName + last;

					webUrl = webUrl + timeDir + fileName + last; // webUrl
																// =>../newd_uploadpics/2017/05/

					// 判断文件是否存在 不存在则创建
					File f = new File(filepath);
					if (f.exists() == false) {
						f.mkdirs();
					}

					file.transferTo(new File(filepath));

					// 在上传到微信服务器！
					String access_token = GetAccessToken.getAT(atzb);
					mediaId = thumbUpload(filepath, access_token, msgType);
										
					try {
						picture.setPname(fileName);
						picture.setThumb_media_id(mediaId);
						picture.setWebUrl(webUrl);
						pictureBiz.addPicture(picture);						
						jm.setCode(1);
						jm.setMsg(mediaId);
					} catch (Exception e) {
						e.printStackTrace();
						jm.setCode(0);
						jm.setMsg(e.getMessage());
					}
				}
			}
		}
		return jm;
	}
	
	/**
	 * 上传缩略图到微信
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String thumbUpload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = WeixinUtil.MEDIA_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		String mediaId = jsonObj.getString("media_id");
		return mediaId;
	}
}
