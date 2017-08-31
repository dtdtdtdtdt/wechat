package com.wx.common.biz;

import com.wx.common.bean.RobotStatus;

public interface RobotStatusBiz {
	
	// 初始化一个机器人状态
	public void addRobotStatus();
	
	//查找机器人状态信息
	public RobotStatus findRobotStatus();
	
	//修改机器人状态】
	public void updateRobotStatus(Integer status);
	
	
}	
