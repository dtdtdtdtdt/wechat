package com.wx.source.biz;

import com.wx.common.bean.Mpnews;

public interface MpnewsBiz {
	//图文消息    添加微信返回的media_id 以便群发
	public void addMpnews(Mpnews mpnews);
}
