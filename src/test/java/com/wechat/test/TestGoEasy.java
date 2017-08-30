package com.wechat.test;

import io.goeasy.GoEasy;

public class TestGoEasy {
	public static void main(String[] args) {
		GoEasy goEasy = new GoEasy("BC-e44baa9b32d64f40abf5cddeffa3aa54");
		goEasy.publish("BS-8ecca8bee3204a06a16f5d584262bee0", "你好呀！");
		
	}
}
