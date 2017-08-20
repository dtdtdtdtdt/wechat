package com.wx.menu.biz;

import java.util.List;

import com.wx.common.bean.MenuLx;

public interface MenuBiz {

	/**
	 * 查找所有菜单
	 * @return
	 */
	public List<MenuLx> findAllMenu();
}
