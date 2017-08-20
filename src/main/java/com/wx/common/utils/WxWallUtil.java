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
public class WxWallUtil {
	
	public static void sendServiceMsg(String fromUserName,AccessTokenZpBiz accessTokenZpBiz) throws ParseException, IOException, java.text.ParseException{
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser(fromUserName); 
		temp.setUrl("http://campus.163.com/#/home");
		temp.setTemplate_id("KT2g5VK9vGC7PB2T1O6MW9m0MtfOWBEHoA_Y00kXj9E");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//创建TemplateData 
		TemplateData first= new TemplateData();
		first.setValue(  "您的浦发银行个人信用卡本期账单: \n\n\n" );
//		first.setColor("#000000");
		map.put("first", first);
		//支付金额
		TemplateData keyword1 = new TemplateData();  
		keyword1.setColor("#FF8C00");  
		keyword1.setValue(  new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"\n");   
		map.put("keyword1", keyword1);
		//商品信息
		TemplateData keyword2 = new TemplateData();  
		keyword2.setColor("#FF0000");  
		keyword2.setValue("您无需还款 \n\n");  
		map.put("keyword2", keyword2);
		
		TemplateData keyword3 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword3.setValue(" 人民币-5.27\n");  
		map.put("keyword3", keyword3);
		
		TemplateData keyword4 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword4.setValue(" 人民币0.00\n\n");  
		map.put("keyword4", keyword4);
		
		TemplateData remark = new TemplateData();
		remark.setValue("点击全文可进入您的云账单,办理账单分期,积分查询,快速还款");
		map.put("remark", remark);
		
		
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
