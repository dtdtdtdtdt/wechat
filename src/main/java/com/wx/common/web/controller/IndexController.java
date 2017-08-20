package com.wx.common.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wx.common.bean.Role;
import com.wx.role.biz.RoleBiz;

@Controller
public class IndexController {

	@Resource(name="roleBizImpl")
	private RoleBiz roleBiz;
	
	/**
	 * 跳转到main.jsp  管理界面
	 * @return
	 */
	@RequestMapping(value="/back/toMain.action",method=RequestMethod.GET)
	public String toMain(HttpSession session){
		List<Role> list=roleBiz.findAllRole();
		session.setAttribute("roleList", list);
		return "main";
	}
	
	@RequestMapping(value="/toLogin.action",method=RequestMethod.GET)
	public String toLogin(){
		return "login";
	}
	
}
