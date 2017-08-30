package com.wx.common.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wx.common.bean.SubscribeReply;
import com.wx.common.biz.SubscribeReplyBiz;
import com.wx.common.dao.BaseDao;

@Service
public class SubscribeReplyBizImpl implements SubscribeReplyBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	//增加关注回复设置
	@Override
	public void addSubscribeReply(SubscribeReply subscribeReply) {
		baseDao.save(subscribeReply, "addSubscribeReply");
	}

	//查找所有关注回复设置
	@Override
	public List<SubscribeReply> allSubscribeReply() {
		List<SubscribeReply> list = baseDao.findAll(new SubscribeReply(), "findAllSubscribeReply");
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	//根据sid删除关注回复设置
	@Override
	public void delSubscribeReplyBySid(SubscribeReply subscribeReply) {
		baseDao.del(subscribeReply, "delSubscribeReplyBySid");
	}

}
