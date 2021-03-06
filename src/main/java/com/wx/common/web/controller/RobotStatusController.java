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
public class RobotStatusController {
	
	
	@Resource(name="robotStatusBizImpl")
	private RobotStatusBiz robotStatusBiz;
	
	@Resource(name="wxWallStatusBizImpl")
	private WxWallStatusBiz wxWallStatusBiz;
	
	
	@RequestMapping("/back/updateRobotStatus.action")
	public void updateRobotStatus(HttpServletRequest request){
		JsonModel jm = new JsonModel();
		WxWallStatus ws = wxWallStatusBiz.findWxWallStatus();
		//先查找是否有机器人
		RobotStatus rs = robotStatusBiz.findRobotStatus();
		if(rs==null){
			//加一个
			robotStatusBiz.addRobotStatus();
		}
		String status = request.getParameter("status");
		Integer t = 0;
		if( status.equals("checked") ){
			t = 1;
			if(ws!=null) {
				wxWallStatusBiz.updateWxWallStatus(0);
			}
		}else if(status.equals("unchecked")){
			t = 0;
			if(ws!=null) {
				wxWallStatusBiz.updateWxWallStatus(1);
			}
		}
		robotStatusBiz.updateRobotStatus( t );
		
	}
	
	// 查找机器人是否使用状态
	@RequestMapping("/back/findRobotStatus.action")
	public JsonModel findRobotStatus(){
		JsonModel jm = new JsonModel();
		RobotStatus rs = robotStatusBiz.findRobotStatus();
		Integer it = 0;
		if(rs!=null){
			it = rs.getStatus();
		}
		jm.setObj(it);
		return jm;
	}
	
}
