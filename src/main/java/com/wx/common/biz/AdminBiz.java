package com.wx.common.biz;

import com.wx.common.bean.Admin;

public interface AdminBiz {
	
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin);
}
