package com.wx.common.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wx.common.bean.KeyReply;
import com.wx.message.Image;
import com.wx.message.ImageMessage;
import com.wx.message.News;
import com.wx.message.NewsMessage;
import com.wx.message.TextMessage;
import com.wx.message.Video;
import com.wx.message.VideoMessage;
import com.wx.message.Voice;
import com.wx.message.VoiceMessage;

//用于微信关键字回复工具类  减少weixinController代码显示
public class KeyReplyUtils implements Serializable{
	
	//回复文本信息
	public static void keyReplyText(KeyReply kr,String toUserName,String fromUserName,PrintWriter out  ){
		
        TextMessage text = new TextMessage();
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName); 
        text.setMsgType("text");     //返回的类型
        text.setCreateTime(  new Date().getTime() );
        //内容需要拿过来使用
        text.setContent("关键字回复:\n"+kr.getContent());	
        String message = XmlAndMap.textMessageToXml(text);
        out.print(message);
        out.flush();
        out.close();
	}
	
	//回复图片信息
	public static void keyReplyImage(KeyReply kr,String toUserName,String fromUserName,PrintWriter out  ){
		Image i = new Image();
		i.setMediaId(  kr.getMediaId() );
		ImageMessage im = new ImageMessage();
		im.setFromUserName(toUserName);
		im.setToUserName(fromUserName);
		im.setCreateTime(new Date().getTime());
		im.setMsgType("image");
		im.setImage(i);
        String message = XmlAndMap.imageMessageToXml(im);
        out.print(message);
        out.flush();
        out.close();
	}
	
	//回复语音信息
	public static void keyReplyVoice(KeyReply kr,String toUserName,String fromUserName,PrintWriter out  ){
		Voice v = new Voice();
		v.setMediaId(kr.getMediaId());
		VoiceMessage vm = new VoiceMessage();
		vm.setCreateTime(new Date().getTime());
		vm.setFromUserName(toUserName);
		vm.setToUserName(fromUserName);
		vm.setMsgType( "voice" );
		vm.setVoice(v);
		String message = XmlAndMap.voiceMessageToXml(vm);
        out.print(message);
        out.flush();
        out.close();
	}
	
	
	//回复视频信息
	public static void keyReplyVideo(KeyReply kr,String toUserName,String fromUserName,PrintWriter out  ){
		Video v = new Video();
		v.setDescription(kr.getDescription());
		v.setMediaId(kr.getMediaId());
		v.setTitle(kr.getTitle());
		VideoMessage vm = new VideoMessage();
		vm.setCreateTime(new Date().getTime());
		vm.setFromUserName(toUserName);
		vm.setToUserName(fromUserName);
		vm.setMsgType("video");
		vm.setVideo(v);
		String message = XmlAndMap.videoMessageToXml(vm);
        out.print(message);
        out.flush();
        out.close();
	}
	
	//回复图文信息
	public static void keyReplyNews(KeyReply kr,String toUserName,String fromUserName,PrintWriter out  ){
		News n = new News();
		n.setTitle(kr.getTitle());
		n.setDescription(kr.getDescription());
		n.setUrl(kr.getUrl());
		n.setPicUrl(kr.getPicUrl());
		
		List<News> articles = new ArrayList<News>();
		articles.add(n);
		
		NewsMessage nm = new NewsMessage();
		nm.setArticleCount(1);
		nm.setArticles(articles);
		nm.setCreateTime(new Date().getTime());
		nm.setMsgType("news");
		nm.setFromUserName(toUserName);
		nm.setToUserName(fromUserName);
		
		String message = XmlAndMap.newsMessageToXml(nm);
        out.print(message);
        out.flush();
        out.close();
	}
	
	
}
