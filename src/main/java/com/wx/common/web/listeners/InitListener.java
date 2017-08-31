package com.wx.common.web.listeners;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.http.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.WeixinUtil;

@WebListener
public class InitListener implements ServletContextListener{
	
	private ApplicationContext ac;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 取application
		ServletContext application = sce.getServletContext();
		// 工具类: tomcat启动时-> ContextLoaderListener -> 读取 contextConfigLocation指定的
		// beans.xml -> 加载整个应用程序中的bean ioc, -> di -> context -> 存到application
		// 利用spring提代的 WebApplicationContextUtils类来获取 spring 容器
		ac = WebApplicationContextUtils.getWebApplicationContext(application);
		AccessTokenZpBiz atb=(AccessTokenZpBiz) ac.getBean("accessTokenZpBizImpl");
		if( atb.serachAccessToken()==null ) {
			try {
				AccessTokenZp atz = WeixinUtil.getAccessToken();
				atb.addAccessToken(atz);
			} catch (ParseException | IOException e) {
				System.out.println("AccessToken获取失败！请检查网络" );
				e.printStackTrace();
			}
		}
		try {
			atb.updateAccessToken();
		} catch (Exception e) {
			System.out.println("AccessToken更新失败!");
			e.printStackTrace();
		}
	}


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
