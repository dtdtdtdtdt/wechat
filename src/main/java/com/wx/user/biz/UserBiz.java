package com.wx.user.biz;

import java.util.List;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.bean.UserLx;

import net.sf.json.JSONObject;


public interface UserBiz {

	/**
	 * 增加单个用户
	 */
	public void addUser(UserLx userLx);
	
	/**
	 * 刷新用户并存入数据库
	 * @return
	 */
	public void refreshUser();
	
	/**
	 * 从微信服务器获取单个用户
	 * @param wechatUser
	 * @return
	 */
	public UserLx getWechatUser(UserLx userLx);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<UserLx> findAllUser(UserLx userLx);
	
	/**
	 * 删除用户
	 * @param userLx
	 */
	public void deleteUser(UserLx userLx);
	
	/**
	 * 从数据库查询用户
	 * @param userLx
	 * @return
	 */
	public UserLx findUser(UserLx userLx);
	
	/**
	 * 更新数据库用户数据
	 * @param userLx
	 */
	public void updateUser(UserLx userLx);
	
	/**
	 * 更新用户关注信息
	 * @param userLx
	 */
	public void updateSubUser(UserLx userLx);
}
