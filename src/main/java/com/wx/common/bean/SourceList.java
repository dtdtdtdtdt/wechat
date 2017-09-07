package com.wx.common.bean;

import java.io.Serializable;

public class SourceList implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type;  	 //是	素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	private Integer offset;	 //是	从全部素材的该偏移位置开始返回，0表示从第一个素材 
	private Integer count;	 //是	返回素材的数量，取值在1到20之间
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "MpnewsList [type=" + type + ", offset=" + offset + ", count=" + count + "]";
	}		 
}
