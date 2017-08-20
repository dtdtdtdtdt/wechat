package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.FirstMenuDb;

public interface FirstMenuDbBiz {
	
	//插入一级菜单
	public void addFirstMenu( FirstMenuDb firstMenuDb);
	
	//查找所有一级菜单
	public List<FirstMenuDb> findAllFirstMenu();
	
	//查询一级菜单数量
	public int findAllFirstMenuCount();
	
	//根据fid查找对应的一级菜单信息
	public FirstMenuDb findFirstMenuByFid(int fid);
	
	//根据fid删除该一级菜单
	public void deleteFirstMenuByFid( FirstMenuDb firstMenuDb );
}
