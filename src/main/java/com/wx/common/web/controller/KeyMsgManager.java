package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.KeyReply;
import com.wx.common.bean.UserLx;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.web.model.JsonModel;


//关键字管理
@RestController
public class KeyMsgManager {
	
	@Resource(name="keyReplyBizImpl")
	private KeyReplyBiz keyReplyBiz;
	
	
	@RequestMapping("/back/findAllKeyWords.action")
	public void findAllKeyWords(HttpServletResponse response,HttpServletRequest request,KeyReply keyReply){
		//用于排序
		keyReply.setOrderby(  request.getParameter("sort")   );
		keyReply.setOrderway(  request.getParameter("order")  );
		Integer pagesize = Integer.parseInt(  request.getParameter("rows")    );
		keyReply.setPagesize(  pagesize );
		Integer start = (keyReply.getPages()-1)*keyReply.getPagesize();
		keyReply.setStart( start  );
		
		
		List<KeyReply> list = keyReplyBiz.findAllKeyWords(keyReply);
		Gson gson=new Gson();
		int count=keyReplyBiz.findKeyWordsCount();
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
	
	@RequestMapping("/back/deleteKeyWords.action")
	public JsonModel deleteKeyWords(KeyReply keyReply){
		
		JsonModel jm = new JsonModel();
		
		keyReplyBiz.deleteKeyWords(keyReply);
		jm.setCode(1);
		
		
		return jm;
	}
	
	
	
	
	
	
	
	
}
