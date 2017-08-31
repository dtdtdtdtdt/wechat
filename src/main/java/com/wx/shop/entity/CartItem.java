package com.wx.shop.entity;

import java.io.Serializable;

import com.wx.common.bean.WxShop;

public class CartItem implements Serializable {

	private static final long serialVersionUID = -4059465944200417797L;
	
	
	private WxShop wxShop;
	private Integer num;
	private Double smallcount;
	public WxShop getWxShop() {
		return wxShop;
	}
	public void setWxShop(WxShop wxShop) {
		this.wxShop = wxShop;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getSmallcount() {
		return wxShop.getRealprice()*num;
	}

	
	
	
	
}
