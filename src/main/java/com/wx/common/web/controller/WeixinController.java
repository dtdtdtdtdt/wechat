package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.bean.KeyReply;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.bean.Sign;
import com.wx.common.bean.UserLx;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.biz.SignBiz;
import com.wx.common.utils.CheckUtil;
import com.wx.common.utils.CommonDateUtils;
import com.wx.common.utils.KeyReplyUtils;
import com.wx.common.utils.SignUtil;
import com.wx.common.utils.WxSignNewsMsg;
import com.wx.common.utils.WxWallUtil;
import com.wx.common.utils.XmlAndMap;
import com.wx.message.Image;
import com.wx.message.ImageMessage;
import com.wx.message.TextMessage;
import com.wx.user.biz.UserBiz;
import com.wx.wxwall.entity.User;
import com.wx.wxwall.websocket.MyWebSocketHandler;

@Controller
public class WeixinController {

	@Resource(name = "signBizImpl")
	private SignBiz signBiz;

	@Resource(name = "accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;

	@Resource(name="userBizImpl")
	private UserBiz ub;
	
	@Resource(name="keyReplyBizImpl")
	private KeyReplyBiz keyReplyBiz;
	
	@Resource(name="firstMenuDbBizImpl")
	private FirstMenuDbBiz firstMenuDbBiz;
	
	@Resource(name="secondMenuDbBizImpl")
	private SecondMenuDbBiz secondMenuDbBiz;
	
	@Resource
	MyWebSocketHandler handler;
	//用于微信墙
	Map<Long, User> users = new HashMap<Long, User>();
	
	
	
	// 微信服务器认证发送一条get请求
	@RequestMapping(value = "/weixin.action", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 将微信发过来的参数数据转换成map
		// Map<String,String> map = XmlAndMap.xmlToMap(req);

		String signature = req.getParameter("signature"); // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = req.getParameter("timestamp"); // 时间戳
		String nonce = req.getParameter("nonce"); // 随机数
		String echostr = req.getParameter("echostr"); // 随机字符串

		// 如果请求来自微信则返回echostr给微信 用来验证成功
		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
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
	        String EventKey = map.get("EventKey");
	        //事件为关注
        	if(Event.equals("subscribe")){
        		//关注时添加到数据库
				UserLx userLx=new UserLx();
				userLx.setOpenid(fromUserName);
				userLx=ub.getWechatUser(userLx);

				//先查询是否存在该用户
				UserLx wu=new UserLx();
				wu=ub.findUser(userLx);
				//不存在则插入  存在即更新
				if(wu!=null){
					ub.updateUser(userLx);
				}else{
					ub.addUser(userLx);
				}
				//关注时推送！
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
        	}else if(Event.equals("CLICK") ){
        		//获取key值执行不同的操作    包括一级菜单和二级菜单唉  所以需要先查找所有一级菜单和二级菜单中的key值！
        		// 获取key和数据库中的对比  在根据数据库中的回复关键字回复内容   所以需要修改表哦
        		
        		List<FirstMenuDb>  firstMenuList =  firstMenuDbBiz.findAllFirstMenu();
        		if( firstMenuList!=null&&firstMenuList.size()>0 ){
            		for( FirstMenuDb fm :firstMenuList ){
            			if( EventKey.equals(fm.getKey() ) ){ //fm.getKey()  是数据库中的key	在根据event执行操作
            	        	KeyReply  kr =  keyReplyBiz.findKeyWords( fm.getEvent()  ); 
            	        	//根据kr的ktype判断   0为文本 1为 图片 2 语音 3 视频
            	        	if(kr!=null){
            	        		//根据类型判断进行回复
            	        		switch(kr.getKtype()){
            	        			case 0: KeyReplyUtils.keyReplyText(kr, toUserName, fromUserName, out);  break;
            		        		case 1: KeyReplyUtils.keyReplyImage(kr, toUserName, fromUserName, out); break;
            		        		case 2: KeyReplyUtils.keyReplyVoice(kr, toUserName, fromUserName, out); break;
            		        		case 3: KeyReplyUtils.keyReplyVideo(kr, toUserName, fromUserName, out); break;
            		        		case 4: KeyReplyUtils.keyReplyNews(kr, toUserName, fromUserName, out); break;
            	        		}
            	        	}
            			}
            			
            		}
        			
        		}

        		//所有二级菜单
        		SecondMenuDb sm = new SecondMenuDb();
        		sm.setFid( null );
        		List<SecondMenuDb>  secondMenuList =  secondMenuDbBiz.findAllSecondMenuByFid(sm);
        		if( secondMenuList!=null&&secondMenuList.size()>0 ){
            		for( SecondMenuDb smd :secondMenuList ){
            			if( EventKey.equals(smd.getKey() ) ){ //在根据event执行操作
            	        	KeyReply  kr =  keyReplyBiz.findKeyWords( smd.getEvent()  ); 
            	        	//根据kr的ktype判断   0为文本 1为 图片 2 语音 3 视频
            	        	if(kr!=null){
            	        		//根据类型判断进行回复
            	        		switch(kr.getKtype()){
            	        			case 0: KeyReplyUtils.keyReplyText(kr, toUserName, fromUserName, out);  break;
            		        		case 1: KeyReplyUtils.keyReplyImage(kr, toUserName, fromUserName, out); break;
            		        		case 2: KeyReplyUtils.keyReplyVoice(kr, toUserName, fromUserName, out); break;
            		        		case 3: KeyReplyUtils.keyReplyVideo(kr, toUserName, fromUserName, out); break;
            		        		case 4: KeyReplyUtils.keyReplyNews(kr, toUserName, fromUserName, out); break;
            	        		}
            	        	}
            			}
            		}
        			
        		}

        		
        		
        		
        		//为了让其自动识别签到功能  扫描一级菜单是否有签到菜单  
        		//所有一级菜单
        		if(firstMenuList!=null&&firstMenuList.size()>0){
        			for( FirstMenuDb fm :firstMenuList ){
        				if(fm.getName().equals("签到")){
        					//点击签到
        	        		if( EventKey.equals(fm.getKey()) ){
                        		//签到
                        		SignUtil.sign(fromUserName, toUserName, signBiz, out);
                        		break;
        	        		}

        				}
        			}
        		}


        		
			}
        }else if(msgType.equals("text")){ // 文本信息
        	//首先判断该用户是否进入微信墙模式   一会搞定...
        	
        	
        	KeyReply  kr =  keyReplyBiz.findKeyWords(content); //根据用户发的信息查询(关键字)回复内容
        	//根据kr的ktype判断   0为文本 1为 图片 2 语音 3 视频
        	if(kr!=null){
        		//根据类型判断进行回复
        		switch(kr.getKtype()){
        			case 0: KeyReplyUtils.keyReplyText(kr, toUserName, fromUserName, out);  break;
	        		case 1: KeyReplyUtils.keyReplyImage(kr, toUserName, fromUserName, out); break;
	        		case 2: KeyReplyUtils.keyReplyVoice(kr, toUserName, fromUserName, out); break;
	        		case 3: KeyReplyUtils.keyReplyVideo(kr, toUserName, fromUserName, out); break;
	        		case 4: KeyReplyUtils.keyReplyNews(kr, toUserName, fromUserName, out); break;
        		}
        	}
        	//如果发送的文本是  微信墙则绑定微信墙功能！  
        	//处理思路   新建一张表  用于存取上墙的用户   以后就可以抽奖操作
        	if( content.equals("微信墙") ){
        		WxWallUtil.sendServiceMsg(fromUserName, accessTokenZpBiz);
        	}
        	//存在session中
    		ServletContext application = req.getServletContext();
    		application.setAttribute("my", content);
    	
        
    		

        	

        }
	}
}
