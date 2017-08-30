package com.wx.common.biz;

import java.util.List;
import java.util.Map;

public interface DateBackUpBiz {
	
	//查找所有表
	public List<String> findAllTable();
	
	//查询所有表的数量
	public int findAllTableCount();
	
}
