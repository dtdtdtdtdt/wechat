package com.wechat.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTest6 {
	public static void main(String[] args) throws ParseException {
		//项目路径
		String str = "2017-08-25 14:57:04.0";
		System.out.println( str );
		
		DateFormat df = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
//		System.out.println( df.parse("Aug 25, 2017 2:57:04 PM") );
		
		System.out.println( str.substring(0, str.lastIndexOf(".")) );
	}
}
