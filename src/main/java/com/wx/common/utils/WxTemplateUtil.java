package com.wx.common.utils;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.template.TemplateData;
import com.wx.template.WxTemplate;

public class WxTemplateUtil implements Serializable {

	private static final long serialVersionUID = 3667137791376147158L;
	
	
	public static void sendRobotLoginTemplate(String fromUserName,AccessTokenZpBiz accessTokenZpBiz  ) throws ParseException, IOException, java.text.ParseException{
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser(  fromUserName ); 
//		temp.setUrl("http://campus.163.com/#/home");
		temp.setTemplate_id("UD0TYSbMu1_luu7pABszV849uhJMd8xRowouzJG_3aU");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//创建TemplateData 
//		TemplateData first= new TemplateData();
//		first.setValue(  "机器人绑定成功: \n\n\n" );
//		map.put("first", first);
		//绑定日期
		TemplateData keyword1 = new TemplateData();  
		keyword1.setColor("#FF8C00");  
		keyword1.setValue(  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\n");   
		map.put("keyword1", keyword1);
		// 提示
		TemplateData remark = new TemplateData();
		remark.setValue("回复[退出机器人]即可退出！");
		map.put("remark", remark);

		//这句在最后 不能丢！
		temp.setData(  map );
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		// 发送请求！
		WeixinUtil.doPostStr(  url , json);
	}
	
	// 退出机器人 模板信息回复
	public static void sendRobotOutTemplate(String fromUserName,AccessTokenZpBiz accessTokenZpBiz  ) throws ParseException, IOException, java.text.ParseException{
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser(  fromUserName ); 
//		temp.setUrl("http://campus.163.com/#/home");
		temp.setTemplate_id("CQc2zxnGQuUhJPvSKHBwjojmGmE8I3DWqMVSczHNrX0");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//创建TemplateData 
		TemplateData first= new TemplateData();
		first.setValue(  "机器人模式退出成功！" );
		map.put("first", first);
		//欢迎下次使用
		TemplateData keyword1 = new TemplateData();  
		keyword1.setColor("#FF8C00");  
		keyword1.setValue( "记得推荐给其他小伙伴哦~" );   
		map.put("keyword1", keyword1);
		// 提示
//		TemplateData remark = new TemplateData();
//		remark.setValue("可以推荐给你的小伙伴哦~");
//		map.put("remark", remark);

		//这句在最后 不能丢！
		temp.setData(  map );
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		// 发送请求！
		WeixinUtil.doPostStr(  url , json);
	}
	
}
