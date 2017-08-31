package com.wx.common.bean;

import java.io.Serializable;

public class Robot implements Serializable {

	private static final long serialVersionUID = 9062320697435887117L;
	
	private String 	fromUserName ;
	private Integer status ;
	
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
