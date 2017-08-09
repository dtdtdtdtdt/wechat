package com.wechat.test;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.bean.Sign;
import com.wx.common.biz.SignBiz;
import com.wx.common.utils.CommonDateUtils;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:beans.xml")
public class MyTest {

	@Autowired
	ApplicationContext ac;
	
	
	@Test
	public  void  test() throws ParseException{
		SignBiz sb =  (SignBiz) ac.getBean("signBizImpl");
		
		Sign s = new Sign();
		s.setFromUserName("oeFt8wYmSWPGqc8BJKxBQDU_px7U");
		
		Sign sign = sb.findSignByFromUserName(s);
		
		
		System.out.println("最近签到日期"+ sign.getLastModifytime() );  //签到时间
//		System.out.println(  sign.getLastModifytime().toString() );


		
		//今天凌晨时间戳
		Timestamp todayStartTimeStamp = CommonDateUtils.getTodayStartTimeStamp();
		System.out.println( "今天时间戳"+todayStartTimeStamp );
		//获取数据库时间转换为str 
		long dtime =  CommonDateUtils.StrDateFormat(  sign.getLastModifytime().toString() ).getTime();
		
		
		
		//计算断签天数	如果大于2则说明是断签的人				//当前时间戳 1502097484772    1501952400000
		final  long missDays= ( System.currentTimeMillis() - dtime )/(24*60*60*1000);
		//System.currentTimeMillis()
		System.out.println( missDays );
		
		

		
		
		

		

		
		
		
		
		
	}
}
