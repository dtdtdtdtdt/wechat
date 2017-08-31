package com.wx.common.bean;

import java.io.Serializable;

public class SubscribeReply implements Serializable {

	private static final long serialVersionUID = -8742131483761746975L;

	private Integer sid;
	private String keywords;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
	
	
}
