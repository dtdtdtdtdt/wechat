package com.wx.common.biz;

import java.io.IOException;

import org.apache.http.ParseException;

import com.wx.common.bean.AccessTokenZp;

public interface AccessTokenZpBiz {
	
	//查找  只有一个公众号！不需要参数
	public AccessTokenZp serachAccessToken(  );
	
	//增加
	public void addAccessToken(AccessTokenZp  accesstoken);
	
	//更新  只有一个公众号！不需要参数
	public void updateAccessToken(  ) throws ParseException, IOException;
	
}
