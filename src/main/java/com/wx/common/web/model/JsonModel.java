package com.wx.common.web.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

public class JsonModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer code;  //状态码
	private String msg;  //返回消息
	private Object obj;
	
	private Integer total;//总记录数
	private Integer pages;//当前第几页
	private Integer pageSize;//每页xx条
	private List<T> rows;//记录集合
	private JSONObject json;
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public JsonModel(int code,String msg,Object obj){
		super();
		this.code=code;
		this.msg=msg;
		this.obj=obj;
	}
	
	public JsonModel(){
		super();
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "JsonModel [code=" + code + ", msg=" + msg + ", obj=" + obj + ", total=" + total + ", pages=" + pages
				+ ", pageSize=" + pageSize + ", rows=" + rows + ", json=" + json + "]";
	}

}
