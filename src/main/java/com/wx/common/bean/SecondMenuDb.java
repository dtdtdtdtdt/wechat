package com.wx.common.bean;

import java.io.Serializable;

public class SecondMenuDb implements Serializable{


	private static final long serialVersionUID = 3579110626772641176L;
	
	private Integer sid;
	private Integer fid;
	private String type;
	private String name;
	private String key;
	private String url;
	private String event;
	private Integer count;  //用于统计每个一级菜单下的二级菜单数量


	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getSid() {
		return sid;
	}

	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
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
