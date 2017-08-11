package com.wx.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;



//签到bean
public class Sign implements Serializable {
	private static final long serialVersionUID = -8581879404646312512L;
	
	private Integer sid; 
	private String FromUserName; //--用户名
	private Integer signCount;		//--连续签到次数
	private Integer integration;	//--总积分
	private Timestamp lastModifytime;  //注意保持格式2017-08-07 19:50:02.0  数据库格式 --最后修改时间（签到）
	private long signHistory;	//--签到历史
	private String ext;   //保留字段  不做功能

	
	public Integer getSid() {
		return sid;
	}

	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Integer getSignCount() {
		return signCount;
	}
	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	public Integer getIntegration() {
		return integration;
	}
	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public long getSignHistory() {
		return signHistory;
	}
	public void setSignHistory(long signHistory) {
		this.signHistory = signHistory;
	}
	public Timestamp getLastModifytime() {
		return lastModifytime;
	}

	@Override
	public String toString() {
		return "Sign [sid=" + sid + ", FromUserName=" + FromUserName + ", signCount=" + signCount + ", integration="
				+ integration + ", lastModifytime=" + lastModifytime + ", signHistory=" + signHistory + "]";
	}

	
	
	
	
}
