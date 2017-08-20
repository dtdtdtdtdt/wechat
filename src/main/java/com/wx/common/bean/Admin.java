package com.wx.common.bean;

import java.io.Serializable;

/**
 * 管理员
 * @author 刘翔
 *
 */
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer aid;
	private String aname;
	private String apwd;
	private String role;
	private String atime;
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getApwd() {
		return apwd;
	}
	public void setApwd(String apwd) {
		this.apwd = apwd;
	}
}
