package com.wx.user.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.City;
import com.wx.common.dao.BaseDao;
import com.wx.user.biz.CityBiz;

@Service
public class CityBizImpl implements CityBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Override
	public List<City> findAllCity() {
		return baseDao.findAll(City.class, "findAllCity");
	}

	@Override
	public Integer findValueByCity(City city) {
		int value=(int) baseDao.findOne(city, "findValueByCity");
		return value;
	}

}
