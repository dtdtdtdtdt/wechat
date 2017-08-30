package com.wx.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.wx.common.bean.Admin;
import com.wx.common.bean.DateBackUp;
import com.wx.common.bean.DateBackUpRecord;
import com.wx.common.biz.DateBackUpBiz;
import com.wx.common.biz.DateBackUpRecordBiz;

//数据库备份工具类
public class DateBackUpUtil {
	
	// 参数说明  数据库用户名 数据库密码 数据库名 表名 备份类型  备份人
	public static boolean save(String userName,String password, String dbName,String table,String type,HttpServletRequest request,Admin admin,DateBackUpRecordBiz dateBackUpRecordBiz,DateBackUpBiz dateBackUpBiz) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String fileName = df.format(new Date());
		// 备份文件存放的路径 // 取tomcat路径
	    Calendar c = Calendar.getInstance();
	    String tomcatdir = request.getRealPath("/");
	    File tomcatFile = new File(tomcatdir);
	    File webapppath = tomcatFile.getParentFile();
   
	    String val = type;  //备份类型
	    File filePath = null;
	    String fileNameLast = null;
	    if(type.equals("结构")) {
		    filePath = new File(webapppath, "backupFile" + File.separator + c.get(Calendar.YEAR) + File.separator
					+ (c.get(Calendar.MONTH) + 1) + File.separator+fileName+"-"+table+"-s.sql");
		    fileNameLast = filePath.toString().substring(filePath.toString().lastIndexOf("\\")+1, filePath.toString().length());

		   File Path = new File(filePath.toString().substring(0, filePath.toString().lastIndexOf("\\")));
		   if (Path.exists() == false) {
			   Path.mkdirs();
		   }
	    }else if(type.equals("结构和数据")){ 
		    filePath = new File(webapppath, "backupFile" + File.separator + c.get(Calendar.YEAR) + File.separator
					+ (c.get(Calendar.MONTH) + 1) + File.separator+fileName+"-"+table+"-sd.sql");
		    fileNameLast = filePath.toString().substring(filePath.toString().lastIndexOf("\\")+1, filePath.toString().length());
		   File Path = new File(filePath.toString().substring(0, filePath.toString().lastIndexOf("\\")));
		   if (Path.exists() == false) {
			   Path.mkdirs();
		   }
	    }
//	    System.out.println( filePath  );
	    

	    

	    Runtime runtime = Runtime.getRuntime();
		
