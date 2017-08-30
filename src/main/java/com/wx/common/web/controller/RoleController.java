package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

@RestController
public class RoleController {

	@Resource(name="roleBizImpl")
	private RoleBiz roleBiz;
	
	@Resource(name="adminBizImpl")
	private AdminBiz adminBiz;
	
	/**
	 * 加载所有角色信息
	 * @param response
	 */
	@RequestMapping("/back/findRoles.action")
	public void findAllRoles(HttpServletResponse response,HttpSession session){
		List<Role> list=roleBiz.findAllRole();
		//session.setAttribute("roleList", list);
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
	
	@RequestMapping("/back/addRole.action")
	public JsonModel addRole(String strTitle,String strMenu,Role role){
		JsonModel jm=new JsonModel();
		
		String[] title=strTitle.split(",");
		String[] menu=strMenu.split(",");
		for(int i=0;i<title.length;i++){
			role.setMtitle(title[i]);
			role.setMenu(menu[i]);
			try {
				roleBiz.addRole(role);
				jm.setCode(1);
			} catch (Exception e) {
				jm.setCode(0);
				jm.setMsg(e.getMessage());
				break;
			}
		}
		return jm;
	}
	
	@RequestMapping("/back/deleteRole.action")
	public JsonModel deleteByRole(Role role){
		JsonModel jm=new JsonModel();
		try {
			roleBiz.deleteRole(role);
			jm.setCode(1);
		} catch (Exception e) {
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
	
	@RequestMapping("/back/showMenu.action")
	public JsonModel findMenuByrole(Role role,HttpSession session){
		JsonModel jm=new JsonModel();
		List<Role> list=new ArrayList<Role>();
		Admin admin=new Admin();
		admin.setRole(role.getRole());
		try {
			list=adminBiz.findMenuByRole(admin);
			session.setAttribute("preMenuList", list);
		} catch (Exception e) {
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}
}
