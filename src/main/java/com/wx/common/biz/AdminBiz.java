package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.Admin;
import com.wx.common.bean.Role;

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
	 * 根据aid更新管理员
	 * @param admin
	 */
	public void updateAdmins(Admin admin);
	
	/**
	 * 根据角色名查询权限
	 * @param admin
	 * @return
	 */
	public List<Role> findMenuByRole(Admin admin);
	
	/**
	 * 根据aid查询用户信息
	 * @param admin
	 * @return
	 */
	public Admin findAdminByAid(Admin admin);
}
