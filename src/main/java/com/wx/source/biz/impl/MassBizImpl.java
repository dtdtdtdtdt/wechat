package com.wx.source.biz.impl;

import java.io.IOException;
import javax.annotation.Resource;
import org.apache.http.ParseException;
import org.springframework.stereotype.Service;
import com.wx.common.bean.AccessTokenZp;
import com.wx.common.bean.MassMpnews;
import com.wx.common.bean.Mpnews;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.dao.BaseDao;
import com.wx.common.utils.WeixinUtil;
import com.wx.source.biz.MassBiz;

import net.sf.json.JSONObject;

@Service
public class MassBizImpl implements MassBiz {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz atzb;
	
	
	AccessTokenZp at =new AccessTokenZp();

	//获取用户的openid  群发是需要
	@Override
	public String[] getOpenID() throws ParseException, IOException{
		at =atzb.serachAccessToken();
		String url =WeixinUtil.GET_OPENID_URL.replace("ACCESS_TOKEN", at.getAccess_token()).replace("NEXT_OPENID","");		
		JSONObject jsonObject =WeixinUtil.doGetStr(url);
		String data =jsonObject.getJSONObject("data").getString("openid");
		int end =data.length()-1;
		data =data.substring(1,end);
		String[] openId =data.split(",");	
		for(int i=0;i<openId.length;i++){
			openId[i] =openId[i].substring(1, openId[i].length()-1);
		}
		return openId;				 
	}
	
	//组装图文群发消息
	@Override
	public MassMpnews initMass(Mpnews mpnews,String msgtype,int send_ignore_reprint) throws ParseException, IOException{		
		MassMpnews massMpnews =new MassMpnews();
		massMpnews.setMpnews(mpnews);
		massMpnews.setTouser(getOpenID());
		massMpnews.setMsgtype(msgtype);
		massMpnews.setSend_ignore_reprint(send_ignore_reprint);		
		return massMpnews;
	}	
	
	//获取发送图文消息后 微信返回的内容 判断群发是否成功
	@Override
	public JSONObject MassMpnewsMessage(String massMpnews) throws ParseException, IOException{
		at =atzb.serachAccessToken();
		String url =WeixinUtil.MASS_URL.replace("ACCESS_TOKEN", at.getAccess_token());		
		JSONObject jsonObject =WeixinUtil.doPostStr(url,massMpnews);
		return jsonObject;		
	}
}
