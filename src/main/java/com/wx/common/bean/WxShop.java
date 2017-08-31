package com.wx.common.bean;


//对应商品信息
public class WxShop extends CommonBean{

	private static final long serialVersionUID = 6406180587668046193L;
	
	private Integer fid ;
	private String name ;
	private Double normprice ;
	private Double realprice ;
	private String detail ;  //商品详细介绍  应用ckedictor处理！
	private String cover ;
	
	private String detaila ; //详情页图片1
	private String detailb ; ////详情页图片2
	private Integer stock ; //库存
	
	
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDetaila() {
		return detaila;
	}
	public void setDetaila(String detaila) {
		this.detaila = detaila;
	}
	public String getDetailb() {
		return detailb;
	}
	public void setDetailb(String detailb) {
		this.detailb = detailb;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getNormprice() {
		return normprice;
	}
	public void setNormprice(Double normprice) {
		this.normprice = normprice;
	}
	public Double getRealprice() {
		return realprice;
	}
	public void setRealprice(Double realprice) {
		this.realprice = realprice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	
	
	
	
}
