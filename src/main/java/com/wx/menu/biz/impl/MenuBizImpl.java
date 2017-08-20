package com.wx.menu.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.MenuLx;
import com.wx.common.dao.BaseDao;
import com.wx.menu.biz.MenuBiz;

@Service
public class MenuBizImpl implements MenuBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public List<MenuLx> findAllMenu() {
		return baseDao.findAll(MenuLx.class, "findAllMenu");
	}

}
