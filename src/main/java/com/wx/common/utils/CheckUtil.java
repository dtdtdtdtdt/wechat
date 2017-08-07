package com.wx.common.utils;

import java.util.Arrays;

//检验signature对请求进行校验是否来自微信
public class CheckUtil {
	private static final String token = "weixin";
	
	public static boolean checkSignature(String signature,String timestamp ,String nonce){
		String [] arr = new String[]{token,timestamp,nonce};
		//将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		
		//将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuffer sb = new StringBuffer();
		for( int i=0;i<arr.length;i++){
			sb.append( arr[i] );
		}
		//sha加密
		Encrypt encrypt = new Encrypt();
		String str = encrypt.SHA( sb.toString() );
		//开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return str.equals(signature);
	}
}
