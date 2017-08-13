package com.wechat.test;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.customer.CustomerService;

import net.sf.json.JSONObject;

//测试客服功能
public class MyTestCustomerService {
	
	


	public  static void main(String [] args)throws ParseException, IOException, java.text.ParseException {
		
		
		String access_token = "hkORPuHv7jAdG8x5CVABlVio0F7dQxMqbCG0PSpU4Wo_EjJYDwDdc-SmiRt1K8uIQ0NI9-On7ROEonG63BRzQDQDDAYCWvaI4I0CWxxrWN48Mccvz5ISYOmK1X-2F3ygHSRfAHARCB";
		//增加客服
		String add = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token="+access_token;
		CustomerService cs = new CustomerService();
		cs.setKf_account("客服妹子@gh_da14b7309b34");
		cs.setNickname("我是客服一");

		//对象转json
//		JSONObject jo = JSONObject.fromObject(cs);
//		System.out.println( jo );
//		//发送post请求
//		JSONObject str = WeixinUtil.doPostStr(add, jo.toString());
//		System.out.println( str );
		
		String s = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token="+access_token;
		JSONObject str2 = WeixinUtil.doGetStr(s);
		System.out.println( str2 );
		
	}
}
