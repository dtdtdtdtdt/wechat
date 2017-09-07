package com.wx.source.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.Mpnews;
import com.wx.common.dao.BaseDao;
import com.wx.source.biz.MpnewsBiz;

@Service
public class MpnewsBizImpl implements MpnewsBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public void addMpnews(Mpnews mpnews) {
		this.baseDao.save(mpnews, "addMediaId");
	}

}
