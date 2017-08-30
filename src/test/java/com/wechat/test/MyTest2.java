package com.wechat.test;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.Sign;
import com.wx.common.biz.SignBiz;
import com.wx.common.biz.impl.SignBizImpl;
import com.wx.common.utils.TransFormUtil;
import com.wx.menu.Button;
import com.wx.menu.Menu;



public class MyTest2 {

	
	public static void main(String[] args) {
		
		String str="C:\\apache-tomcat-7.0.77\\webapps\\backupFile\\2017\\8\\2017-08-30-09-02-26-accesstoken-sd.sql";
		System.out.println(  str.substring(0, str.lastIndexOf("\\")) );
	}
	
}
