package com.wx.source.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wx.common.bean.MassMpnews;
import com.wx.common.bean.Mpnews;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.web.model.JsonModel;
import com.wx.source.biz.MassBiz;

import net.sf.json.JSONObject;

@RestController
public class MassController {
	
	@Resource(name="massBizImpl")
	private MassBiz massBiz;
	
	@Resource(name ="accessTokenZpBizImpl")
	private AccessTokenZpBiz atzb;
	
	@RequestMapping("/back/SendMessage.action")
	public JsonModel SendMessage(HttpServletRequest request,Mpnews mpnews,HttpSession session) throws ParseException, IOException{
		JsonModel jm =new JsonModel();	
		try {
			String msgtype =request.getParameter("msgtype");
			MassMpnews massMpnews =new MassMpnews();
			
			//图文消息
			if(msgtype.equals("mpnews")){
				//得到图文消息的mediaID
				Map<String,String> map = new HashMap<String,String>();
				map =(Map<String, String>) session.getAttribute("map");
				int end =map.get("media_id").length();
				mpnews.setMedia_id(map.get("media_id").substring(13,end-2));
				
				int send_ignore_reprint =0;
				massMpnews.setTouser(massBiz.getOpenID());
				String massMpnews1 =JSONObject.fromObject(massBiz.initMass(mpnews,msgtype,send_ignore_reprint)).toString();
				massBiz.MassMpnewsMessage(massMpnews1);
				
			 //文本消息
			}else if(msgtype.equals("text")){
				
			}
			jm.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}	
		return jm;	
	}
}
