package com.wx.common.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.Admin;
import com.wx.common.biz.DateBackUpBiz;
import com.wx.common.biz.DateBackUpRecordBiz;
import com.wx.common.utils.DateBackUpUtil;
import com.wx.common.web.model.JsonModel;

@RestController
public class DateBackUpController {
	
	@Resource(name="dateBackUpBizImpl")
	private DateBackUpBiz dateBackUpBiz;
	
	//备份记录
	@Resource(name="dateBackUpRecordBizImpl")
	private DateBackUpRecordBiz dateBackUpRecordBiz;
	
	// 查找数据库中所有的表
	@RequestMapping(value="/back/databaseList.action")
	public JsonModel findAllTable() {
		JsonModel jm = new JsonModel();
		List<String> list = dateBackUpBiz.findAllTable();
		if(list!=null&&list.size()>0) {
			jm.setObj(list);
		}else {
			jm.setObj(null);
		}

		return jm;
	}
	
	//执行备份操作
	@RequestMapping(value="/back/databaseBackUp.action")
	public JsonModel doBackUpDateBase(HttpServletRequest request,HttpSession session) {
		JsonModel jm = new JsonModel();
		//取出备份人
		Admin admin = (Admin) session.getAttribute("admin");
		//备份类型
		String types = request.getParameter("types");
		String table = request.getParameter("table");
		
		String userName = "root";  //数据库用户名 root
		String password = "a";   //数据库用户密码
		String dbName = "wechat";  //数据库名
		if(  DateBackUpUtil.save(userName, password, dbName, table, types,request,admin,dateBackUpRecordBiz, dateBackUpBiz) ) {
			jm.setCode(1);
		}else {
			jm.setCode(0);
		}
		return jm;
	}
	
	//执行还原操作
	@RequestMapping(value="/back/rollBackDatabaseBackUpRecordByFilePath.action")
	public JsonModel doRollBackDateBase(HttpServletRequest request,HttpSession session) {
		JsonModel jm = new JsonModel();
		//取出备份人
		Admin admin = (Admin) session.getAttribute("admin");
		//还原文件路径
		String filePath = request.getParameter("filepath");
		System.out.println( filePath );
		
		String userName = "root";  //数据库用户名 root
		String password = "a";   //数据库用户密码
		String dbName = "wechat";  //数据库名

		if(DateBackUpUtil.rollback(userName, password, dbName, filePath, dateBackUpRecordBiz, dateBackUpBiz) ){
			jm.setCode(1);
		}else {
			jm.setCode(0);
			jm.setMsg("还原失败！");
		}
		
		return jm;
	}
	
	
	
	
	
	
	
	
}
