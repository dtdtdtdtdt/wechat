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
import com.wx.common.bean.Robot;
import com.wx.common.bean.RobotStatus;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.bean.Sign;
import com.wx.common.bean.SubscribeReply;
import com.wx.common.bean.UserLx;
import com.wx.common.bean.WxWallStatus;
import com.wx.common.bean.WxWallUser;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.biz.RobotBiz;
import com.wx.common.biz.RobotStatusBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.biz.SignBiz;
import com.wx.common.biz.SubscribeReplyBiz;
import com.wx.common.biz.WxWallStatusBiz;
import com.wx.common.biz.WxWallUserBiz;
import com.wx.common.utils.CheckUtil;
import com.wx.common.utils.CommonDateUtils;
import com.wx.common.utils.KeyReplyUtils;
import com.wx.common.utils.SignUtil;
import com.wx.common.utils.TulingRobot;
import com.wx.common.utils.WxSignNewsMsg;
import com.wx.common.utils.WxTemplateUtil;
import com.wx.common.utils.WxWallTemplateUtil;
import com.wx.common.utils.XmlAndMap;
import com.wx.message.Image;
import com.wx.message.ImageMessage;
import com.wx.message.TextMessage;
import com.wx.user.biz.UserBiz;

import io.goeasy.GoEasy;

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

	@Resource(name="subscribeReplyBizImpl")
	private SubscribeReplyBiz subscribeReplyBiz;
	
	@Resource(name="robotBizImpl")
	private RobotBiz robotBiz;
	
	@Resource(name="robotStatusBizImpl")
	private RobotStatusBiz robotStatusBiz;
	
	// 用于微信墙用户处理
	@Resource(name="wxWallUserBizImpl")
	private WxWallUserBiz wxWallUserBiz;
	
	
	@Resource(name="wxWallStatusBizImpl")
	private WxWallStatusBiz wxWallStatusBiz;
	
	
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
        
        //System.out.println( "收到用户发来的信息:"+map );
        
        
        PrintWriter out = resp.getWriter();
        //获取来自请求的信息
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
		
		//System.out.println( toUserName +"*******************" );
        
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
				//先查找关注回复设置 
				List<SubscribeReply> list = subscribeReplyBiz.allSubscribeReply();
				if( list!=null&&list.size()>0 ){
					for( SubscribeReply sr : list ){
        	        	KeyReply  kr =  keyReplyBiz.findKeyWords( sr.getKeywords()  ); 
        	        	//根据kr的ktype判断   0为文本 1为 图片 2 语音 3 视频
        	        	if(kr!=null){
        	        		//根据类型判断进行回复
        	        		switch(kr.getKtype()){
        	        			case 0: KeyReplyUtils.keyReplyText(kr, toUserName, fromUserName, out);  break;
        		        		case 1: KeyReplyUtils.keyReplyImage(kr, toUserName, fromUserName, out); break;
        		        		case 2: KeyReplyUtils.keyReplyVoice(kr, toUserName, fromUserName, out); break;
        		        		case 3: KeyReplyUtils.keyReplyVideo(kr, toUserName, fromUserName, out); break;
        		        		case 4: KeyReplyUtils.keyReplyNews(kr, toUserName, fromUserName, out);  break;
        	        		}
        	        	}
					}
					
				}else{ // 默认为这个！
		        	UserLx userL = new UserLx();
		        	userLx.setOpenid(fromUserName);
		        	UserLx user = ub.findUser(userLx);
		        	
		            TextMessage text = new TextMessage();
		            text.setToUserName(fromUserName);
		            text.setFromUserName(toUserName); 
		            text.setMsgType("text");     //返回的类型
		            text.setCreateTime(  new Date().getTime() );
		            text.setContent("你好"+  user.getNickname()  +"\n欢迎来到诺亚方舟,等候多时了哦~");	
		            message = XmlAndMap.textMessageToXml(text);
			        out.print(message);
			        out.flush();
			        out.close();
				}
				
				
				

        	}else if(Event.equals("unsubscribe")){
        		//取消关注时更新用户信息
				UserLx userLx=new UserLx();
				userLx.setOpenid(fromUserName);

				//先查询是否存在该用户
				UserLx wu=new UserLx();
				wu=ub.findUser(userLx);
				//不存在则插入  存在即更新用户关注信息
				if(wu!=null){
					ub.updateSubUser(userLx);
				}else{
					userLx=ub.getWechatUser(userLx);
					ub.addUser(userLx);
				}
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
        	// 用于微信墙获取用户头像和发送的内容!
        	UserLx userLx = new UserLx();
        	userLx.setOpenid(fromUserName);
        	UserLx user = ub.findUser(userLx);
        	KeyReply  kr2 = null;
        	//微信墙功能
        	WxWallStatus ws = wxWallStatusBiz.findWxWallStatus();
        	
        	//先判断是否有启用机器人功能
        	RobotStatus rs = robotStatusBiz.findRobotStatus();
        	//启用了机器人
        	if(rs!=null&&rs.getStatus()==1){
            	Robot r = robotBiz.findRobotUser( fromUserName );
            	// 第一次使用机器人
            	if( content.equals("机器人") ){
            		//判断是否使用过机器人...
            		if(r==null){
                		robotBiz.addRobotUser(fromUserName);
                		//推送一条绑定机器人成功的信息！
                		WxTemplateUtil.sendRobotLoginTemplate(fromUserName, accessTokenZpBiz);
            		}else{ //更改状态为1
            			if( r.getStatus()!=1 ){//如果是进入机器人模式后 再次回复机器人  就不要推送了
                    		//推送一条绑定机器人成功的信息！
                    		WxTemplateUtil.sendRobotLoginTemplate(fromUserName, accessTokenZpBiz);
            			}
            			robotBiz.updateRobotUserLogin(fromUserName);
            		}
            	}
            	if( r!=null&&r.getStatus()==1 ){ // 正在使用机器人
            		if( content.equals("退出机器人")){
            			robotBiz.updateRobotUserOut(fromUserName); // 修改状态值为  0 则退出了机器人
            			//推送一条信息退出机器人
            			WxTemplateUtil.sendRobotOutTemplate(fromUserName, accessTokenZpBiz);
            			return ;
            		}
            		
            		// 调用图灵机器人
            		String reply = TulingRobot.Msg(content);
    	            TextMessage text = new TextMessage();
    	            text.setToUserName(fromUserName);
    	            text.setFromUserName(toUserName); 
    	            text.setMsgType("text");     //返回的类型
    	            text.setCreateTime(  new Date().getTime() );
    	            text.setContent( reply );	
    	            String message = XmlAndMap.textMessageToXml(text);
    		        out.print(message);
    		        out.flush();
    		        out.close();

            		
            	}
        	}
        	if( ws!=null&&ws.getStatus()==1 ){
            	//如果发送的文本是  微信墙则绑定微信墙功能！  
            	//处理思路   新建一张表  用于存取上墙的用户   以后就可以抽奖操作
            	WxWallUser wwu = wxWallUserBiz.findWxWallUserByFromUserName(fromUserName);
            	if( content.equals("微信墙") ){
                	//第一次使用
                	if(wwu==null) {
                		WxWallUser wxWallUser = new WxWallUser();
                		wxWallUser.setFromUserName(fromUserName);
                		wxWallUserBiz.addWxWallUser(wxWallUser);
                		//推送一条绑定微信墙成功的信息
                		WxWallTemplateUtil.sendWxWallLogin(fromUserName, accessTokenZpBiz);
                	}else { //更改使用状态
                		if( wwu.getStatus()!=1 ) { //已经进入微信墙状态
                       		//推送一条绑定微信墙成功的信息
                    		WxWallTemplateUtil.sendWxWallLogin(fromUserName, accessTokenZpBiz);
                		}
            			wwu.setStatus(1);
            			wxWallUserBiz.updateWxWallUserStatus(wwu);
                	}
            		
            	}
            	//正在使用微信墙
            	if( wwu!=null&&wwu.getStatus()==1 ) {
            		if( content.equals("退出微信墙") ) {
            			//修改状态为0
            			wwu.setStatus(0);
            			wxWallUserBiz.updateWxWallUserStatus(wwu);
            			//发送一条退出成功的信息
            			WxWallTemplateUtil.sendWxWallOut(fromUserName, accessTokenZpBiz);
            			return;
            		}
            		//反馈用户上墙成功
            		KeyReplyUtils.successWxWall(kr2, toUserName, fromUserName, out);
            		//字符串截取一下
            		if( content.length()>=13 ) {
            			content = content.substring(0, 13);
            		}                	//使用goeasy推送到前端页面！
            		GoEasy goEasy = new GoEasy("BC-e44baa9b32d64f40abf5cddeffa3aa54");
            		goEasy.publish("BS-8ecca8bee3204a06a16f5d584262bee0",user.getNickname()+":"+content+"#$"+user.getHeadimgurl() );  
            	}
        	}
        	
        	//微信墙未开启    机器人也可以触发关键字回复
        	if( ws!=null&&ws.getStatus()==0 ) {
            	//文本关键字回复功能
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
        	}
        	
        	


        }
	}
}
