package com.wx.user.web.controller;

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
import com.wx.common.bean.UserLx;
import com.wx.user.biz.UserBiz;

@RestController
public class UserController {
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	@RequestMapping("/back/findUsers.action")
	public void showUserList(HttpServletResponse response){
		//userBiz.getAndSaveUser();
		List<UserLx> list=userBiz.findAllUser();
		Gson gson=new Gson();
		int count=userBiz.findUserCount();
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
