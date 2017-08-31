package com.wx.shop.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.WxshopOrderItem;
import com.wx.common.dao.BaseDao;
import com.wx.shop.biz.WxshopOrderItemBiz;

@Service
public class WxshopOrderItemBizImpl implements WxshopOrderItemBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public void addWxshopOrderitem(WxshopOrderItem wxshopOrderItem) {
		baseDao.save(wxshopOrderItem, "addWxshopOrderitem");

	}

}
