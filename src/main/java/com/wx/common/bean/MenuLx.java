package com.wx.common.bean;

import java.io.Serializable;

public class MenuLx implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer mid;
	private String menu;
	private String mtitle;
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getMtitle() {
		return mtitle;
	}
	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}
}
