package com.wx.common.biz;

import com.wx.common.bean.Robot;

public interface RobotBiz {
	
	//保存使用机器人的用户
	public void addRobotUser(String fromUserName);
	
	//更新状态退出  根据用户名
	public void updateRobotUserLogin(String fromUserName);
	
	//更新状态加入  根据用户名
	public void updateRobotUserOut(String fromUserName);
	
	//查找使用机器人的用户   根据用户名
	public Robot findRobotUser(String fromUserName);
	
	
}
