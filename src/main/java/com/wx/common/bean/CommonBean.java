package com.wx.common.bean;

import java.io.Serializable;


//用于分页
public class CommonBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer pages=1;
	private Integer pagesize=50;
	private String orderby;	//select * from xxx order by 列名 desc/asc;
	private String orderway;
	private Integer start;

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getOrderway() {
		return orderway;
	}

	public void setOrderway(String orderway) {
		this.orderway = orderway;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "CommonBean [pages=" + pages + ", pagesize=" + pagesize + ", orderby=" + orderby + ", orderway="
				+ orderway + ", start=" + start + "]";
	}
	
	
	
	
	

}
