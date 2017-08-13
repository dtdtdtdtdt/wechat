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
import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;

@RestController
public class MyTestController {
	
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	
	@RequestMapping(value="/test.action")
	public JsonModel testUp(HttpServletRequest request,@RequestParam("file")CommonsMultipartFile file2) throws IllegalStateException, IOException, ParseException, java.text.ParseException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		
		JsonModel jm = new JsonModel();

		//获取关键字keywords
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String keywords = multipartRequest.getParameter("keyWords");
		System.out.println( "获取到了吧！"+keywords );
		
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
                   String path="G:/test/"+fileName+last;
                   //上传 到指定磁盘
                   file.transferTo(new File(path)); 
                   //在上传到微信服务器！
                   String access_token = GetAccessToken.getAT(accessTokenZpBiz);
                   String mediaId = WeixinUtil.upload(path, access_token, "image");
                   //此id需要存数据库
                   System.out.println( "上传完成"+mediaId );
                   
               }
                
           }
          
       }
       long  endTime=System.currentTimeMillis();
       System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
       if( endTime>=0 ){
    	   jm.setCode(1);
       }else{
    	   jm.setCode(0);
    	   jm.setMsg("程序异常！");
       }	
		return jm;
	}
	
}
