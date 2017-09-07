package com.wx.shop.biz;

import java.util.List;

import com.wx.common.bean.WxshopUser;

//订餐用户信息
public interface WxshopUserBiz {
	
	
	//插入用户订餐信息
	public void addWxshopUser(WxshopUser wxshopUser);
	
	//根据openid查找用户信息
	public WxshopUser findWxShopUserByOpenid(WxshopUser wxshopUser);
	//根据用户openid修改收货地址信息
	public void updateWxShopUserByOpenid(WxshopUser wxshopUser);
	
	//查询所有商城用户
	public List<WxshopUser> findAllWxShopUser();
}
