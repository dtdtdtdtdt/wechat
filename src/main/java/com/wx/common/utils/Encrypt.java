package com.wx.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具 md5加密出来的长度是32位 sha加密出来的长度是40位
 */
public class Encrypt {

	public static void main(String[] args) {

		String src = "123";
		System.out.println(MD5(src).length());

		System.out.println(SHA(src).length());
	}

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		return DigestUtils.md5Hex(str);
	}

	/**
	 * 对字符串进行sha1加密
	 */
	public static String SHA(String str) {
		return DigestUtils.shaHex(str);
	}

	/**
	 * 对字符串进行base64加密
	 */
	public static String encodeBase64(String str) {
		return Base64.encodeBase64String(str.getBytes());
	}

	/**
	 * 对字符串进行base64解密
	 */
	public static String decodeBase64(String str) {
		return new String(Base64.decodeBase64(str));
	}

}
