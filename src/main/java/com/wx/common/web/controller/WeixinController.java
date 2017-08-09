package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.utils.CheckUtil;
import com.wx.common.utils.XmlAndMap;

@RestController
public class WeixinController {
	
	@RequestMapping(value="/weixin.action",method=RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		// 确认是来自微信服务器
		String signature = request.getParameter("signature"); // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = request.getParameter("timestamp"); // 时间戳
		String nonce = request.getParameter("nonce"); // 随机数
		String echostr = request.getParameter("echostr"); // 随机字符串

		// 如果请求来自微信则返回echostr给微信 用来验证成功
		try {
			PrintWriter out = response.getWriter();
			if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("无返回数据!");
		}
	}
	
	@RequestMapping(value="/weixin.action",method=RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("设置请求字符集失败!");
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		// 将传过来xml转换为map
		Map<String, String> map = XmlAndMap.xmlToMap(request);
	}
}
