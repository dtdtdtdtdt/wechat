package com.wx.common.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//用于用户签到时间处理工具类
public class CommonDateUtils implements Serializable {

	private static final long serialVersionUID = 5076615837037863543L;
	
	/**
	 * 结果 样式  Mon Aug 07 19:41:40 CST 2017
	 * 将数据库中的时间格式化一下  方便获取时间戳  
	 * @param str
	 * @return
	 * @throws ParseException 
	 * 形参样式2017-08-13 00:00:00.0
	 */
	public static Date StrDateFormat(String str) throws ParseException{
		String s = str.replaceAll("-", " ").replace(".0", "");
		DateFormat df = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		return df.parse(s);
	}
	
	//获取当天时间戳
	public static Timestamp  getTodayStartTimeStamp(){
      Calendar c = Calendar.getInstance();   
      c.setTime(new Date());   
      c.set(Calendar.HOUR_OF_DAY, 0);   
      c.set(Calendar.MINUTE, 0);   
      c.set(Calendar.SECOND, 0); 
      Timestamp t = new Timestamp( c.getTime().getTime() );
      return t;
	}
	//从数据库中取出获取当天日期凌晨时间戳
	//str样式形参样式2017-08-13 00:00:00.0
	public static long getDateBaseTimeStamp(String str) throws ParseException{
      String [] s =  str.split(" ");
      String st = s[0];  //2017-08-13
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date date = df.parse(  st );
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      long timestamp = cal.getTimeInMillis();
      return timestamp;
	}
	
}
