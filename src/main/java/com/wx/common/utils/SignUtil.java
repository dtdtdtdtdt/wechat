package com.wx.common.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;

import com.wx.common.bean.Sign;
import com.wx.common.biz.SignBiz;

//签到工具类  
public class SignUtil implements Serializable {
	
	public static void sign( String  fromUserName,String toUserName,SignBiz signBiz,PrintWriter out ){
		
    	//签到
    	Sign sign = new Sign();
    	sign.setFromUserName(fromUserName);
//    	System.out.println( sign );
    	//根据用户名进行数据库查询是否有该用户
    	Sign s = signBiz.findSignByFromUserName(sign);
    	//如果没有则是第一次签到
    	if( s==null ){
    		Sign newSign = new Sign();
    		newSign.setFromUserName(fromUserName); //签到人
    		newSign.setIntegration(1);  //签到总积分
    		newSign.setSignCount(1);  //签到次数
    		newSign.setSignHistory(1);  //签到历史   注意！签到时间由数据库自己写
    		//插入数据
    		signBiz.addSign(newSign);
			//推送签到图文
			WxSignNewsMsg.sendWxSignNewsMsg(  newSign , fromUserName, toUserName, out);
    	}else{
    		//获取签到次数   总积分  签到时间
    		Integer signCount = s.getSignCount();   //签到次数
    		Integer integration = s.getIntegration();  //总积分
    		Timestamp ts = s.getLastModifytime();   //最近签到时间

			//获取今天的时间戳
			Timestamp todayStartTimeStamp = CommonDateUtils.getTodayStartTimeStamp();
			//如果今天没有签到过
			if(  todayStartTimeStamp.after(ts) ){
        		//计算是否断签过  根据当天的时间减去数据库中最近签到日期当天的凌晨时间戳  
        		long dtime = 0;
				try {
					dtime = CommonDateUtils.getDateBaseTimeStamp(  s.getLastModifytime().toString() );
//					dtime = CommonDateUtils.StrDateFormat(  s.getLastModifytime().toString() ).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//应该为目前时间戳减去签到数据库凌晨时间戳
				final  long missDays= (System.currentTimeMillis()- dtime)/(24*60*60*1000);  //连续签到则等于 1

				//如果MissDays == 1  则说明前一天已经签到连续签到 
				if( missDays == 1 ){
					s.setSignCount( s.getSignCount()+1  ); //连续签到天数加一
					s.setSignHistory( s.getSignHistory()+1 ); //签到历史  用于记录总签单天数
					//解决积分问题  根据签到天数判断积分问题
					switch( signCount+1 ){
						case 1:s.setIntegration( s.getIntegration()+1 ); break;
						case 2:s.setIntegration( s.getIntegration()+2 ); break;
						case 3:s.setIntegration( s.getIntegration()+4 ); break;
						case 4:s.setIntegration( s.getIntegration()+8 ); break;
						case 5:s.setIntegration( s.getIntegration()+16 ); break;
						case 6:s.setIntegration( s.getIntegration()+32 ); break;
						case 7:s.setIntegration( s.getIntegration()+64 ); break;    
						default : s.setIntegration( s.getIntegration()+64 ); break; 
					}
				}else{
					//否则连续签到次数变为1 
					s.setSignCount(1);
					s.setIntegration(  s.getIntegration()+1 );
				}			
				//更新数据库
				signBiz.updateSign(s);
    			//推送签到图文
    			WxSignNewsMsg.sendWxSignNewsMsg(s, fromUserName, toUserName, out);
			}else{  //今天签到过了！
    			//推送签到文本
    			WxSignNewsMsg.alreadySignNewsMsg(s, fromUserName, toUserName, out);
			}
    	}
		
		
		
		
	}
	
}
