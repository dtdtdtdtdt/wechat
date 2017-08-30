package com.wx.common.web.controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import com.wx.common.bean.KeyReply;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;
import com.wx.message.TextMessage;

//微信关键字设置回复   关键词回复是需要微信用户互动的！设计错误了  没错！应该放数据库
@RestController
public class KeyMsgToReplyController {
	
	
	@Resource(name="keyReplyBizImpl")
	private KeyReplyBiz keyReplyBiz;
	
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	//微信关键字设置回复  文本和图文
	@RequestMapping(value="/back/keyMsgToReplyTexeAndNews.action")
	public JsonModel keyMsgToReplyImg(KeyReply keyReply){
		JsonModel jm = new JsonModel();
		
//		System.out.println(  keyReply );
		
		//获取类型的不同
		String msgType = null;
		switch(keyReply.getKtype()){
		//用户操作为设置文本，图文 操作完图文应该把图文的信息清空
		case 0:msgType = "text"; 
			keyReply.setTitle("");
			keyReply.setDescription("");
			keyReply.setPicUrl("");
			keyReply.setUrl("");
			//文本直接存数据库
			keyReplyBiz.addKeyWords(keyReply);
			jm.setCode(1);
			 return jm;
		
		case 4:msgType = "news"; 
			//把文本内容清空
			keyReply.setContent("");
			//文本直接存数据库
			keyReplyBiz.addKeyWords(keyReply);
			jm.setCode(1);
			 return jm;

		}
		return jm;
	}
	
	
	
	
	
	//微信关键字设置回复  图片 语音 视频
	@RequestMapping(value="/back/keyMsgToReply.action")
	public JsonModel keyMsgToReplyImg(KeyReply keyReply,HttpServletRequest request,HttpServletResponse resp,@RequestParam("file")CommonsMultipartFile file2) throws ServletException,IOException, ParseException, java.text.ParseException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		JsonModel jm = new JsonModel();
		
		//媒体id设置为全局变量
		String mediaId = null;
		
		
		
		//文件大小必须限制
		long fileSize = file2.getSize();
		//获取类型的不同
		String msgType = null;
		switch(keyReply.getKtype()){
			case 1:msgType = "image";
					keyReply.setTitle("");
					keyReply.setDescription("");
			
					if(fileSize>2097152){
						jm.setCode(0);
						jm.setMsg("文件大小大于2M,请压缩文件大小");
						return jm;
					}
				  break;
			case 2:msgType = "voice";
					keyReply.setTitle("");
					keyReply.setDescription("");
					if(fileSize>2097152){
						jm.setCode(0);
						jm.setMsg("文件大小大于2M,请压缩文件大小");
						return jm;
					}
				  break;
			case 3:msgType = "video"; 
					if(fileSize>10485760){
						jm.setCode(0);
						jm.setMsg("文件大小大于10M,请压缩文件大小");
						return jm;
					}
				  break;
		}
		

		
		
		
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
       CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
               request.getSession().getServletContext());
       //检查form中是否有enctype="multipart/form-data"
       if(multipartResolver.isMultipart(request))
       {
           //将request变成多部分request
           MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
          //获取multiRequest 中所有的文件名
           Iterator iter=multiRequest.getFileNames();
            
           while(iter.hasNext())
           {
               //一次遍历所有文件
               MultipartFile file=multiRequest.getFile(iter.next().toString());
               //截取文件后缀名
               int t = file.getOriginalFilename().lastIndexOf(".");
               String last = file.getOriginalFilename().substring(t); // .png
               if(file!=null)
               {
            	  
            	   String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            	 
				   //取出tomcat路径
				   Calendar c=Calendar.getInstance();
				   String tomcatdir=request.getRealPath("/");
				   File tomcatFile=new File(tomcatdir);
				   File webapppath=tomcatFile.getParentFile();
					
				   File picpath=new File(webapppath,"wxpic"+File.separator+c.get(Calendar.YEAR)+
							File.separator+(c.get(Calendar.MONTH)+1)+File.separator+fileName+last);
            	   String filepath = picpath.toString();
            	   
                   //上传 到指定磁盘
                   file.transferTo(new File(filepath)); 
                   //在上传到微信服务器！
                   String access_token = GetAccessToken.getAT(accessTokenZpBiz);
                   mediaId = WeixinUtil.upload(filepath, access_token, msgType);
                   //此id需要存数据库
                   System.out.println( "上传完成"+mediaId );
                   keyReply.setMediaId(mediaId);
                   //保存到数据库
                   keyReplyBiz.addKeyWords(keyReply);
                   
               }  
           }
       }

       
       
       long  endTime=System.currentTimeMillis();
       System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
       if( endTime>=0 ){
    	   jm.setCode(1);
       }else{
    	   jm.setCode(0);
    	   jm.setMsg("请检查上传文件是否规范！");
       }
       return jm;
	}
	
	
	
}
