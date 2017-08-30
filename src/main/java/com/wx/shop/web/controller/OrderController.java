package com.wx.shop.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wx.common.bean.UserLx;
import com.wx.common.bean.WxShop;
import com.wx.common.bean.WxshopOrder;
import com.wx.common.bean.WxshopOrderItem;
import com.wx.common.bean.WxshopUser;
import com.wx.shop.biz.WxShopBiz;
import com.wx.shop.biz.WxshopOrderBiz;
import com.wx.shop.biz.WxshopOrderItemBiz;
import com.wx.shop.biz.WxshopUserBiz;
import com.wx.shop.entity.CartItem;


//订单处理
@RestController
public class OrderController {
	
	//用于微信点餐菜单
	@Resource(name="wxShopBizImpl")
	private WxShopBiz wxShopBiz;
	//订餐用户
	@Resource(name="wxshopUserBizImpl")
	private WxshopUserBiz wxshopUserBiz;
	//订餐订单
	@Resource(name="wxshopOrderBizImpl")
	private WxshopOrderBiz wxshopOrderBiz;
	//订餐详情
	@Resource(name="wxshopOrderItemBizImpl")
	private WxshopOrderItemBiz wxshopOrderItemBiz;
	
	
	
//	@Transactional
	//立即支付处理  加事务  
	@RequestMapping(value="/shop/doOrder.action")
	public ModelAndView doOrder(HttpServletRequest request,HttpSession session) {
		ModelAndView mad = new ModelAndView();
		//取出用户
		UserLx user = (UserLx) session.getAttribute("user");
		//先判断用户是否有填写地址信息
		WxshopUser wuser = new WxshopUser();
		wuser.setOpenid( user.getOpenid() );
		WxshopUser wu = wxshopUserBiz.findWxShopUserByOpenid(wuser);
		if(wu.getTel()==null||wu.getTel().equals("")) {
			mad.setViewName("../../shop/jsp/address");
			return mad;
		}
		//session来自toOrder.action  防止刷新重复提交
		if( session.getAttribute("status")!=null ) {
			//取出商品名称 价格 库存
			WxShop ws = (WxShop) session.getAttribute("ws");
			//思路  获取订单编号和商品fid 更改订单状态  cart中移除fid
			//先从session中取出购物车Map 取出商品的编号
			Map<Integer,CartItem> cart = new HashMap<Integer,CartItem>();
			//判断session中是否有商品   	
			if( session.getAttribute("cart")!=null){
				cart = (Map<Integer, CartItem>) session.getAttribute("cart");
			}
			//查找商品id
			int fid = Integer.parseInt( request.getParameter("fid") );

			
			//插入一条订单订单操作
			WxshopOrder wo = new WxshopOrder();
			String  roid =  request.getParameter("roid");
			wo.setRoid(roid);
			wo.setUserid( wu.getUserid() );
			wxshopOrderBiz.addWxshopOrder(wo);
			//插入订单详情操作
			WxshopOrderItem wxshopOrderItem = new WxshopOrderItem();
			//前台传过来的价格
			double normprice = Double.parseDouble(  request.getParameter("normprice") );
			int num = Integer.parseInt(  request.getParameter("num") );
			wxshopOrderItem.setRoid(roid);
			wxshopOrderItem.setFid( fid  );
			wxshopOrderItem.setDealprice( normprice*num );
			wxshopOrderItem.setNum( num );  
			wxshopOrderItemBiz.addWxshopOrderitem(wxshopOrderItem);
			//执行完后清除session
			session.setAttribute("status", null);
			session.removeAttribute("status");
			//移除购物车中的数据
			cart.remove(fid);
			//需要查询已经购买付款的订单信息  重新赋值一下
			//根据openid查到userid
			WxshopOrder wxshopOrder = new WxshopOrder();
			wxshopOrder.setUserid(  wu.getUserid() );
			//需要查询已经购买付款的订单信息
			List<WxshopOrder> list = wxshopOrderBiz.findAllWxshopOrderByUserid(wxshopOrder);
			if(list!=null&&list.size()>0) {
				session.setAttribute("allWxshopOrder", list);
			}
			
			
		}

		//买好后跳转到订单页！
		mad.setViewName("../../shop/jsp/order");
		return mad;
	}
	
