package com.wechat.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.ParseException;

import com.wx.common.utils.WeixinUtil;
import com.wx.menu.Button;
import com.wx.menu.ClickButton;
import com.wx.menu.Menu;
import com.wx.menu.ViewButton;

import net.sf.json.JSONObject;

public class TokenTest {
	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
//		AccessToken token = WeixinUtil.getAccessToken();
//		System.out.println( token.getToken()+"\n"+token.getExpiresIn()  );
		
//		String wxtoken = "-GfQW8WpA2Z2kf7vQ_WQ6s7dwqkFoBtJLnTOD8WXbI8oF3C83m41ZwiAUQdtbu0FvYy-Y7w_AQOZ-7NObzXYEjbwa760FaUu_E0YGuwSIMkAVEdAGAPKT";
//		int wxExpiresIn = 7200;
//		//上传图片会需要一个id
//		String path = "C:\\E/1.jpg";
//		String meadiid = WeixinUtil.upload(path, wxtoken, "thumb");
//		System.out.println( meadiid );
		
		
		//测试推送信息
//		StringBuffer sb = new StringBuffer();
//		sb.append("{");		
//		sb.append("\"touser\":[");		
//		sb.append("\"oeFt8wYmSWPGqc8BJKxBQDU_px7U\",");	
//		sb.append("\" oeFt8weYLF0KWGw9a-zdLA2B-Z_8\"");	
//		sb.append("],");		
//		sb.append("\"msgtype\": \"text\",");	
//		sb.append(" \"text\": { \"content\": \"hello from boxer.\"}");
//		sb.append("}");
//		String access_token="3Z4KQQMTyy83lHXyguLJW4Wt47aN0YJLOGAark8bFJSbCtDskrLDwVMy7niOSm2YchLIgeKqrv24wKR1no3Y-_rSM998oXmrSXvQSZQzH7zuwJk6J0gHyo_0weaBODzsGXNcAFALCS";
//		String url = WeixinUtil.QUN_FA_URL.replace("ACCESS_TOKEN", access_token);
//		JSONObject jo = WeixinUtil.doPostStr(url, sb.toString());
//		System.out.println( jo );
		
		
		
		
		
		
		
		
		
		
		Menu menu = new Menu();
		//click 菜单事件
		ClickButton cb11 = new ClickButton();
		cb11.setName("签到");
		cb11.setType("click");
		cb11.setKey("11");
		
		ViewButton vb12 = new ViewButton();
		vb12.setName("view菜单");
		vb12.setType("view");
		vb12.setUrl("http://campus.163.com/#/home");
		
		ClickButton cb13 = new ClickButton();
		cb13.setName("点击扫码");
		cb13.setType("scancode_push");
		cb13.setKey("13");
		
		
		ClickButton cb14 = new ClickButton();
		cb14.setName("地理位置");
		cb14.setType("location_select");
		cb14.setKey("14");
		
		ClickButton cb15 = new ClickButton();
		cb15.setName("系统拍照");
		cb15.setType("pic_sysphoto");
		cb15.setKey("14");
		
		ClickButton cb16 = new ClickButton();
		cb16.setName("微信相册");
		cb16.setType("pic_weixin");
		cb16.setKey("14");
		
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{cb13,cb14,cb15,cb16});  
		//三个一级菜单
		menu.setButton(new Button[]{cb11,vb12,button});  
		
		JSONObject  jsonObject = JSONObject.fromObject(menu);
		System.out.println( jsonObject.toString() );
//		String ACCESS_TOKEN = "mW1shrUwN-DmNI8rCVIE0WYIU1WdnlJOos-yLFWpIqelyqHdrTc40telaVVN6PF1er9eTE5BXhLlnYZNv-WRM3RZnCreqGh04RvH_YOIxU1fsnz3rl2ZIpcfHARdWH4iROBjAFAEBO";
//		String outStr = jsonObject.toString();
//		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ACCESS_TOKEN;
//		JSONObject  jo = WeixinUtil.doPostStr(url, outStr);
//		System.out.println( jo.getInt("errcode") );
//		if( jo.getInt("errcode")==0 ){
//			System.out.println( "创建菜单成功" );
//		}else{
//			System.out.println("失败");
//		}
		
		
		
		
//		//测试查询菜单
//		String access_token = "fyEV9dckYNMe1-i878lYM7Zaj0iMeNajK6JLNWcrJruuToGKeoqBNxOn0fcTDs3cjZm62b6YvTlaiIyqOQHxQG1F-SWYe4vOdSPxAWoG_MmqX53HPm3tR4g4Z9Y5dzmqWPWbAGAPIP";
////		String access_token ="stRHnbI7r1UYTA0Jv3dpv-QllmypOm-y4DnJPwtBASQXEWFXOlbnupxvMu_0euXjNMTtZ99q4NOwLgkWRJ8sWofmXJnrareyrACwR1RC8axlbC_KPqrDmljUhhviXwryIFMbAHAPMT";
//		String url = WeixinUtil.QUERY_MENU_URL.replace("ACCESS_TOKEN", access_token);
//		JSONObject jo = WeixinUtil.doGetStr(url);
//		//json转对象
//		String jsonStr = jo.toString();
//		System.out.println( jsonStr );
//		Menu m = TransFormUtil.jsonToBean(jsonStr, Menu.class);
//		System.out.println( m );
		
		
		
		
		
		
		
	}
}
