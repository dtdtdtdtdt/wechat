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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.Article;
import com.wx.common.bean.Mpnews;
import com.wx.common.bean.News_item;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;
import com.wx.source.biz.MpnewsBiz;
import com.wx.source.biz.SourceBiz;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class SourceController {

	@Resource(name="sourceBizImpl")
	private SourceBiz sourceBiz;
	
	@Resource(name="mpnewsBizImpl")
	private MpnewsBiz mpnewsBiz;
	
	@Resource(name ="accessTokenZpBizImpl")
	private AccessTokenZpBiz atzb;
	
	
	/**
	 * 将素材消息保存到数据库、保存到微信服务器
	 * @param article
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/back/addArticle.action")
	public JsonModel saveArticle(News_item news_item,Mpnews mpnews,Article article,HttpServletRequest request) throws ParseException, IOException, java.text.ParseException{
		JsonModel jsonModel =new JsonModel();
		JSONObject returnMediaId=null;
		try {	
			
			String content =request.getParameter("content");
			String c =replaceUrl(content,0,request);
			article.setContent(c);
			
			//保存到微信
			String source =JSONObject.fromObject(sourceBiz.initSource(article)).toString();		
			returnMediaId=sourceBiz.createSource(source);
			
			if(returnMediaId!=null){
				//存储返回的media_id
				mpnews.setMedia_id((returnMediaId).toString());				
				mpnewsBiz.addMpnews(mpnews);
				
				//根据returnMediaId获取微信里面的永久素材
				String access_token = GetAccessToken.getAT(atzb);
				String url = WeixinUtil.GET_MEDIA_URL.replace("ACCESS_TOKEN", access_token);
				JSONObject jsonObject =new JSONObject();
				jsonObject=WeixinUtil.doPostStr(url,String.valueOf(returnMediaId));
				
				//将微信返回的永久素材存储到数据库
				String news_items =jsonObject.getString("news_item").toString();
				news_items=news_items.replace("[", "").replace("]", "");
				jsonObject=JSONObject.fromObject(news_items);   //将news_items里的数据解析成jsonObject，然后取出来

				news_item.setTitle(jsonObject.getString("title"));
				news_item.setAuthor(jsonObject.getString("author"));
				news_item.setDigest(jsonObject.getString("digest"));
				news_item.setContent(jsonObject.getString("content"));
				news_item.setContent_source_url(jsonObject.getString("content_source_url"));
				news_item.setThumb_media_id(jsonObject.getString("thumb_media_id"));
				news_item.setShow_cover_pic(jsonObject.getInt("show_cover_pic"));
				news_item.setUrl(jsonObject.getString("url"));
				news_item.setThumb_url(jsonObject.getString("thumb_url"));				

				try {
					//保存到数据库
					sourceBiz.addReturnArticel(news_item);
					jsonModel.setCode(1);
				} catch (Exception e) {
					e.printStackTrace();
					jsonModel.setCode(0);
					jsonModel.setMsg(e.getMessage());
				}
			}else{
				jsonModel.setCode(0);
				jsonModel.setMsg(String.valueOf(returnMediaId));
			}
		} catch (Exception e) {			
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		} 			
		return jsonModel;
	}
	
	/**
	 * 将素材消息保存到数据库、保存到微信服务器，并群发
	 * @param news_item
	 * @param article
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/back/addAndSendArticle.action")
	public JsonModel saveAndSendArticle(News_item news_item,Mpnews mpnews,Article article,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException, IOException, java.text.ParseException{		
		JsonModel jsonModel =new JsonModel();
		JSONObject returnMediaId=null;
		try {	
			
			String content =request.getParameter("content");
			String c =replaceUrl(content,0,request);
			article.setContent(c);
			
			//保存到微信
			String source =JSONObject.fromObject(sourceBiz.initSource(article)).toString();		
			returnMediaId=sourceBiz.createSource(source);
			
			if(returnMediaId!=null){
				//存储返回的media_id
				mpnews.setMedia_id((returnMediaId).toString());				
				mpnewsBiz.addMpnews(mpnews);
				
				//根据returnMediaId获取微信里面的永久素材
				String access_token = GetAccessToken.getAT(atzb);
				String url = WeixinUtil.GET_MEDIA_URL.replace("ACCESS_TOKEN", access_token);
				JSONObject jsonObject =new JSONObject();
				jsonObject=WeixinUtil.doPostStr(url,String.valueOf(returnMediaId));
				
				//将微信返回的永久素材存储到数据库
				String news_items =jsonObject.getString("news_item").toString();
				news_items=news_items.replace("[", "").replace("]", "");
				jsonObject=JSONObject.fromObject(news_items);   //将news_items里的数据解析成jsonObject，然后取出来

				news_item.setTitle(jsonObject.getString("title"));
				news_item.setAuthor(jsonObject.getString("author"));
				news_item.setDigest(jsonObject.getString("digest"));
				news_item.setContent(jsonObject.getString("content"));
				news_item.setContent_source_url(jsonObject.getString("content_source_url"));
				news_item.setThumb_media_id(jsonObject.getString("thumb_media_id"));
				news_item.setShow_cover_pic(jsonObject.getInt("show_cover_pic"));
				news_item.setUrl(jsonObject.getString("url"));
				news_item.setThumb_url(jsonObject.getString("thumb_url"));				
				
				Map<String,String> map = new HashMap<String,String>();				
				map.put("title",jsonObject.getString("title"));
				map.put("digest",jsonObject.getString("digest"));
				map.put("thumb_url",jsonObject.getString("thumb_url"));	
				map.put("media_id", returnMediaId.toString());
				session.setAttribute("map", map);
				
				try {
					//保存到数据库
					sourceBiz.addReturnArticel(news_item);
					jsonModel.setCode(1);
				} catch (Exception e) {
					e.printStackTrace();
					jsonModel.setCode(0);
					jsonModel.setMsg(e.getMessage());
				}
			}else{
				jsonModel.setCode(0);
				jsonModel.setMsg(String.valueOf(returnMediaId));
			}
		} catch (Exception e) {			
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		} 			
		return jsonModel;
	}
	
	//替换content中图片的url 换成微信返回的url
	private String replaceUrl(String content,int index,HttpServletRequest request) throws ParseException, IOException, java.text.ParseException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{		
	 
		int srcStart = content.indexOf("src=\"",index); //获取src出现的位置
	    int srcEnd = content.indexOf("\"",srcStart+32);
	    srcStart = srcStart + 5;
	    String src = content.substring(srcStart,srcEnd); //获取图片路径

		String access_token = GetAccessToken.getAT(atzb);	   
		//执行上传图片方法
	    String newPath = uploadPic(src,access_token,request);	   
	    //替换字符串中该图片路径
	    content = content.replace(src, newPath);
	     //查看字符串下方是否还有img标签
	   int sfhyImg = content.indexOf("<img",srcEnd);
	   try {
		   if(sfhyImg==-1){
			   return content;
		   }else{
			   return replaceUrl(content, srcEnd,request);
		   }
		} catch (Exception e) {
			e.printStackTrace();
			return "上传图片失败"+e.getMessage();
		}
	}
	
	//将图文消息中的图片上传到微信  获取微信中该图片的URL
	public static String uploadPic(String src, String accessToken,HttpServletRequest request)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		
		String tomcatdir =request.getRealPath("/");
		File tomcatFile =new File(tomcatdir);
		File webapppath =tomcatFile.getParentFile();
		src =src.substring(8);
		String filepath =webapppath+src;
		File file = new File(filepath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		
		String url = WeixinUtil.PiCTURE_URL.replace("ACCESS_TOKEN", accessToken);

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
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
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
		String newUrl = jsonObj.getString("url");
		return newUrl;
	}
	
	@RequestMapping("/back/getSourceList.action")
	public void getSourceList(HttpSession session,HttpServletResponse resp) throws ParseException, IOException{
	
		String sourceList =JSONObject.fromObject(sourceBiz.initSourceList()).toString();
		JSONObject jsonObject =sourceBiz.getSourceList(sourceList);

		String item =jsonObject.getString("item").toString();
		item =item.substring(1, item.length()-1);
		String[] itemList =item.split("\\}\\,\\{");
		
		List<Article> Sourcelist =new ArrayList<Article>();
		for(int i=0;i<itemList.length;i++){
			if(i==0){
				itemList[i] =itemList[i]+"}";
			}else if(i==itemList.length-1){
				itemList[i] ="{"+itemList[i];
			}else{
				itemList[i] ="{"+itemList[i]+"}";
			}	
			
			JSONObject obj =JSONObject.fromObject(itemList[i]);
			String news_item =obj.getJSONObject("content").getString("news_item");
			news_item =news_item.substring(1,news_item.length()-1);
			Article article =new Article();
			jsonObject=JSONObject.fromObject(news_item);	
			article.setTitle(jsonObject.getString("title"));
			article.setAuthor(jsonObject.getString("author"));
			article.setContent(jsonObject.getString("content"));
			article.setDigest(jsonObject.getString("digest"));	
			Sourcelist.add(article);
		}
		int count = Sourcelist.size();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", Sourcelist);

		Gson gson = new Gson();
		String jsonstr = gson.toJson(map);
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter out = resp.getWriter();			
		out.println(jsonstr);
		out.flush();
		out.close();	
		
	}
	
/*	@RequestMapping("/back/getSourceList2.action")
	public JsonModel getSourceList2(HttpSession session,HttpServletResponse resp) throws ParseException, IOException{
		JsonModel jsonModel =new JsonModel();
		
		String sourceList =JSONObject.fromObject(sourceBiz.initSourceList()).toString();
		JSONObject jsonObject =sourceBiz.getSourceList(sourceList);

		String item =jsonObject.getString("item").toString();
		item =item.substring(1, item.length()-1);
		String[] itemList =item.split("\\}\\,\\{");
		
		List<Article> Sourcelist =new ArrayList<Article>();
		for(int i=0;i<itemList.length;i++){
			if(i==0){
				itemList[i] =itemList[i]+"}";
			}else if(i==itemList.length-1){
				itemList[i] ="{"+itemList[i];
			}else{
				itemList[i] ="{"+itemList[i]+"}";
			}	
			
			JSONObject obj =JSONObject.fromObject(itemList[i]);
			String news_item =obj.getJSONObject("content").getString("news_item");
			news_item =news_item.substring(1,news_item.length()-1);
			Article article =new Article();
			jsonObject=JSONObject.fromObject(news_item);	
			article.setTitle(jsonObject.getString("title"));
			article.setAuthor(jsonObject.getString("author"));
			article.setContent(jsonObject.getString("content"));
			article.setDigest(jsonObject.getString("digest"));	
			Sourcelist.add(article);
		}		
		session.setAttribute("Sourcelist", Sourcelist);
		jsonModel.setCode(1);
		
		return jsonModel;	
	}*/
}
