package com.wechat.test;

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
		
		String s = "JZg2xpzOe9IqyI7oGw31wq3N0ZJnwEgPOxnEk87dntoV2kaNDSo-tAiV4gCfFLq41ygOCQK31ySJjNtBzHhWr_a5QxhSKwZn3nNpOv6oHUuoxXip9X3gZQmZ43e3AP2ZDEBaAFAWXU";
		
		System.out.println( s.length() );
	}
	
}
