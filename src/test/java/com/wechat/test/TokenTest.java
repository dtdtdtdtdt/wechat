package com.wechat.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.ParseException;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.utils.WeixinUtil;

import net.sf.json.JSONObject;

public class TokenTest {
	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
		AccessTokenZp token = WeixinUtil.getAccessToken();
		System.out.println( token.getAccess_token()  );
		
		
		
		
		
		
		
		
	}
}
