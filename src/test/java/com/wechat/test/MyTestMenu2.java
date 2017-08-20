package com.wechat.test;

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
public class MyTestMenu2 {
	
	@Autowired
	ApplicationContext ac;
	
	
	
	@Test
	public void test(){
		
		FirstMenuDbBiz fmdb = (FirstMenuDbBiz) ac.getBean("firstMenuDbBizImpl");
		
		FirstMenuDb fm = new FirstMenuDb();
		fm.setName("一级2");
		fm.setType("view");
		fm.setUrl("https://www.nowcoder.com/");
		fm.setKey("");
		//插入数据库
		fmdb.addFirstMenu(fm);
		
		//取出fid
		System.out.println(  fm.getFid() );
		
		
	}
	
	
	
}
