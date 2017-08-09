package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wx.common.bean.Sign;
import com.wx.common.biz.SignBiz;
import com.wx.common.dao.BaseDao;
import com.wx.common.dao.impl.BaseDaoMybatisImpl;



@Service
@Transactional
public class SignBizImpl implements SignBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	
	//根据用户名查找该签到人信息
	@Override
//	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Sign findSignByFromUserName(Sign sign) {
		Sign s = (Sign) baseDao.findOne(sign, "findSignByFromUserName");  //说明这句为空...？
		if( s!=null ){
			return s;
		}
		return null;
	}

	//根据用户名修改数据
	@Override
	public void addSign(Sign sign) {
		baseDao.save(sign, "addSign");
	}
	
	//根据用户名修改数据
	@Override
	public void updateSign(Sign sign) {
		baseDao.update(sign, "updateSign");  //返回空类型
		
	}





	
	
	
	
	
	
	
}
