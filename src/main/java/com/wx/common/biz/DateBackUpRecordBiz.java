package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.DateBackUpRecord;

public interface DateBackUpRecordBiz {
	
	//管理员备份数据库
	public void addDateBackUpRecordByOperator(DateBackUpRecord dateBackUpRecord);
	
	//系统自动备份数据库
	public void addDateBackUpRecordBySystem(DateBackUpRecord dateBackUpRecord);
	
	//更新有效日期
	public void updateDateBackUpRecordDeadline();
	
	//查找所有备份记录
	public List<DateBackUpRecord> findAllDateBackUpRecord(DateBackUpRecord dateBackUpRecord);
	
	//根据路径删除备份记录  同时删除文件哦
	public void delDatabaseBackUpRecordByFilePath(DateBackUpRecord dateBackUpRecord);
	
	//查找备份保留时间==0的天数
	public List<DateBackUpRecord> findAllDateBackUpRecordWhereDeadline();
	
	
	
}
