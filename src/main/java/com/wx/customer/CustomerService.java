package com.wx.customer;

import java.io.Serializable;

//微信客服  最多10个 
public class CustomerService implements Serializable {

	private static final long serialVersionUID = -8320427908561928222L;

	private String kf_account;  //客服账号
	private String nickname;

	public String getKf_account() {
		return kf_account;
	}
	public void setKf_account(String kf_account) {
		this.kf_account = kf_account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	
}
