package com.wx.role.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.Role;
import com.wx.common.dao.BaseDao;
import com.wx.role.biz.RoleBiz;

@Service
public class RoleBizImpl implements RoleBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public List<Role> findAllRole() {
		List<Role> list=new ArrayList<Role>();
		list=baseDao.findAll(Role.class, "findAllRole");
		return list;
	}

	@Override
	public void addRole(Role role) {
		baseDao.save(role, "addRole");
	}

	@Override
	public void deleteRole(Role role) {
		baseDao.del(role, "deleteRoleByRole");
	}

}
