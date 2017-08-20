package com.wx.common.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.Admin;
import com.wx.common.bean.Role;
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

	@Override
	public List<Admin> findAllAdmins() {
		List<Admin> list=new ArrayList<Admin>();
		list =baseDao.findAll(Admin.class, "findAllAdmins");
		return list;
	}

	@Override
	public void addAdmins(Admin admin) {
		baseDao.save(admin, "addAdmins");
	}

	@Override
	public void deleteAdmins(Admin admin) {
		baseDao.del(admin, "deleteAdmins");
		
	}

	@Override
	public void updateAdmins(Admin admin) {
		baseDao.update(admin, "updateAdmins");
	}

	@Override
	public List<Role> findMenuByRole(Admin admin) {
		List<Role> list=new ArrayList<Role>();
		Role role=new Role();
		role.setRole(admin.getRole());
		list=baseDao.findAll(role, "findMenuByRole");
		return list;
	}

	@Override
	public Admin findAdminByAid(Admin admin) {
		admin=(Admin) baseDao.findOne(admin, "findAdminByAid");
		return admin;
	}

}
