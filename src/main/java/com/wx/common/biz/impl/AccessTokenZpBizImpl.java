package com.wx.common.biz.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.springframework.stereotype.Service;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.dao.BaseDao;
import com.wx.common.utils.WeixinUtil;

@Service
public class AccessTokenZpBizImpl implements AccessTokenZpBiz {

	@Resource(name = "baseDao")
	private BaseDao baseDao;

	// 查询token
	@Override
	public AccessTokenZp serachAccessToken() {
		AccessTokenZp zp = (AccessTokenZp) baseDao.findOne(new AccessTokenZp(), "serachAccessToken");
		if (zp != null) {
			return zp;
		}
		return null;
	}

	// 增加accesstoken
	@Override
	public void addAccessToken(AccessTokenZp accesstoken) {
		baseDao.save(accesstoken, "addAccessToken");

	}

	// 更新
	@Override
	public void updateAccessToken() throws ParseException, IOException {
		AccessTokenZp token = WeixinUtil.getAccessToken();
		baseDao.update(token, "updateAccessToken");
	}

}
