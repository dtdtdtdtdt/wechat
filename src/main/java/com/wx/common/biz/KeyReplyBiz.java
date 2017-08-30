package com.wx.common.biz;

import java.util.List;

import com.wx.common.bean.KeyReply;

public interface KeyReplyBiz {
	
	//插入关键字
	public boolean addKeyWords(KeyReply KeyReply);
	
	//查找关键字查找关键字所有内容
	public KeyReply findKeyWords(String keyWords);
	
	//查找所有关键字
	public List<KeyReply> findAllKeyWords(KeyReply KeyReply);
	
	//查找关键字数量
	public int findKeyWordsCount();
	
	//根据kid删除该关键字
	public void deleteKeyWords(KeyReply KeyReply);
	
	
	
}
