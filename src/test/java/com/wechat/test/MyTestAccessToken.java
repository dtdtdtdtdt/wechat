package com.wechat.test;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.CommonDateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:beans.xml")
public class MyTestAccessToken {
	
	@Autowired
	ApplicationContext ac;
	
	@Test
	public void test() throws ParseException, org.apache.http.ParseException, IOException{
		
		AccessTokenZpBiz acBiz = (AccessTokenZpBiz) ac.getBean("accessTokenZpBizImpl");
		
		AccessTokenZp accesstoken = acBiz.serachAccessToken();
		
		System.out.println( accesstoken.getAccess_token() );
		
		//进行判断assesstoken是否过期
		long dtime = CommonDateUtils.StrDateFormat( accesstoken.getLastModifytime().toString()).getTime();
		//根据当前时间戳减去数据库中的时间
		double time = (System.currentTimeMillis()-dtime)/3600/1000;
		System.out.println( "时间差为:"+ time);
		//如果大于两小时则更新数据库
		if( time>2.0 ){
			acBiz.updateAccessToken();
		}
		

		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
