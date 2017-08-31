package com.wx.shop.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.WxshopUser;
import com.wx.common.dao.BaseDao;
import com.wx.shop.biz.WxshopUserBiz;

@Service
public class WxshopUserBizImpl implements WxshopUserBiz{

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//增加用户信息
	@Override
	public void addWxshopUser(WxshopUser wxshopUser) {
		baseDao.save(wxshopUser, "addWxshopUser");
		
	}
	
	
	//根据openid查找用户信息
	@Override
	public WxshopUser findWxShopUserByOpenid(WxshopUser wxshopUser) {
		WxshopUser wu = (WxshopUser) baseDao.findOne(wxshopUser, "findWxShopUserByOpenid");
		if(wu!=null) {
			return wu;
		}
		return null;
	}

	//根据openid修改用户收货地址信息
	@Override
	public void updateWxShopUserByOpenid(WxshopUser wxshopUser) {
		baseDao.update(wxshopUser, "updateWxShopUserByOpenid");
	}

}
