package com.wx.common.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.dao.BaseDao;

@Service
@Transactional 
public class FirstMenuDbBizImpl implements FirstMenuDbBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//增加一级菜单
	@Override
	public void addFirstMenu(FirstMenuDb firstMenuDb) {
		baseDao.save(firstMenuDb, "addFirstMenu");
	}
	
	//查找所有一级菜单
	@Override
	public List<FirstMenuDb> findAllFirstMenu() {
		List<FirstMenuDb> list = baseDao.findAll(new FirstMenuDb(), "findAllFirstMenu");
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	//查询所有一级菜单数量
	@Override
	public int findAllFirstMenuCount() {
		List<FirstMenuDb> list = baseDao.findAll(new FirstMenuDb(), "findAllFirstMenu");
		if(list!=null&&list.size()>0){
			return list.size();
		}
		return 0;
	}
	
	//根据fid查找对应的一级菜单信息
	@Override
	public FirstMenuDb findFirstMenuByFid(int fid) {
		FirstMenuDb fm = new FirstMenuDb();
		fm.setFid(fid);
		FirstMenuDb fmd = (FirstMenuDb) baseDao.findOne(fm, "findFirstMenuByFid");
		if( fmd!=null ){
			return fmd;
		}
		return null;
	}
	
	//根据fid删除该一级菜单
	@Override
	public void deleteFirstMenuByFid(FirstMenuDb firstMenuDb) {
		//不包括子菜单则可以直接删除
		if(firstMenuDb.getType()!="sub_button"){
			baseDao.del(firstMenuDb, "deleteFirstMenuByFid");
		}
		
		
	}

	//查询一级和二级菜单共同查询
	@Override
	public List<FirstMenuDb> findFirstAndSecondMenu() {
		List<FirstMenuDb> list = baseDao.findAll(new FirstMenuDb(), "findFirstAndSecondMenu");
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
	}
	
	

}
