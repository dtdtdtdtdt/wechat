package com.wx.common.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;


public class FileUpload extends HttpServlet{

	private String allowedFilesList = "jpg,jpeg,png,bmp,gif";
	private String deniedFilesList = "bat,sh,exe,class,html,js,css";
	private long maxFileSize = 200000000;
	private long totalMaxFileSize = 5 * maxFileSize;

	public Map<String,String> uploadFiles(PageContext pageContext,HttpServletRequest request) throws ServletException, IOException, SQLException, SmartUploadException{
		
		Map<String,String> map =new HashMap<String,String>();
		
		SmartUpload su = new SmartUpload();
		su.initialize(pageContext);
		su.setCharset("utf-8");

		// 定义允许上传文件类型
		su.setAllowedFilesList(allowedFilesList);
		// 定义不允许上传文件类型
		su.setDeniedFilesList(deniedFilesList);
		// 单个文件最大限制
		su.setMaxFileSize(maxFileSize);
		// 所有文件总容量限制
		su.setTotalMaxFileSize(totalMaxFileSize);

		su.upload();
		
		//取参数  Request是smartupload的request
		Request re = su.getRequest();
		Enumeration<String> enu =re.getParameterNames();
		while(enu.hasMoreElements()){
			String pn =enu.nextElement();
			map.put(pn,re.getParameter(pn));
		}
		
		//取上传文件列表
		Files files =su.getFiles();
		int count =files.getCount();		
		if(files!=null && count>0){
			for(int i=0;i<count;i++){
				//取Tomcat路径
				Calendar c =Calendar.getInstance();
				String tomcatdir =request.getRealPath("/");
				File tomcatFile =new File(tomcatdir);
				File webapppath =tomcatFile.getParentFile();
				
				File picpath =new File(webapppath,"pic"+File.separator+c.get(Calendar.YEAR)+File.separator
						+(c.get(Calendar.MONTH)+1)+File.separator);
				
				String weburl ="../../../pic/"+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/";  //网页上访问图片的路径
			
				//判断文件是否存在  不存在则创建
				if(picpath.exists() ==false){
					picpath.mkdirs();
				}
				//获取单个上传文件   只取列表中的第一个文件  写全路径  防止系统以为是java.io.File类
				com.jspsmart.upload.File file =files.getFile(i);
				
				String filePrefixName =genNewFilePrefixName();
				
				//取出原文件的后缀名
				String extName =file.getFileExt();
				//拼接新的文件名
				String fileName =filePrefixName+"."+extName;
				
				weburl +=fileName;
				String destFilePathName =picpath +"/"+fileName;
				
				file.saveAs(destFilePathName,SmartUpload.SAVE_PHYSICAL);   //以物理路径来保存
				
				String filedName =file.getFieldName();
				String oldFileName =file.getFieldName();
				
				map.put(filedName+"_weburl",weburl);
				map.put(filedName+"_destFilePathName",destFilePathName);
				map.put(filedName+"_fileName",fileName);
			}
		}		
		return map;
	}
	
	//生成新的文件名
	private String genNewFilePrefixName() {
		Date d =new Date();
		SimpleDateFormat df =new SimpleDateFormat("yyyyMMddHHmmss");
		String filePrefixName =df.format(d);    //文件前缀名
		return filePrefixName;
	}
}


