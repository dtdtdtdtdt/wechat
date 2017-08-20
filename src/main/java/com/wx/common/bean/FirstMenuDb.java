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
