package com.wechat.test;

import java.io.File;

public class Mytest4 {

	public static void main(String[] args) {
		String str = "123.png";
		int t = str.lastIndexOf(".");
		System.out.println( str.substring(t)  );
	}

}
