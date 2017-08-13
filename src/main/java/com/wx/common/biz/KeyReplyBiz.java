package com.wx.common.biz;

import com.wx.common.bean.KeyReply;

public interface KeyReplyBiz {
	
	//插入关键字
	public boolean addKeyWords(KeyReply KeyReply);
	
	//查找关键字
	public KeyReply findKeyWords(String keyWords);
	
	
}
