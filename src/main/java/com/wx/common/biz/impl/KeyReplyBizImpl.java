package com.wx.common.biz.impl;

import java.util.List;

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
	
	//查找所有关键字
	@Override
	public List<KeyReply> findAllKeyWords(KeyReply KeyReply) {
		List<KeyReply> list = baseDao.findAll(KeyReply, "findAllKeyWords");
		if( list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	//查找关键字数量  就是查找所有关键字！
	@Override
	public int findKeyWordsCount() {

		List<KeyReply> list = baseDao.findAll(new KeyReply(), "findAllKeyWords");
		if( list!=null&&list.size()>0){
			return list.size();
		}

		return 0;
	}
	
	//根据kid删除关键字
	@Override
	public void deleteKeyWords(KeyReply KeyReply) {
		baseDao.del(KeyReply, "deleteKeyWordsByKid");
		
	}

}
