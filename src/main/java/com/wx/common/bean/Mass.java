package com.wx.common.bean;

import java.io.Serializable;
import java.util.Arrays;

//群发消息的父类
public class Mass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String[] touser;		//是	  填写图文消息的接收者，一串OpenID列表，OpenID最少2个，最多10000个

	private String msgtype; 	//是   群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard


	public String[] getTouser() {
		return touser;
	}

	public void setTouser(String[] touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	@Override
	public String toString() {
		return "Mass [touser=" + Arrays.toString(touser) + ", msgtype=" + msgtype + "]";
	}

}
