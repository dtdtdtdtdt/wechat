package com.wx.shop.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.UserLx;
import com.wx.common.bean.WxShop;
import com.wx.common.bean.WxshopUser;
import com.wx.common.utils.WeixinUtil;
import com.wx.shop.biz.WxShopBiz;
import com.wx.shop.biz.WxshopUserBiz;
import com.wx.user.biz.UserBiz;

import net.sf.json.JSONObject;

@RestController
public class CallBackController {
	
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	//用于微信点餐菜单
	@Resource(name="wxShopBizImpl")
	private WxShopBiz wxShopBiz;
	
	
	
	//订餐用户
	@Resource(name="wxshopUserBizImpl")
	private WxshopUserBiz wxshopUserBiz;
	
	
	
	@RequestMapping(value="/shop/callBack.action",method = RequestMethod.GET)
	public void callBack(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException {
		String code = req.getParameter("code");
		//通过使用code获取 	通过code换取网页授权access_token
		String url ="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+WeixinUtil.APPID
				+ "&secret="+WeixinUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		
		//将请求的结果转成json格式
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
//		System.out.println( jsonObject );

		String openid  = jsonObject.getString("openid");
		String token = jsonObject.getString("access_token");
		//获取用户信息接口调用
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo"
				+ "?access_token="+token
				+ "&openid="+openid
				+ "&lang=zh_CN";
		
		JSONObject userinfo = WeixinUtil.doGetStr(infoUrl);
//		System.out.println( userinfo );
		
		//根据openid搞事情    获取用户名微信名  和头像
		UserLx user = new UserLx();
		user.setOpenid(openid);
		UserLx u = userBiz.findUser(user);
		//判断数据库是否有该用户
		WxshopUser wuser = new WxshopUser();
		wuser.setOpenid( u.getOpenid() );
		WxshopUser wu = wxshopUserBiz.findWxShopUserByOpenid(wuser);
		if(wu==null) {
			//插入openid信息
			wxshopUserBiz.addWxshopUser(wuser);
		}
		//存session
		HttpSession session = req.getSession();
		session.setAttribute("user", u);
		//查所有菜单存application
		WxShop wxShop = new WxShop();
		List<WxShop> list = wxShopBiz.findAllWxShop(wxShop);
		if(list!=null&&list.size()>0) {
			session.setAttribute("wxshop", list);
		}
		//跳转页面
		resp.sendRedirect("jsp/index.jsp");
	}
	
	
}
