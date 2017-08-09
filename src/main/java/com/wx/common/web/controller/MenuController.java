package com.wx.common.web.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;


import net.sf.json.JSONObject;

//@RestController
public class MenuController {
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	
	//查询公众号菜单
	@RequestMapping(name="/searchMenu.action")
	public JsonModel searchMenu() throws ParseException, IOException, java.text.ParseException {
		
		
		JsonModel jm = new JsonModel();
		//使用access_token查询
		try {
			String access_token = GetAccessToken.getAT(accessTokenZpBiz);
			
			String url = WeixinUtil.QUERY_MENU_URL.replace("ACCESS_TOKEN", access_token);
			JSONObject jo = WeixinUtil.doGetStr(url);
			//json转对象
			String jsonStr = jo.toString();
			System.out.println( jsonStr );
			
			jm.setCode(1);
			jm.setObj(jsonStr);
			
		}catch (IOException e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		
		
		
		return jm;
	}
	
	
	
	@RequestMapping(name="delMenu.action")
	public JsonModel delMenu() throws ParseException, IOException, java.text.ParseException{
		
		JsonModel jm = new JsonModel();
		//获取token
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		//删除菜单url
		String url = WeixinUtil.DELETE_MENU_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject jo = WeixinUtil.doGetStr(url);
		
		
		
		return jm;
	}
	
	
	
	
	
	
	
	
	
}
