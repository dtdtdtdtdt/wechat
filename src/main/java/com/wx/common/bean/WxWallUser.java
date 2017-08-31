package com.wx.common.bean;

import java.io.Serializable;

public class WxWallUser implements Serializable {

	private static final long serialVersionUID = 4136125837829248761L;
	
	private String 	fromUserName;
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
