package com.wechat.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.biz.FirstMenuDbBiz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:beans.xml")
public class MyTestAllMenu {

	@Autowired
	ApplicationContext ac;
	
	@Test
	public void test() {
		FirstMenuDbBiz fmdb = (FirstMenuDbBiz) ac.getBean("firstMenuDbBizImpl");
		List<FirstMenuDb> list = fmdb.findFirstAndSecondMenu();
		for(FirstMenuDb fm:list) {
			if(!fm.getType().equals("sub_button")) {
				System.out.println( fm.getFname());
			}
		}
		for(FirstMenuDb fm:list) {
			if(fm.getType().equals("sub_button")) {
				System.out.println( fm.getFname());
				break;
			}
		}
		for(FirstMenuDb fm:list) {
			if(fm.getType().equals("sub_button")) {
				System.out.println( fm.getSname());
			}
		}
		
	}
	
	
}
