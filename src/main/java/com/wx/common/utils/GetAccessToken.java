package com.wx.common.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.http.ParseException;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;

public class GetAccessToken implements Serializable {
	
	
	public static String getAT( AccessTokenZpBiz  accesstokenBiz  ) throws ParseException, IOException, java.text.ParseException{
		AccessTokenZp accesstoken = accesstokenBiz.serachAccessToken();
		
//		System.out.println( accesstoken.getAccess_token() );
		//进行判断assesstoken是否过期
		long dtime = CommonDateUtils.StrDateFormat( accesstoken.getLastModifytime().toString()).getTime();
		//根据当前时间戳减去数据库中的时间
		double time = (System.currentTimeMillis()-dtime)/3600.0/1000.0;
//		System.out.println( "时间差为:"+ time);
		//如果大于两小时则更新数据库
		if( time>=2.00000000000000 ){
			accesstokenBiz.updateAccessToken();
			//更新数据库后重新获取
			AccessTokenZp newAccesstoken = accesstokenBiz.serachAccessToken();
			return newAccesstoken.getAccess_token();
		}
		return accesstoken.getAccess_token();
	}
}
