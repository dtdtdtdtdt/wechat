package com.wx.template;

import java.io.Serializable;

//一条模板包含多条数据，模板数据类封装
public class TemplateData implements Serializable {


	private static final long serialVersionUID = -5779637922297372020L;
    private String value;//模板显示值  
    private String color = "#000000";//模板显示颜色    设置默认为黑色
    
    
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
