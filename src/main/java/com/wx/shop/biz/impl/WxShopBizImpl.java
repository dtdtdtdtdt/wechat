package com.wx.shop.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.WxShop;
import com.wx.common.dao.BaseDao;
import com.wx.shop.biz.WxShopBiz;

@Service
public class WxShopBizImpl implements WxShopBiz {

	//查找所有菜品
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public List<WxShop> findAllWxShop(WxShop wxShop) {
		List<WxShop> list = baseDao.findAll(wxShop, "findAllWxShop");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}

	//根据id查找商品
	@Override
	public WxShop findWxShopById(WxShop wxShop) {
		WxShop ws = (WxShop) baseDao.findOne(wxShop, "findWxShopById");
		if(ws!=null) {
			return ws;
		}
		return null;
	}

	@Override
	public int findWxShopCount(WxShop wxShop) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addWxShop(WxShop wxShop) {
		baseDao.save(wxShop, "addWxShop");
	}

	@Override
	public void deleteWxShop(WxShop wxShop) {
		baseDao.del(wxShop, "deleteWxShop");
	}

	@Override
	public void updateWxShop(WxShop wxShop) {
		baseDao.update(wxShop, "updateWxShop");
	}

}
