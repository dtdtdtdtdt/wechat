package com.wx.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;



//签到bean
public class Sign extends CommonBean   {


	private static final long serialVersionUID = -8581879404646312512L;
	
	private Integer sid; 
	private String FromUserName; //--用户名
	private Integer signCount;		//--连续签到次数
	private Integer integration;	//--总积分
	private Timestamp lastModifytime;  //注意保持格式2017-08-07 19:50:02.0  数据库格式 --最后修改时间（签到）
	private long signHistory;	//--签到历史
	private String ext;   //保留字段  不做功能
	private String UserName;  // 用FromUserName查到openid后获取微信名  用于easyui显示数据
	private long Timesformat;   //用于转换lastModifytime 格式，用于easyui显示数据
	private String headUrl;  //用用户头像数据 用于easyui显示数据
	
	
	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public long getTimesformat() {
		return Timesformat;
	}

	public void setTimesformat(long timesformat) {
		Timesformat = timesformat;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

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

	public void setLastModifytime(Timestamp lastModifytime) {
		this.lastModifytime = lastModifytime;
	}
	
	@Override
	public String toString() {
		return "Sign [sid=" + sid + ", FromUserName=" + FromUserName + ", signCount=" + signCount + ", integration="
				+ integration + ", lastModifytime=" + lastModifytime + ", signHistory=" + signHistory + "]";
	}

	
	
	
	
}
