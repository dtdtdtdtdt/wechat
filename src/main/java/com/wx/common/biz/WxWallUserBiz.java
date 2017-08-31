package com.wx.common.biz;

import com.wx.common.bean.WxWallUser;

public interface WxWallUserBiz {
	
	//插入数据
	public void addWxWallUser(WxWallUser wxWallUser);
	
	//更新装态
	public void updateWxWallUserStatus(WxWallUser  wxWallUser);
	
	//根据openid查找
	public WxWallUser findWxWallUserByFromUserName(String  fromUserName);
	
	
}	
