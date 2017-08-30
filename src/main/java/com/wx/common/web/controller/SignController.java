package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.DateBackUpRecord;
import com.wx.common.bean.KeyReply;
import com.wx.common.bean.Sign;
import com.wx.common.bean.UserLx;
import com.wx.common.biz.KeyReplyBiz;
import com.wx.common.biz.SignBiz;
import com.wx.user.biz.UserBiz;

@RestController
public class SignController {
	
	
	@Resource(name="signBizImpl")
	private SignBiz signBiz;
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	
	@RequestMapping("/back/findAllSign.action")
	public void findAllSign(HttpServletResponse response,HttpServletRequest request,Sign sign){
		//用于排序
//		System.out.println( request.getParameter("sort")+ request.getParameter("order") );
		
		sign.setOrderby(   request.getParameter("sort")  );
		sign.setOrderway(  request.getParameter("order")  );
		Integer pagesize = Integer.parseInt(  request.getParameter("rows")    );
		sign.setPagesize(  pagesize );
		Integer start = (sign.getPages()-1)*sign.getPagesize();
		sign.setStart( start  );
		
		
		List<Sign> list = signBiz.findAllSign(sign);
		if(list!=null&&list.size()>0) {
			for(Sign s:list) {
				UserLx user = new UserLx();
				user.setOpenid(  s.getFromUserName()  );
				UserLx u = userBiz.findUser(user);
				s.setUserName(  u.getNickname()  );	
				s.setHeadUrl( u.getHeadimgurl() );
				// 修改签到时间格式
				//// 1503644224000  需要去掉最后三个0
				String str = String.valueOf(  s.getLastModifytime().getTime() );
				str = str.substring(0, str.length()-3);
				s.setTimesformat( Long.parseLong(str) ); 
			}
		}
		
		int count = 0;
		if(list!=null&&list.size()>0) {
			count = list.size();
		}
		Gson gson=new Gson();
		//easyui要求的格式
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", count);
		map.put("rows", list);
		String jsonstr=gson.toJson(map);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			System.out.println("响应内容发送失败!");
			e.printStackTrace();
		}
		out.println(jsonstr);
		out.flush();
		out.close();
	}
}
