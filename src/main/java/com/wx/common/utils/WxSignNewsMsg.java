package com.wx.common.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wx.common.bean.Sign;
import com.wx.message.News;
import com.wx.message.NewsMessage;
import com.wx.message.TextMessage;

public class WxSignNewsMsg implements Serializable {

	private static final long serialVersionUID = -7975388843160203011L;
	
	
	public static void sendWxSignNewsMsg( Sign sign,String fromUserName,String toUserName ,PrintWriter out ){
		//判断连续签到天数进行积分判断
		int signCount = sign.getSignCount();
		//计算本次签到获得的积
		int todayCount = 0;
		switch( signCount ){
			case 1: todayCount=1; break;
			case 2: todayCount=2; break;
			case 3: todayCount=4; break;
			case 4: todayCount=8; break;
			case 5: todayCount=16; break;
			case 6: todayCount=32; break;
			case 7: todayCount=64; break;
			default :  todayCount=64; break; 
		}
		int tomorrowCount = 0;
		switch( signCount ){
		case 1: tomorrowCount=2; break;
		case 2: tomorrowCount=4; break;
		case 3: tomorrowCount=8; break;
		case 4: tomorrowCount=16; break;
		case 5: tomorrowCount=32; break;
		case 6: tomorrowCount=64; break;
		default :  tomorrowCount=64; break; 
	}
		
		
		//推送一条图文消息
    	NewsMessage nms = new NewsMessage();
    	//图文详情
    	News news = new News();
    	news.setTitle("签到成功,获得"+( todayCount )+"积分");
    	news.setDescription("明天签到将获得"+( tomorrowCount )+"积分,加油吧！\n\n累计签到"+( sign.getSignCount() )+"天\n\n目前"+( sign.getIntegration() )+"积分");
    	news.setPicUrl("http://campus.163.com/campus/img/netease/introduction/163.jpg");
    	news.setUrl("http://campus.163.com/#/home");
    	List<News> list = new ArrayList<News>();
    	list.add(news);
    	//设置回复相关信息
    	nms.setArticleCount(1);  //1条图文
    	nms.setMsgType("news");
    	nms.setToUserName(fromUserName);
    	nms.setFromUserName(toUserName);
    	nms.setCreateTime(new Date().getTime());
    	nms.setArticles(list);  //设置图文	        	
    	String newsmessage = XmlAndMap.newsMessageToXml(nms);
    	out.print(newsmessage);
    	out.flush();
	    out.close();
	}
	
	
	//重复签到推送回复
	public static void alreadySignNewsMsg(Sign sign,String fromUserName,String toUserName ,PrintWriter out){
        TextMessage text = new TextMessage();
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName); 
        text.setMsgType("text");   //setMsgType("text");  //返回的类型
        text.setCreateTime(  new Date().getTime() );
        text.setContent("您今天已签到哦~" );
        String message = XmlAndMap.textMessageToXml(text);
        out.print(message);
        out.flush();
        out.close();
	}

	
	
	
}
