package com.wx.common.bean;

import java.io.Serializable;

public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String name;
	private String url;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
