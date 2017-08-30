package com.wx.common.bean;

import java.io.Serializable;
import java.sql.Date;

///订单bean
public class WxshopOrder implements Serializable {

	private static final long serialVersionUID = -504740604875669642L;

	private String roid  ; 
	private Integer userid;
	private Date ordertime ;	
	private Integer paystatus = 1 ;
	private Integer confirmstatus =0;
	//用于查询订单和订单详情显示
	private String name;
	private double dealprice;
	private Integer num;
	private String cover;
	

	
	
	
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRoid() {
		return roid;
	}
	public void setRoid(String roid) {
		this.roid = roid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public Integer getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}
	public Integer getConfirmstatus() {
		return confirmstatus;
	}
	public void setConfirmstatus(Integer confirmstatus) {
		this.confirmstatus = confirmstatus;
	}
	
	
	
}
