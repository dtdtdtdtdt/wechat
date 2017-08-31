package com.wx.shop.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.utils.WeixinUtil;


import net.sf.json.JSONObject;



@RestController
public class InitController {
	
	//进入shop首页！
	@RequestMapping(value="/shop/toIndex.action",method = RequestMethod.GET)
	public void init(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//回调网址
		String backUrl ="http://119.23.65.165/wechat/shop/callBack.action";  
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid="+WeixinUtil.APPID+"&redirect_uri="+URLEncoder.encode(backUrl)+
				"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		//重定向     // snsapi_userinfo   snsapi_base
		resp.sendRedirect(url);
	}
	
}
