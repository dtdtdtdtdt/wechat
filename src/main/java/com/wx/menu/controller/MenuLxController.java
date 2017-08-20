package com.wx.menu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.MenuLx;
import com.wx.menu.biz.MenuBiz;

@RestController
public class MenuLxController {

	@Resource(name="menuBizImpl")
	private MenuBiz menuBiz;
	
	@RequestMapping("/back/findMenu.action")
	public void showUserList(HttpServletResponse response){
		List<MenuLx> list=menuBiz.findAllMenu();
		Gson gson=new Gson();
		int count=list.size();
		//int count=userBiz.findUserCount(userLx);
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
}
