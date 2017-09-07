package com.wx.source.biz;

import java.io.IOException;
import org.apache.http.ParseException;
import com.wx.common.bean.MassMpnews;
import com.wx.common.bean.Mpnews;
import net.sf.json.JSONObject;

public interface MassBiz {
	
	public String[] getOpenID() throws ParseException, IOException;
	
	//组装图文群发消息
	public MassMpnews initMass(Mpnews mpnews,String msgtype,int send_ignore_reprint) throws ParseException, IOException;
	
	//获取发送图文消息后 微信返回的内容 判断群发是否成功
	public JSONObject MassMpnewsMessage(String massMpnews) throws ParseException, IOException;
}