	//确认收货处理
	@RequestMapping(value="/shop/confirm.action")
	public ModelAndView doConfirm(HttpServletRequest request,HttpSession session) {
		ModelAndView mad = new ModelAndView();
		//获取roid进行订单状态修改！
		String roid = request.getParameter("roid");
		WxshopOrder wxshopOrder = new WxshopOrder();
		wxshopOrder.setRoid(roid);
		wxshopOrderBiz.updateWxshopOrderConfirmstatus(wxshopOrder);
		//更新数据！
		//根据openid查到userid
		UserLx user = (UserLx) session.getAttribute("user");
		WxshopUser wxshopUser = new WxshopUser();
		wxshopUser.setOpenid( user.getOpenid() );
		WxshopUser newwxshopUser = wxshopUserBiz.findWxShopUserByOpenid(wxshopUser);
		WxshopOrder newwxshopOrder = new WxshopOrder();
		wxshopOrder.setUserid(  newwxshopUser.getUserid() );
		//需要查询已经购买付款的订单信息
		List<WxshopOrder> list = wxshopOrderBiz.findAllWxshopOrderByUserid(newwxshopOrder);
		if(list!=null&&list.size()>0) {
			session.setAttribute("allWxshopOrder", list);
		}
		
		mad.setViewName("../../shop/jsp/order");
		return mad;
	}
	
	//加入购物车处理  和 立即购买
	@RequestMapping(value="/shop/toCart.action")
	public ModelAndView toCart(HttpServletRequest request,HttpSession session) {
		//设置status 用于判断立即支付时是否刷新导致重复提交数据
		session.setAttribute("status", 1);
		ModelAndView mad = new ModelAndView();
		//思路 
		//取出购物车中的id  和当前商品的id比较，若有则购物车数据量加一，否则数量设置为一
		//先从session中取出购物车Map 取出商品的编号
		Map<Integer,CartItem> cart = new HashMap<Integer,CartItem>();
		 
		//判断session中是否有商品   	
		if( session.getAttribute("cart")!=null){
			cart = (Map<Integer, CartItem>) session.getAttribute("cart");
		}
		//查找商品id
		int fid = Integer.parseInt( request.getParameter("fid") );
		//从session中取所有的菜品  
		List<WxShop> WxShopList = (List<WxShop>) session.getAttribute("wxshop");
		//从WxShopList中取出fid代表的商品
		WxShop WxShop = null;
		//首先判断是否有WxShopList
		if( WxShopList!=null){
			for( WxShop rf :WxShopList){
				if( rf.getFid()==fid){
					WxShop = rf;
					break;
				}
			}
		}
		//判断购物车是否已经买过  根据Map中的键去判断
		CartItem ci = null;
		if( cart.containsKey(fid)){ //根据fid 查找
			ci = cart.get( fid);
			ci.setNum( ci.getNum()+1); //数量+1
			
		}else{ //购物车没有该商品 则新建一个
			ci = new CartItem();
			ci.setNum(1);
		}
		ci.setWxShop(WxShop); //
		//将ci存入map中
		cart.put(fid, ci);
		//在存入session中
		session.setAttribute("cart", cart);
		

		
		mad.setViewName("../../shop/jsp/order");
		return mad;
	}
	
	//取消订单处理   
	@RequestMapping(value="/shop/cancelOrder.action")
	public ModelAndView cancelOrder(HttpServletRequest request,HttpSession session) {
		ModelAndView mad = new ModelAndView();
		//获取fid 从cart中移除
		int fid = Integer.parseInt(  request.getParameter("fid")  );
		Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
		cart.remove(fid);
		//重新存入session中
		session.setAttribute("cart", cart);
		mad.setViewName("../../shop/jsp/order");
		return mad;
	}
	
	//确认收货地址处理
	@RequestMapping(value="/shop/confirmAddresss.action")
	public ModelAndView confirmAddresss(HttpServletRequest request,HttpSession session) {
		ModelAndView mad = new ModelAndView();
		//取出用户   暂时不处理实际信息填写 
		UserLx user = (UserLx) session.getAttribute("user");
		WxshopUser wxshopUser = new WxshopUser();
		wxshopUser.setOpenid(user.getOpenid());
		wxshopUserBiz.updateWxShopUserByOpenid(wxshopUser);
	
		
		mad.setViewName("../../shop/jsp/order");
		return mad;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}	
