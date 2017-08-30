package com.wx.common.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.DateBackUpRecord;
import com.wx.common.biz.DateBackUpRecordBiz;
import com.wx.common.dao.BaseDao;

@Service
public class DateBackUpRecordBizImpl implements DateBackUpRecordBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//管理员备份数据库
	@Override
	public void addDateBackUpRecordByOperator(DateBackUpRecord dateBackUpRecord) {
		baseDao.save(dateBackUpRecord, "addDateBackUpRecordByOperator");

	}
	//系统自动备份数据库
	@Override
	public void addDateBackUpRecordBySystem(DateBackUpRecord dateBackUpRecord) {
		baseDao.save(dateBackUpRecord, "addDateBackUpRecordBySystem");

	}
	//更新有效日期
	@Override
	public void updateDateBackUpRecordDeadline() {
		baseDao.update(new DateBackUpRecord(), "updateDateBackUpRecordDeadline");

	}
	//查找所有备份记录
	@Override
	public List<DateBackUpRecord> findAllDateBackUpRecord(DateBackUpRecord dateBackUpRecord) {
		List<DateBackUpRecord> list = baseDao.findAll( dateBackUpRecord , "findAllDateBackUpRecord");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}
	
	//根据文件路径删除备份记录
	@Override
	public void delDatabaseBackUpRecordByFilePath(DateBackUpRecord dateBackUpRecord) {
		baseDao.del(dateBackUpRecord, "delDatabaseBackUpRecordByFilePath");
	}
	
	//查找备份保留时间==0的天数
	@Override
	public List<DateBackUpRecord> findAllDateBackUpRecordWhereDeadline() {
		List<DateBackUpRecord> list = baseDao.findAll(new DateBackUpRecord(), "findAllDateBackUpRecordWhereDeadline");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}
	
	
	
	
	

}
