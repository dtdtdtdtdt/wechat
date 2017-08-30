package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.RobotStatus;
import com.wx.common.biz.RobotStatusBiz;
import com.wx.common.dao.BaseDao;

@Service
public class RobotStatusBizImpl implements RobotStatusBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	@Override
	public void addRobotStatus() {
		baseDao.save(new RobotStatus(), "addRobotStatus");
	}

	@Override
	public RobotStatus findRobotStatus() {
		RobotStatus rs = (RobotStatus) baseDao.findOne(new RobotStatus(), "findRobotStatus");
		if(rs!=null){
			return rs;
		}
		return null;
	}

	@Override
	public void updateRobotStatus(Integer status) {
		RobotStatus rs = new RobotStatus();
		rs.setStatus(  status );
		baseDao.update( rs, "updateRobotStatus");

	}

}
