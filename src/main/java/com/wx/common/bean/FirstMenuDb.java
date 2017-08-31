package com.wx.common.bean;

import java.io.Serializable;

public class FirstMenuDb implements Serializable {


	private static final long serialVersionUID = 3876291537943970973L;
	
	private Integer fid;
	private String type;
	private String name;
	private String key;
	private String url;
	private String event;

	//用户菜单实时显示  一级菜单二二级菜单名
	private String fname;
	private String sname;
	
	
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Integer getFid() {
		return fid;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	
}
