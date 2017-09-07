package com.wx.source.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wx.common.bean.Picture;
import com.wx.common.dao.BaseDao;
import com.wx.source.biz.PictureBiz;

@Service
@Transactional
public class PictureBizImpl implements PictureBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Picture> findPicture(Picture picture) {
		List<Picture> list =new ArrayList<Picture>();
		list =baseDao.findAll(picture, "findPicture");
		return list;
	}

	@Override
	public void addPicture(Picture picture) {
		this.baseDao.save(picture, "addPicture");	
	}

	@Override
	public void delPicture(Picture picture) {
		this.baseDao.del(picture, "delPicture");
	}

}
