package com.wx.shop.biz;

import java.util.List;

import com.wx.common.bean.WxshopOrder;

public interface WxshopOrderBiz {
	
	//增加订单
	public void addWxshopOrder(WxshopOrder wxshopOrder);
	
	//查找已经付款购买的订单信息
	public List<WxshopOrder> findAllWxshopOrderByUserid(WxshopOrder wxshopOrder);
	
	//修改订单确认状态
	public void updateWxshopOrderConfirmstatus(WxshopOrder wxshopOrder);
	
}
