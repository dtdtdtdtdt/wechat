package com.wx.shop.biz;

import java.util.List;

import com.wx.common.bean.WxShop;

public interface WxShopBiz {
	
	//查找所有菜品
	public List<WxShop> findAllWxShop(WxShop wxShop);
	
	//根据id查找信息
	public WxShop findWxShopById(WxShop wxShop);
	
	
	
}
