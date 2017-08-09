package com.wx.menu;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//菜单包括多个按钮
@JsonIgnoreProperties(ignoreUnknown = true) //忽略javabean中没有的属性
public class Menu implements Serializable {

	private static final long serialVersionUID = 5231245510558328298L;
	
	
	
	private Button [] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
	
	
	
}
