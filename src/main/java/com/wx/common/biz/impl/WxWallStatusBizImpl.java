package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.RobotStatus;
import com.wx.common.bean.WxWallStatus;
import com.wx.common.biz.WxWallStatusBiz;
import com.wx.common.dao.BaseDao;

@Service
public class WxWallStatusBizImpl implements WxWallStatusBiz {

	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public void addWxWallStatus() {
		baseDao.save(new WxWallStatus(), "addWxWallStatus");

	}

	@Override
	public WxWallStatus findWxWallStatus() {
		WxWallStatus r = (WxWallStatus) baseDao.findOne(new WxWallStatus(), "findWxWallStatus");
		return r;
	}

	@Override
	public void updateWxWallStatus(Integer status) {
		WxWallStatus ws = new WxWallStatus();
		ws.setStatus(status);
		baseDao.update(ws, "updateWxWallStatus");
	}

}
