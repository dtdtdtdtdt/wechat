package com.wx.shop.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wx.common.bean.WxShop;
import com.wx.shop.biz.WxShopBiz;
import com.wx.shop.entity.CartItem;


@RestController
public class DetailController {
	
	//用于微信点餐菜单
	@Resource(name="wxShopBizImpl")
	private WxShopBiz wxShopBiz;
	
	@RequestMapping(value="/shop/detail.action",method = RequestMethod.GET)
	public ModelAndView doDetail(HttpServletRequest request,HttpSession session) {
		ModelAndView mad = new ModelAndView();
		//查找商品id
		Integer fid = Integer.parseInt( request.getParameter("fid")   )  ;
		WxShop wxShop = new WxShop();
		wxShop.setFid(fid);
		WxShop ws = wxShopBiz.findWxShopById(wxShop);
		session.setAttribute("ws", ws);
		
		//订单号
		Timestamp t = new Timestamp( new Date().getTime() );
		String roid = String.valueOf(t.getTime());
		session.setAttribute("roid", roid);
		//多个视图解析器 会有冲突
		mad.setViewName("../../shop/jsp/detail");
		return mad;
	}
	
}
