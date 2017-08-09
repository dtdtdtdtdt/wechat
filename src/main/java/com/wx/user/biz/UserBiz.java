package com.wx.user.biz;

import java.util.List;

import com.wx.common.bean.UserLx;


public interface UserBiz {

	/**
	 * 获取用户并存入数据库
	 * @return
	 */
	public void getAndSaveUser();
	
	/**
	 * 获取单个用户
	 * @param wechatUser
	 * @return
	 */
	public UserLx getWechatUser(UserLx userLx);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<UserLx> findAllUser();
	
	/**
	 * 查询所有用户数目
	 * @return
	 */
	public int findUserCount(); 
}
