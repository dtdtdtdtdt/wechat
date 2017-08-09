package com.wx.message;

import java.io.Serializable;
//基本的回复的信息
public class BaseMessage implements Serializable{
	
	private static final long serialVersionUID = 3045809587155075009L;
	
	
	private String ToUserName;//	
	private String FromUserName;	//
	private long   CreateTime;	//
	private String MsgType;	// 消息类型
	
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}
	
	
	
	
	
	
}
