package com.wx.common.bean;

import java.io.Serializable;

public class City implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String city;
	private String lng;
	private String lat;
	private Integer value;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
}
