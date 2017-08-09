package com.wx.user.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wx.common.bean.UserLx;
import com.wx.common.dao.BaseDao;
import com.wx.user.biz.UserBiz;

import net.sf.json.JSONObject;

@Service
public class UserBizImpl implements UserBiz {
	private String UserList_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	private String User_URl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void getAndSaveUser() {
		JSONObject jsonObject=new JSONObject();
		UserLx wu=new UserLx();
		//jsonObject=gap.doGet(UserList_URL.replace("ACCESS_TOKEN", at.getAccess_token()).replace("NEXT_OPENID", ""));
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
					//System.out.println(wu.toString());
				}
			}
		}
	}

	@Override
	public UserLx getWechatUser(UserLx userLx) {
		JSONObject jsonObject=new JSONObject();
		UserLx wu=new UserLx();
		//jsonObject=gap.doGet(User_URl.replace("OPENID", wechatUser.getOpenid()).replace("ACCESS_TOKEN", at.getAccess_token()));
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
	public List<UserLx> findAllUser() {
		List<UserLx> userList=new ArrayList<UserLx>();
		userList=baseDao.findAll(UserLx.class, "findAllUser");
		return userList;
	}

	@Override
	public int findUserCount() {
		int count=(int) baseDao.findOne(UserLx.class, "findUserCount");
		return count;
	}

}
