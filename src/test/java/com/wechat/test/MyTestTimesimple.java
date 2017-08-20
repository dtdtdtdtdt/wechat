package com.wechat.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.wx.common.utils.CommonDateUtils;

public class MyTestTimesimple {
	public static void main(String[] args) throws ParseException {
		

		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(new Date());
		calendar.add(calendar.DATE,-1);//期往增加.整数往推,负数往前移

		
		calendar.set(Calendar.HOUR_OF_DAY, 0);   
		calendar.set(Calendar.MINUTE, 0);   
		calendar.set(Calendar.SECOND, 0); 
		

	      Timestamp t = new Timestamp( calendar.getTime().getTime() );
	      System.out.println( t );
	      
	      
	      
	      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	      Date date = df.parse("2017-08-12");
	      Calendar cal = Calendar.getInstance();
	      cal.setTime(date);
	      long timestamp = cal.getTimeInMillis();
	      System.out.println( "昨日凌晨时间戳"+timestamp );
	      
	      CommonDateUtils.getTodayStartTimeStamp();
	      
	      final  long missDays= (System.currentTimeMillis()- timestamp)/(24*60*60*1000); 
	      System.out.println(  missDays );
//	      Timestamp t2 = new Timestamp( cal.getTime().getTime() );
//	      System.out.println( t2 );
	      
	      
	      

		
	}
}
