package com.wx.common.biz.impl;

import java.util.List;

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
		Sign s = (Sign) baseDao.findOne(sign, "findSignByFromUserName"); 
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

	@Override
	public List<Sign> findAllSign(Sign sign) {
		List<Sign> list = baseDao.findAll(sign, "findAllSign");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}





	
	
	
	
	
	
	
}
