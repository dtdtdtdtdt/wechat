package com.wx.common.biz.impl;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.springframework.stereotype.Service;

import com.wx.common.bean.WxWallUser;
import com.wx.common.biz.WxWallUserBiz;
import com.wx.common.dao.BaseDao;

@Service
public class WxWallUserBizImpl implements WxWallUserBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//插入数据
	@Override
	public void addWxWallUser(WxWallUser wxWallUser) {
		baseDao.save(wxWallUser, "addWxWallUser");
	}

	// 更新状态
	@Override
	public void updateWxWallUserStatus(WxWallUser wxWallUser) {
		baseDao.update(wxWallUser, "updateWxWallUserStatus");
	}

	// 根据openid查找使用微信墙的用户
	@Override
	public WxWallUser findWxWallUserByFromUserName(String  fromUserName) {
		WxWallUser ww = new WxWallUser();
		ww.setFromUserName(fromUserName);
		WxWallUser wwu = (WxWallUser) baseDao.findOne(ww, "findWxWallUserByFromUserName");
		if(wwu!=null) {
			return wwu;
		}
		return null;
	}

}
