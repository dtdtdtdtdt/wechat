package com.wx.common.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.RobotStatus;
import com.wx.common.bean.WxWallStatus;
import com.wx.common.biz.RobotStatusBiz;
import com.wx.common.biz.WxWallStatusBiz;
import com.wx.common.web.model.JsonModel;

@RestController
public class WxWallStatusController {
	
	
	@Resource(name="wxWallStatusBizImpl")
	private WxWallStatusBiz wxWallStatusBiz;
	
	
	@Resource(name="robotStatusBizImpl")
	private RobotStatusBiz robotStatusBiz;
	
	@RequestMapping("/back/updateWxWallStatus.action")
	public void updateRobotStatus(HttpServletRequest request){
		JsonModel jm = new JsonModel();
		RobotStatus rs = robotStatusBiz.findRobotStatus();
		//先查找有微信墙
		WxWallStatus ws = wxWallStatusBiz.findWxWallStatus();
		if(ws==null){
			//加一个
			wxWallStatusBiz.addWxWallStatus();
		}
		String status = request.getParameter("status");
		Integer t = 0;
		if( status.equals("checked") ){
			t = 1;
			if(rs!=null) {
				robotStatusBiz.updateRobotStatus(0);
			}
		}else if(status.equals("unchecked")){
			t = 0;
			if(rs!=null) {
				robotStatusBiz.updateRobotStatus(1);
			}
		}
		wxWallStatusBiz.updateWxWallStatus(t);
	}
	
	// 查找微信墙是否使用状态
	@RequestMapping("/back/findWxWallStatus.action")
	public JsonModel findRobotStatus(){
		JsonModel jm = new JsonModel();
		WxWallStatus rs = wxWallStatusBiz.findWxWallStatus();
		Integer it = 0;
		if(rs!=null){
			it = rs.getStatus();
		}
		jm.setObj(it);
		return jm;
	}
	
}
