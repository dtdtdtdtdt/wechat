package com.wx.common.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.Admin;
import com.wx.common.biz.AdminBiz;
import com.wx.common.web.model.JsonModel;

@RestController
public class AdminController {
	
	@Resource(name="adminBizImpl")
	private AdminBiz adminBiz;
	
	@RequestMapping("/adminLogin.action")
	public JsonModel login(Admin admin,HttpServletRequest request,HttpSession session){
		JsonModel jsonModel=new JsonModel();
		String zccode=request.getParameter("zccode");
		String rand=session.getAttribute("rand").toString();
		if(!rand.equals(zccode)){
			jsonModel.setCode(0);
			jsonModel.setMsg("验证码错误!");
		}else{
			admin=adminBiz.login(admin);
			if(admin!=null){
				session.setAttribute("admin", admin);
				jsonModel.setCode(1);
			}else{
				jsonModel.setCode(0);
				jsonModel.setMsg("用户名或密码错误!");
			}
		}
		return jsonModel;
	}
}
