package com.wx.user.biz;

import java.util.List;

import com.wx.common.bean.City;

public interface CityBiz {
	
	/**
	 * 查询所有中国城市
	 * @return
	 */
	public List<City> findAllCity();
	
	/**
	 * 根据所有城市查询人数
	 * @param city
	 * @return
	 */
	public Integer findValueByCity(City city);
}
