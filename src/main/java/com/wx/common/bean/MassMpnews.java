package com.wx.common.bean;

import java.io.Serializable;

//群发文本消息
public class MassMpnews extends Mass implements Serializable {

	private static final long serialVersionUID = 1L;

	private Mpnews mpnews;
	private int send_ignore_reprint;

	public Mpnews getMpnews() {
		return mpnews;
	}

	public void setMpnews(Mpnews mpnews) {
		this.mpnews = mpnews;
	}

	public int getSend_ignore_reprint() {
		return send_ignore_reprint;
	}

	public void setSend_ignore_reprint(int send_ignore_reprint) {
		this.send_ignore_reprint = send_ignore_reprint;
	}

	
}
