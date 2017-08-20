package com.wx.common.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.dao.BaseDao;

@Service
@Transactional 
public class SecondMenuDbBizImpl implements SecondMenuDbBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	//增加二级菜单
	@Override
	public void addSecondMenu(SecondMenuDb secondMenuDb) {
		baseDao.save(secondMenuDb, "addSecondMenu");
		
	}
	
	//查找所有二级菜单  根据fid
	@Override
	public List<SecondMenuDb> findAllSecondMenuByFid(SecondMenuDb secondMenuDb) {
		List<SecondMenuDb> list = baseDao.findAll(secondMenuDb, "findAllSecondMenuByFid");
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	//查询二级菜单数量  需要根据对应的一级菜单fid
	@Override
	public int findAllSecondMenuCountByFid(SecondMenuDb secondMenuDb) {
		List<SecondMenuDb> list = baseDao.findAll(secondMenuDb, "findAllSecondMenuByFid");
		if(list!=null&&list.size()>0){
			return list.size();
		}
		return 0;
	}


	// <!-- 删除二级菜单 根据sid -->
	@Override
	public void deleteSecondBySid(int sid) {
		SecondMenuDb secondMenuDb = new SecondMenuDb();
		secondMenuDb.setSid(sid);
		baseDao.del(secondMenuDb, "deleteSecondBySid");
		
	}

	//查询二级菜单数量根据分组fid
	@Override
	public List<SecondMenuDb> findSecondCountGroupBy() {
		List<SecondMenuDb> list = baseDao.findAll(new SecondMenuDb(), "findSecondCountGroupBy");
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	



}
