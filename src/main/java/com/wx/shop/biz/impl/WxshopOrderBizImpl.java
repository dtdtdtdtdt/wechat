package com.wx.shop.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.WxShop;
import com.wx.common.bean.WxshopOrder;
import com.wx.common.dao.BaseDao;
import com.wx.shop.biz.WxshopOrderBiz;

@Service
public class WxshopOrderBizImpl implements WxshopOrderBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	@Override
	public void addWxshopOrder(WxshopOrder wxshopOrder) {
		baseDao.save(wxshopOrder, "addWxshopOrder");

	}

	//查询所有已经购买的商品订单
	@Override
	public List<WxshopOrder> findAllWxshopOrderByUserid(WxshopOrder wxshopOrder) {
		List<WxshopOrder> list = baseDao.findAll(wxshopOrder, "findAllWxshopOrderByUserid");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}

	//修改订单确认状态
	@Override
	public void updateWxshopOrderConfirmstatus(WxshopOrder wxshopOrder) {
		baseDao.update(wxshopOrder, "updateWxshopOrderConfirmstatus");
	}

}
