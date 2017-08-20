package com.wx.user.biz.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.springframework.stereotype.Service;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.bean.UserLx;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.dao.BaseDao;
import com.wx.common.utils.WeixinUtil;
import com.wx.user.biz.UserBiz;

import net.sf.json.JSONObject;

@Service
public class UserBizImpl implements UserBiz {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenBiz;
	
	@SuppressWarnings("unchecked")
	@Override
	public void refreshUser() {
		JSONObject jsonObject=new JSONObject();
		UserLx wu=new UserLx();
		AccessTokenZp at=new AccessTokenZp();
		at=accessTokenBiz.serachAccessToken();
		try {
			jsonObject=WeixinUtil.doGetStr(WeixinUtil.USERLIST_URL.replace("ACCESS_TOKEN", at.getAccess_token()).replace("NEXT_OPENID", ""));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接失败!");
		}
		wu.setTotal(jsonObject.getInt("total"));
		wu.setCount(jsonObject.getInt("count"));
		String openidList=jsonObject.getJSONObject("data").getString("openid");
		String [] openCls=null;
		if(openidList.length()<28){
			wu.setOpenid(openidList);
			wu=this.getWechatUser(wu);
			System.out.println(wu);
		}else{
			openCls = openidList.substring(2,openidList.length()-2).split("\",\"");
			for(int i=0;i<openCls.length;i++){
				wu.setOpenid(openCls[i]);
				wu=this.getWechatUser(wu);
				//判断是否关注了公众号  关注了 即可进行数据库操作
				if(wu.getSubscribe()==1){
					//先查询是否存在该用户
					UserLx wu2=new UserLx();
					wu2=(UserLx) baseDao.findOne(wu, "findUserByOpenid"); //ud.findUserByOpenid(wu);
					//不存在则插入  存在即更新
					if(wu2!=null){
						baseDao.update(wu, "updateUser");//ud.updateUser(wu);
					}else{
						baseDao.save(wu, "addUser");//ud.addUser(wu);
					}
				}
			}
		}
	}

	@Override
	public UserLx getWechatUser(UserLx userLx) {
		JSONObject jsonObject=new JSONObject();
		UserLx wu=new UserLx();
		AccessTokenZp at=new AccessTokenZp();
		at=accessTokenBiz.serachAccessToken();
		try {
			jsonObject=WeixinUtil.doGetStr(WeixinUtil.USER_URL.replace("OPENID", userLx.getOpenid()).replace("ACCESS_TOKEN", at.getAccess_token()));
		} catch (Exception e) {
			System.out.println("请求失败!");
			e.printStackTrace();
		}
		wu.setTotal(userLx.getTotal());
		wu.setCount(userLx.getCount());
		wu.setSubscribe(jsonObject.getInt("subscribe"));
		wu.setOpenid(jsonObject.getString("openid"));
		wu.setNickname(jsonObject.getString("nickname"));
		wu.setSex(jsonObject.getInt("sex"));
		wu.setLanguage(jsonObject.getString("language"));
		wu.setCity(jsonObject.getString("city"));
		wu.setProvince(jsonObject.getString("province"));
		wu.setCountry(jsonObject.getString("country"));
		wu.setHeadimgurl(jsonObject.getString("headimgurl"));
		wu.setSubscribe_time(jsonObject.getInt("subscribe_time"));
		wu.setRemark(jsonObject.getString("remark"));
		wu.setGroupid(jsonObject.getInt("groupid"));
		wu.setTagid_list(jsonObject.getString("tagid_list"));
		return wu;
	}

	@Override
	public List<UserLx> findAllUser(UserLx userLx) {
		List<UserLx> userList=new ArrayList<UserLx>();
		userList=baseDao.findAll(userLx, "findAllUser");
		return userList;
	}

	@Override
	public void addUser(UserLx userLx) {
		baseDao.save(userLx, "addUser");
	}

	@Override
	public void deleteUser(UserLx userLx) {
		baseDao.del(userLx, "deleteUser");
	}

	@Override
	public UserLx findUser(UserLx userLx) {
		userLx=(UserLx) baseDao.findOne(userLx, "findUser");
		return userLx;
	}

	@Override
	public void updateUser(UserLx userLx) {
		baseDao.update(userLx, "updateUser");
	}

	@Override
	public void updateSubUser(UserLx userLx) {
		baseDao.update(userLx, "updateSubUser");
		
	}

}
