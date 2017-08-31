package com.wx.common.timetask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.DateBackUpBiz;
import com.wx.common.biz.DateBackUpRecordBiz;
import com.wx.common.utils.DateBackUpUtil;
import com.wx.user.biz.UserBiz;

@Component
public class TimeTask {
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	@Resource(name="dateBackUpBizImpl")
	private DateBackUpBiz dateBackUpBiz;
	
	//备份记录
	@Resource(name="dateBackUpRecordBizImpl")
	private DateBackUpRecordBiz dateBackUpRecordBiz;
	
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
	 * 定时凌晨 更新备份数据有效期  每天减一
	 * 
	 */
	@Scheduled(cron="0 0 0 * * ?")
	public void update(){
		dateBackUpRecordBiz.updateDateBackUpRecordDeadline();
	}
	
	
	/**
	 * 定时备份数据库  每天凌晨两点备份   删除备份数据库  自动判断30天后删除
	 */
	@Scheduled(cron="0 0 2 * * ?")
	public void  backUpDataBase(){
		DateBackUpUtil.systemAutoMaticSaveAll(dateBackUpRecordBiz, dateBackUpBiz);
	}
	

}
