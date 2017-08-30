package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.SubscribeReply;

public interface SubscribeReplyBiz {
	
	//增加关注回复
	public void addSubscribeReply(SubscribeReply subscribeReply);
	
	//查找所有关注回复设置
	public List<SubscribeReply> allSubscribeReply();
	
	//根据sid删除关注回复设置
	public void delSubscribeReplyBySid(SubscribeReply subscribeReply);
	
}
