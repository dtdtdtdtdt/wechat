package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.Robot;
import com.wx.common.biz.RobotBiz;
import com.wx.common.dao.BaseDao;

@Service
public class RobotBizImpl implements RobotBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//保存使用机器人的用户
	@Override
	public void addRobotUser(String fromUserName) {
		Robot robot = new Robot();
		robot.setFromUserName(fromUserName);
		baseDao.save(robot, "addRobotUser");

	}
	//更新状态  使用
	@Override
	public void updateRobotUserLogin(String fromUserName) {
		Robot robot = new Robot();
		robot.setFromUserName(fromUserName);
		baseDao.update(robot, "updateRobotUserLogin");

	}

	//更新状态  退出
	@Override
	public void updateRobotUserOut(String fromUserName) {
		Robot robot = new Robot();
		robot.setFromUserName(fromUserName);
		baseDao.update(robot, "updateRobotUserOut");
	}
	
	
	//查找使用机器人的用户
	@Override
	public Robot findRobotUser(String fromUserName) {
		Robot robot = new Robot();
		robot.setFromUserName(fromUserName);
		Robot r = (Robot) baseDao.findOne(robot, "findRobotUser");   
		if( r!=null){
			return r;
		}
		return null;
	}


}
