package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.Admin;
import com.wx.common.biz.AdminBiz;
import com.wx.common.dao.BaseDao;

/**
 * 
 * @author 刘翔
 *
 */
@Service
public class AdminBizImpl implements AdminBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	/**
	 * 管理员登录
	 */
	@Override
	public Admin login(Admin admin) {
		admin=(Admin) baseDao.findOne(admin, "AdminLogin");
		return admin;
	}

}
