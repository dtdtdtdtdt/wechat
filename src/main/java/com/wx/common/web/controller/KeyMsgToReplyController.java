package com.wx.common.web.controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
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


import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
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
	
	//文本微信关键字设置回复
	@RequestMapping(value="/back/keyMsgToReply.action")
	public JsonModel keyMsgToReply(KeyReply keyReply,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SmartUploadException{
		JsonModel jm = new JsonModel();
		//获取关键字
//		System.out.println( keyReply.getKeywords()+"\n"+keyReply.getContent() );
		//设置文本类型为0
		keyReply.setKtype(0);
		
		boolean b = keyReplyBiz.addKeyWords(keyReply);
		if( b ){
			jm.setCode(1);		
		}else{
			jm.setCode(0);
			jm.setMsg("增加失败！");
		}
		jm.setCode(1);	
		return jm;
	}
	
	
	//图片,视频,语音微信关键字设置回复
	@RequestMapping(value="/back/keyMsgToReplyImg.action")
	public JsonModel keyMsgToReplyImg(KeyReply keyReply,HttpServletRequest request,HttpServletResponse resp,@RequestParam("file")CommonsMultipartFile file2) throws ServletException, IOException, SmartUploadException, ParseException, java.text.ParseException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		JsonModel jm = new JsonModel();
		
//		System.out.println( keyReply.getKeywords()+ keyReply.getKtype());
		//文件大小必须限制
		long fileSize = file2.getSize();
		//获取类型的不同
		String msgType = null;
		switch(keyReply.getKtype()){
			case 1:msgType = "image";
					if(fileSize>2097152){
						jm.setCode(0);
						jm.setMsg("文件大小大于2M,请压缩文件大小");
						return jm;
					}
				  break;
			case 2:msgType = "voice";
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
            	   //需要修改路径   尽量设置为c盘
                   String path="G:/test/"+fileName+last;
                   //上传 到指定磁盘
                   file.transferTo(new File(path)); 
                   //在上传到微信服务器！
                   String access_token = GetAccessToken.getAT(accessTokenZpBiz);
                   String mediaId = WeixinUtil.upload(path, access_token, msgType);
                   //此id需要存数据库
                   System.out.println( "上传完成"+mediaId );
                   keyReply.setMediaId(mediaId);
                   //保存到数据库
                   keyReplyBiz.addKeyWords(keyReply);
                   
               }  
           }
       }
       long  endTime=System.currentTimeMillis();
       System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
       if( endTime>=0 ){
    	   jm.setCode(1);
       }else{
    	   jm.setCode(0);
    	   jm.setMsg("请检查上传文件是否规范！");
       }
       return jm;
	}
	
	
	
}
