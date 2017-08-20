package com.wx.common.bean;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer rid;
	private String role;
	private String menu;
	private String mtitle;
	public String getMtitle() {
		return mtitle;
	}
	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
