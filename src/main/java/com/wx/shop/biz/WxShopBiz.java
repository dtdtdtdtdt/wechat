package com.wx.shop.biz;

import java.util.List;

import com.wx.common.bean.WxShop;

public interface WxShopBiz {
	
	//查找所有菜品
	public List<WxShop> findAllWxShop(WxShop wxShop);
	
	//根据id查找信息
	public WxShop findWxShopById(WxShop wxShop);
	
	//查找需要显示的数目
	public int findWxShopCount(WxShop wxShop);
	
	//增加菜品
	public void addWxShop(WxShop wxShop);
	
	//根据fid删除菜品
	public void deleteWxShop(WxShop wxShop);
	
	//根据fid 更新菜品
	public void updateWxShop(WxShop wxShop);
}
