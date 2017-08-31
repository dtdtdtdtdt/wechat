package com.wx.common.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.DateBackUp;
import com.wx.common.biz.DateBackUpBiz;
import com.wx.common.dao.BaseDao;

@Service
public class DateBackUpBizImpl implements DateBackUpBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//查找所有表哦
	@Override
	public List<String> findAllTable() {
		List<String> list = baseDao.findAll(new DateBackUp(), "findAllTable");
		if( list!=null&&list.size()>0 ) {
			return list;
		}
		return null;
	}
	
	//查询所有表的数量
	@Override
	public int findAllTableCount() {
		List<String> list = baseDao.findAll(new DateBackUp(), "findAllTable");
		if( list!=null&&list.size()>0 ) {
			return list.size();
		}
		return 0;
	}

}
