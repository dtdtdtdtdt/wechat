package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.Sign;

public interface SignBiz {
	//根据用户名查找该签到人信息
	public Sign findSignByFromUserName( Sign sign  );
	
	//插入签到的人
	public void addSign(Sign sign);
	
	//根据用户名修改数据
	public void updateSign( Sign sign );
	
	//发现所有签到用户
	public List<Sign> findAllSign(Sign sign );
}
