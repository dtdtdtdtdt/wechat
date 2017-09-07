package com.wx.shop.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.wx.common.bean.KeyReply;
import com.wx.common.bean.WxShop;
import com.wx.common.utils.FileUpload;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;
import com.wx.shop.biz.WxShopBiz;

@RestController
public class FoodController {

	@Resource(name = "wxShopBizImpl")
	private WxShopBiz wxShopBiz;

	@RequestMapping("/back/findAllWxShop.action")
	public void findAllFood(HttpServletResponse response, WxShop wxShop,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows) {

		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
		int pagesize = Integer.valueOf(rows);
		wxShop.setStart(start);
		wxShop.setPagesize(pagesize);
		List<WxShop> list = wxShopBiz.findAllWxShop(wxShop);
		Gson gson = new Gson();
		// int count=list.size();
		int count = list.size();
		// easyui要求的格式
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		String jsonstr = gson.toJson(map);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			System.out.println("响应内容发送失败!");
			e.printStackTrace();
		}
		out.println(jsonstr);
		out.flush();
		out.close();
	}

	@RequestMapping("/back/loadWxShop.action")
	public JsonModel loadWxShop(WxShop wxShop, HttpSession session) {
		JsonModel jm = new JsonModel();
		try {
			wxShop = wxShopBiz.findWxShopById(wxShop);
			session.setAttribute("WxShop", wxShop);
		} catch (Exception e) {
			jm.setCode(0);
		}
		return jm;
	}

	@RequestMapping("/back/addWxShop.action")
	public ModelAndView addWxShop(@RequestParam("pic") MultipartFile[] multipartfiles,WxShop wxShop, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		ModelAndView mav=new ModelAndView();
		String[] pic=new String[3];
		if (null != multipartfiles && multipartfiles.length > 0) {
			// 遍历并保存文件
			for (int i = 0; i < multipartfiles.length; i++) {
				MultipartFile file = multipartfiles[i];
				// 截取文件后缀名
				int t = file.getOriginalFilename().lastIndexOf(".");
				String last = file.getOriginalFilename().substring(t); // .png
				if (file != null) {

					String fileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());

					// 取tomcat路径
					Calendar c = Calendar.getInstance();
					String tomcatdir = request.getRealPath("/");
					File tomcatFile = new File(tomcatdir);
					File webapppath = tomcatFile.getParentFile();

					// 保存文件的目录
					File picpath = new File(webapppath, "wxpic" + File.separator + c.get(Calendar.YEAR) + File.separator
							+ (c.get(Calendar.MONTH) + 1) + File.separator + fileName + last);
					//存入数据库路径
					String picPath="../wxpic" + File.separator + c.get(Calendar.YEAR) + File.separator
							+ (c.get(Calendar.MONTH) + 1) + File.separator + fileName + last;
					File filePath = new File(picpath.toString().substring(0, picpath.toString().lastIndexOf("\\")));

					if (filePath.exists() == false) {
						filePath.mkdirs();
					}
					// 上传 到指定磁盘
					file.transferTo(picpath);
					pic[i] = picPath.replace("\\", "/");
				}
			}
		}
		wxShop.setCover(pic[0]);
		wxShop.setDetaila(pic[1]);
		wxShop.setDetailb(pic[2]);
		try {
			wxShopBiz.addWxShop(wxShop);
			mav.setViewName("../../back/manager/shop/manageFood");
		} catch (Exception e) {
			System.out.println("添加失败!"+e.getMessage());
		}
		return mav;
	}
	
	@RequestMapping("/back/deleteWxShop.action")
	public JsonModel deleteWxShop(WxShop wxShop){
		JsonModel jm=new JsonModel();
		try {
			wxShopBiz.deleteWxShop(wxShop);
			jm.setCode(1);
		} catch (Exception e) {
			jm.setCode(0);
		}
		return jm;
	}

	@RequestMapping("/back/updateWxShop.action")
	public JsonModel updateWxShop(WxShop wxShop, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		JsonModel jm=new JsonModel();
		try {
			wxShopBiz.updateWxShop(wxShop);
			jm.setCode(1);
		} catch (Exception e) {
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
}
