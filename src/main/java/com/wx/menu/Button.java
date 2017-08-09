package com.wx.menu;

import java.io.Serializable;

//按钮基类
public class Button implements Serializable {

	private static final long serialVersionUID = 6554119737699937231L;
	
	private String name;
	private String type;
	private Button [] sub_button;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