		boolean flag = false;
		try {
			// 选择性备份
			if(type.equals("结构")||type.equals("结构和数据")) {
				File fileSql = new File(filePath.toString());
				StringBuffer sbs = new StringBuffer();
				if(table!=null||"".equals(table)){
					if (val.equals("结构和数据")) {
						sbs.append("mysqldump ");
						sbs.append(" -u");
						sbs.append(userName + " ");
						sbs.append("-p" + password + " ");
						sbs.append(dbName);
						sbs.append(" " +table);
					} else if (val.equals("结构")) {
						sbs.append("mysqldump ");
						sbs.append(" -u");
						sbs.append(userName + " ");
						sbs.append("-p" + password + " ");
						sbs.append(" -d ");
						sbs.append(dbName);
						sbs.append(" " +table);
					}
				}else{
					if (val.equals("结构和数据")) {
						sbs.append("mysqldump ");
						sbs.append(" -u");
						sbs.append(userName + " ");
						sbs.append("-p" + password + " ");
						sbs.append(dbName);
					} 
					if (val.equals("结构")) {
						sbs.append("mysqldump ");
						sbs.append(" -u");
						sbs.append(userName + " ");
						sbs.append("-p" + password + " ");
						sbs.append(" -d ");
						sbs.append(dbName);
					}
				}
				
				
				// 调用 mysql 的 cmd:
				Process child = runtime.exec("cmd /c " + sbs.toString());
				
				// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
				InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

				InputStreamReader xx = new InputStreamReader(in, "utf-8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

				String inStr;
				StringBuffer sb = new StringBuffer("");
				String outStr;
				// 组合控制台输出信息字符串
				BufferedReader br = new BufferedReader(xx);
				while ((inStr = br.readLine()) != null) {
					sb.append(inStr + "\r\n");
				}
				outStr = sb.toString();

				// 要用来做导入用的sql目标文件：
				FileOutputStream fout = new FileOutputStream(filePath.toString());
				OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
				writer.write(outStr);
				// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
				writer.flush();
				// 别忘记关闭输入输出流
				//in.close();
				xx.close();
				br.close();
				writer.close();
				fout.close();
				flag = true;
				
				//存入数据库
				DateBackUpRecord dateBackUpRecord = new DateBackUpRecord();
				dateBackUpRecord.setFilename(fileNameLast);
				dateBackUpRecord.setFilepath(filePath.toString());
				dateBackUpRecord.setOperator( admin.getAname() );
				dateBackUpRecord.setType(val);
				dateBackUpRecordBiz.addDateBackUpRecordByOperator(dateBackUpRecord);
				return true;
			}
			
			//备份所有表包括结构和数据  查找所有表
			if( val.equals("备份所有表结构和数据") ) {
				List<String> list =  dateBackUpBiz.findAllTable();
				if( list!=null&&list.size()>0 ) {
					for( String str :list ) {
						StringBuffer bs = new StringBuffer();
						bs.append("mysqldump ");
						bs.append(" -u");
						bs.append(userName + " ");
						bs.append("-p" + password + " ");
						bs.append(dbName);
						bs.append(" " +str);
						
						String fileName2 = new  SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());  //df.format(new Date());
						File filePathAll = new File(webapppath, "backupFile" + File.separator + c.get(Calendar.YEAR) + File.separator
								+ (c.get(Calendar.MONTH) + 1) + File.separator+fileName2+"-"+str+"-sd.sql");
					    String fileNameLast2 = filePathAll.toString().substring(filePathAll.toString().lastIndexOf("\\")+1, filePathAll.toString().length());
					    
					    File PathAll = new File(filePathAll.toString().substring(0, filePathAll.toString().lastIndexOf("\\")));
					   
					    if (PathAll.exists() == false) {
					    	PathAll.mkdirs();
					    }
						
						// 调用 mysql 的 cmd:
						Process child = runtime.exec("cmd /c " + bs.toString());
						
						// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
						InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

						InputStreamReader xx = new InputStreamReader(in, "utf-8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

						String inStr;
						StringBuffer sb = new StringBuffer("");
						String outStr;
						// 组合控制台输出信息字符串
						BufferedReader br = new BufferedReader(xx);
						while ((inStr = br.readLine()) != null) {
							sb.append(inStr + "\r\n");
						}
						outStr = sb.toString();

						// 要用来做导入用的sql目标文件：
						FileOutputStream fout = new FileOutputStream(filePathAll.toString());
						OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
						writer.write(outStr);
						// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
						writer.flush();
						// 别忘记关闭输入输出流
						//in.close();
						xx.close();
						br.close();
						writer.close();
						fout.close();
						//存入数据库
						DateBackUpRecord dateBackUpRecord = new DateBackUpRecord();
						dateBackUpRecord.setFilename(fileNameLast2);
						dateBackUpRecord.setFilepath(filePathAll.toString());
						dateBackUpRecord.setOperator( admin.getAname() );
						dateBackUpRecord.setType("结构和数据");
						dateBackUpRecordBiz.addDateBackUpRecordByOperator(dateBackUpRecord);
						
						
					}
				}
				return true;
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return flag;
	}
	
	  
	//数据库还原    参数说明  数据库用户名   密码  表名  还原文件路径 
	public static boolean rollback(String userName, String password, String dbName,String filePath,DateBackUpRecordBiz dateBackUpRecordBiz,DateBackUpBiz dateBackUpBiz) {
		boolean flag = false;
		try {
			String fPath = filePath; //需要还原的文件路径 
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			StringBuffer sbs = new StringBuffer();
			sbs.append("mysql ");
			sbs.append(" -u ");
			sbs.append(userName + " ");
			sbs.append("-p" + password + " ");
			sbs.append(dbName);

			Process child = rt.exec("cmd /c "+sbs.toString());
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
}
