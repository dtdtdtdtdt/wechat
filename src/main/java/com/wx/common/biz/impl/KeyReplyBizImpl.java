package com.wx.common.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.KeyReply;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.dao.BaseDao;

@Service
public class KeyReplyBizImpl implements KeyReplyBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	
	@Override
	public boolean addKeyWords(KeyReply  KeyReply) {

		baseDao.save(KeyReply, "addKeyWords");
		return true;
	}
	
	//根据关键字查找回复的内容
	@Override
	public KeyReply findKeyWords(String keyWords) {
		KeyReply keyReply = new KeyReply();
		keyReply.setKeywords(keyWords);
		
		KeyReply kr = (KeyReply) baseDao.findOne(keyReply, "findReplyByKeywords");
		if(kr!=null ){
			return kr;
		}
		return null;	
	}

}
