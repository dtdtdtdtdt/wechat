package com.wx.role.biz;

import java.util.List;

import com.wx.common.bean.Role;

public interface RoleBiz {

	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> findAllRole();
	
	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 根据角色名来删除
	 * @param role
	 */
	public void deleteRole(Role role);
}
