package com.wx.common.web.controller;

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
import com.wx.common.bean.KeyReply;
import com.wx.common.bean.SubscribeReply;
import com.wx.common.biz.SubscribeReplyBiz;
import com.wx.common.web.model.JsonModel;

@RestController
public class SubscribeReplyController {

	@Resource(name="subscribeReplyBizImpl")
	private SubscribeReplyBiz subscribeReplyBiz;
	
	//增加关注回复设置
	@RequestMapping(value="/back/addSubscribeReply.action")
	public JsonModel addSubscribeReply( SubscribeReply subscribeReply ){
		JsonModel jm = new JsonModel();
		
		subscribeReplyBiz.addSubscribeReply(subscribeReply);
		jm.setCode(1);
		
		return jm;
	}
	
	//查找所有
	@RequestMapping(value="/back/findAllSubscribeReply.action")
	public void findAllSubscribeReply( HttpServletResponse response){
		List<SubscribeReply> list = subscribeReplyBiz.allSubscribeReply();
		int count = 0;
		if(list!=null&&list.size()>0){
			count = list.size();
		}
	
		Gson gson=new Gson();
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
	
	//根据sid删除
	@RequestMapping(value="/back/delSubscribeReplyBySid.action")
	public JsonModel delSubscribeReplyBySid( SubscribeReply subscribeReply ){
		JsonModel jm = new JsonModel();
		
		subscribeReplyBiz.delSubscribeReplyBySid(subscribeReply);
		jm.setCode(1);
		return jm;
	}
	
	
	
	
	
}

