package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.Admin;
import com.wx.common.bean.Role;
import com.wx.common.biz.AdminBiz;
import com.wx.common.web.model.JsonModel;
import com.wx.role.biz.RoleBiz;
/**
 * 
 * @author 刘翔
 *
 */
@RestController
public class AdminController {
	
	@Resource(name="roleBizImpl")
	private RoleBiz roleBiz;
	
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
				List<Role> list=new ArrayList<Role>();
				list=adminBiz.findMenuByRole(admin);
				session.setAttribute("menuList", list);
			}else{
				jsonModel.setCode(0);
				jsonModel.setMsg("用户名或密码错误!");
			}
		}
		return jsonModel;
	}
	
	@RequestMapping("/adminLogout.action")
	public void logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		session.removeAttribute("admin");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	@RequestMapping("/back/findAdmins.action")
	public void findAllAdmins(HttpServletResponse response){
		List<Admin> list=adminBiz.findAllAdmins();
		Gson gson=new Gson();
		int count=list.size();
		//easyui要求的格式
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", count);
		map.put("rows", list);
		String jsonstr=gson.toJson(map);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out=null;
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
	
	@RequestMapping("/back/deleteAdmins.action")
	public JsonModel deleteAdmins(Admin admin){
		JsonModel jsonModel=new JsonModel();
		try {
			adminBiz.deleteAdmins(admin);
			jsonModel.setCode(1);
		} catch (Exception e) {
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}
	
	@RequestMapping("/back/addAdmins.action")
	public JsonModel addAdmins(Admin admin){
		JsonModel jsonModel=new JsonModel();
		try {
			adminBiz.addAdmins(admin);
			jsonModel.setCode(1);
		} catch (Exception e) {
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}
	
	@RequestMapping("/back/updateAdmins.action")
	public JsonModel upadteAdmins(Admin admin,HttpSession session){
		JsonModel jsonModel=new JsonModel();
		try {
			adminBiz.updateAdmins(admin);
			jsonModel.setCode(1);
		} catch (Exception e) {
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}
	
	@RequestMapping("/back/findAdminByAid.action")
	public JsonModel findAdminByAid(Admin admin,HttpSession session){
		JsonModel jsonModel=new JsonModel();
		try {
			admin=adminBiz.findAdminByAid(admin);
			session.setAttribute("upAdmin", admin);
			jsonModel.setCode(1);
		} catch (Exception e) {
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}
	
	@RequestMapping("/back/reloadRole.action")
	public JsonModel reLoadRole(HttpSession session){
		JsonModel jsonModel=new JsonModel();
		try {
			List<Role> list=roleBiz.findAllRole();
			session.setAttribute("roleList", list);
		} catch (Exception e) {
			jsonModel.setCode(0);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}
}
