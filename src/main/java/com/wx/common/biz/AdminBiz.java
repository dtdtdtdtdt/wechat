package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.Admin;

public interface AdminBiz {
	
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin);
	
	/**
	 * 查找所有管理员
	 * @param admin
	 * @return
	 */
	public List<Admin> findAllAdmins();
	
	/**
	 * 增加管理员
	 * @param admin
	 */
	public void addAdmins(Admin admin);
	
	/**
	 * 删除管理员
	 * @param admin
	 */
	public void deleteAdmins(Admin admin);

	/**
	 * 查询所有用户数目
	 * @return
	 */
	public int findUserCount();
	
	/**
	 * 根据aid更新管理员
	 * @param admin
	 */
	public void updateAdmins(Admin admin);
}
