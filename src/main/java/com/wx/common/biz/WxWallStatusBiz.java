package com.wx.common.biz;


import com.wx.common.bean.WxWallStatus;

public interface WxWallStatusBiz {
	
	// 初始化一个微信墙状态
	public void addWxWallStatus();
	
	//查找微信墙状态信息
	public WxWallStatus findWxWallStatus();
	
	//修改微信墙状态】
	public void updateWxWallStatus(Integer status);
	
	
}	
