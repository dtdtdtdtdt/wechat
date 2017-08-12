package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.Sign;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.SignBiz;
import com.wx.common.utils.CheckUtil;
import com.wx.common.utils.CommonDateUtils;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WxSignNewsMsg;
import com.wx.common.utils.XmlAndMap;
import com.wx.message.News;
import com.wx.message.NewsMessage;
import com.wx.message.TextMessage;

@Controller
public class WeixinController {
	
	@Resource(name="signBizImpl")
	private SignBiz signBiz;
	
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	//微信服务器认证发送一条get请求
	@RequestMapping(value="/weixin.action",method=RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//将微信发过来的参数数据转换成map
//		Map<String,String> map = XmlAndMap.xmlToMap(req);
		
		String signature = req.getParameter("signature");	//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = req.getParameter("timestamp");  //时间戳
		String nonce = req.getParameter("nonce");		 //随机数
		String echostr = req.getParameter("echostr");	 //随机字符串
		
		//如果请求来自微信则返回echostr给微信 用来验证成功
		PrintWriter out = resp.getWriter();
		if( CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr); 
		}
	}
	
	
	@RequestMapping(value="/weixin.action",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, org.apache.http.ParseException, ParseException {
		
        //将传过来xml转换为map
        Map<String, String> map = XmlAndMap.xmlToMap(req);
        
        System.out.println( "收到用户发来的信息:"+map );
        
        
        PrintWriter out = resp.getWriter();
        //获取来自请求的信息
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
		
		System.out.println( toUserName +"*******************" );
        
        //用户操作事件为点击菜单
        if("event".equals(msgType)){
        	String message = null;
	        String Event = map.get("Event");
	        //事件为关注
        	if(Event.equals("subscribe")){
	            TextMessage text = new TextMessage();
	            text.setToUserName(fromUserName);
	            text.setFromUserName(toUserName); 
	            text.setMsgType("text");     //返回的类型
	            text.setCreateTime(  new Date().getTime() );
	            text.setContent("欢迎来到诺亚方舟!等候多时了哦");	
	            message = XmlAndMap.textMessageToXml(text);
		        out.print(message);
		        out.flush();
		        out.close();
        	}else if( Event.equals("CLICK") ){
            	//签到
            	Sign sign = new Sign();
            	sign.setFromUserName(fromUserName);
            	System.out.println( sign );
            	//根据用户名进行数据库查询是否有该用户
            	Sign s = signBiz.findSignByFromUserName(sign);
            	//如果没有则是第一次签到
            	if( s==null ){
            		Sign newSign = new Sign();
            		newSign.setFromUserName(fromUserName); //签到人
            		newSign.setIntegration(1);  //签到总积分
            		newSign.setSignCount(1);  //签到次数
            		newSign.setSignHistory(1);  //签到历史   注意！签到时间由数据库自己写
            		//插入数据
            		signBiz.addSign(newSign);
        			//推送签到图文
        			WxSignNewsMsg.sendWxSignNewsMsg(  newSign , fromUserName, toUserName, out);
            	}else{
            		//获取签到次数   总积分  签到时间
            		Integer signCount = s.getSignCount();   //签到次数
            		Integer integration = s.getIntegration();  //总积分
            		Timestamp ts = s.getLastModifytime();   //最近签到时间

    				//获取今天的时间戳
    				Timestamp todayStartTimeStamp = CommonDateUtils.getTodayStartTimeStamp();
    				//如果今天没有签到过
    				if(  todayStartTimeStamp.after(ts) ){
                		//计算是否断签过  根据当天的时间减去数据库中最近签到日期  如果大于1则说明前一天签到过
                		long dtime = 0;
        				try {
        					dtime = CommonDateUtils.StrDateFormat(  s.getLastModifytime().toString() ).getTime();
        				} catch (ParseException e) {
        					e.printStackTrace();
        				}
        				
        				final  long missDays= (System.currentTimeMillis()- dtime)/(24*60*60*1000);  //连续签到则等于 1
        				System.out.println( missDays+"--------------------------" );
        				//如果MissDays == 0  或者 1  则说明前一天已经签到连续签到 等于零表示时间差小于24小时
        				if( missDays < 2 ){
        					s.setSignCount( s.getSignCount()+1  ); //连续签到天数加一
        					s.setSignHistory( s.getSignHistory()+1 ); //签到历史  用于记录总签单天数
        					//解决积分问题  根据签到天数判断积分问题
        					switch( signCount+1 ){
        						case 1:s.setIntegration( s.getIntegration()+1 ); break;
        						case 2:s.setIntegration( s.getIntegration()+2 ); break;
        						case 3:s.setIntegration( s.getIntegration()+4 ); break;
        						case 4:s.setIntegration( s.getIntegration()+8 ); break;
        						case 5:s.setIntegration( s.getIntegration()+16 ); break;
        						case 6:s.setIntegration( s.getIntegration()+32 ); break;
        						case 7:s.setIntegration( s.getIntegration()+64 ); break;    
        						default : s.setIntegration( s.getIntegration()+64 ); break; 
        					}
        					
        				}else{
        					//否则连续签到次数变为1 
        					s.setSignCount(1);
        					s.setIntegration(  s.getIntegration()+1 );
        				}			
        				//更新数据库
        				signBiz.updateSign(s);
            			//推送签到图文
            			WxSignNewsMsg.sendWxSignNewsMsg(s, fromUserName, toUserName, out);
    				}else{  //今天签到过了！
            			//推送签到文本
            			WxSignNewsMsg.alreadySignNewsMsg(s, fromUserName, toUserName, out);
    				}


        	}
        	}
        }
    
	}
	
	
	
	
	
	
	
}
