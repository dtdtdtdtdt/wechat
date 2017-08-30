package com.wx.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.template.TemplateData;
import com.wx.template.WxTemplate;

import net.sf.json.JSONObject;

//用于发送业务模板信息
public class WxWallTemplateUtil {
	
	// 登录微信墙发送模板信息
	public static void sendWxWallLogin(String fromUserName,AccessTokenZpBiz accessTokenZpBiz) throws ParseException, IOException, java.text.ParseException{
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser(fromUserName); 
		temp.setTemplate_id("gSZ5NbW0WXpy2Z-SphQV2EfsheSHlh_jvyBcod3M3zI");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//活动名称
		TemplateData first = new TemplateData();  
		first.setColor("#FF8C00");  
		first.setValue( "活动名称:微信非官方团队搞事情" );   
		map.put("first", first);

//		TemplateData remark = new TemplateData();
//		remark.setValue("燥起来~");
//		map.put("remark", remark);
		
		//这句在最后 不能丢！
		temp.setData(  map );
		
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
//		System.out.println( json+"\n" );
		//发送模板
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		JSONObject jo2;
		jo2 = WeixinUtil.doPostStr(  url , json);
	}
	
	
	
	// 退出微信墙发送模板信息
	public static void sendWxWallOut(String fromUserName,AccessTokenZpBiz accessTokenZpBiz) throws ParseException, IOException, java.text.ParseException{
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser(fromUserName); 
		temp.setTemplate_id("M1tfT17iEBS0xF1Xraybp8NFC66OV1LnVgmC8J0XYRQ");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();

		TemplateData first = new TemplateData();
		first.setValue("欢迎下次使用！");
		map.put("first", first);
		
		
		//这句在最后 不能丢！
		temp.setData(  map );
		
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
//		System.out.println( json+"\n" );
		//发送模板
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		JSONObject jo2;
		jo2 = WeixinUtil.doPostStr(  url , json);
	}
	
	
	
	
	
	
}
