package com.wx.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	/**
	 * 跳转到main.jsp  管理界面
	 * @return
	 */
	@RequestMapping(value="/back/toMain.action",method=RequestMethod.GET)
	public String toMain(){
		return "main";
	}
	
	@RequestMapping(value="/toLogin.action",method=RequestMethod.GET)
	public String toLogin(){
		return "login";
	}
}
