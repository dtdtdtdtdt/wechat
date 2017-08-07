package com.wx.user.biz;

import java.util.List;

import com.wx.user.bean.User;

public interface UserBiz {
	/**
	 * 请求用户列表
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 查询某个用户
	 * @return
	 */
	public User getUserByOpenid();
}
