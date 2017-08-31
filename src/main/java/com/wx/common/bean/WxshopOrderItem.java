package com.wx.common.bean;

import java.io.Serializable;

public class WxshopOrderItem implements Serializable {

	private static final long serialVersionUID = 1216353479952516407L;
	
	
	private Integer wsid ;
	private String roid  ; //订单编号
	private Integer fid   ;
	private double dealprice ;
	private Integer num  ;
	
	public Integer getWsid() {
		return wsid;
	}
	public void setWsid(Integer wsid) {
		this.wsid = wsid;
	}
	public String getRoid() {
		return roid;
	}
	public void setRoid(String roid) {
		this.roid = roid;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public double getDealprice() {
		return dealprice;
	}
	public void setDealprice(double dealprice) {
		this.dealprice = dealprice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
