package com.wx.common.timetask;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.user.biz.UserBiz;

@Component
public class TimeTask {
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	/**
	 * 定时凌晨1.00刷新用户
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void refreshUser(){
		userBiz.refreshUser();
		System.out.println("更新用户成功！");
	}
	
	/**
	 * 定时1个小时刷新accessToken
	 */
	@Scheduled(cron="0 0 */1 * * ?")
	public void refreshAccessToken(){
		try {
			accessTokenZpBiz.updateAccessToken();
			System.out.println("更新accessToken成功！");
		} catch (Exception e) {
			System.out.println("accessToken定时更新失败！"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 定时备份数据库
	 */
	public void  backUpDataBase(){
		
	}
	
	/**
	 * 定时删除已备份的数据库
	 */
	public void deleteDataBase(){
		
	}
}
