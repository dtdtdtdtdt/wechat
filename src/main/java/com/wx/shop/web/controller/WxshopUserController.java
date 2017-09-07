package com.wx.shop.web.controller;

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
import com.wx.common.bean.WxshopUser;
import com.wx.shop.biz.WxshopUserBiz;

@RestController
public class WxshopUserController {

	@Resource(name="wxshopUserBizImpl")
	private WxshopUserBiz wxshopUserBiz;
	
	@RequestMapping("/back/findAllWxshopUser.action")
	public void findAllWxshopUser(HttpServletResponse response){
		List<WxshopUser> list=wxshopUserBiz.findAllWxShopUser();
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
