package com.wx.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.UserLx;
import com.wx.common.utils.ExcelException;
import com.wx.common.utils.ExcelUtil;
import com.wx.user.biz.UserBiz;

@RestController
public class UserController {
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	@RequestMapping("/back/findUsers.action")
	public void showUserList(HttpServletResponse response,UserLx userLx){
		//userBiz.refreshUser();
		List<UserLx> list=userBiz.findAllUser(userLx);
		Gson gson=new Gson();
		int count=list.size();
		//int count=userBiz.findUserCount(userLx);
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
	
	@RequestMapping("/toExcel.action")  
    public String leadToExcelQuestionBank(UserLx userLx,HttpServletResponse response)throws Exception { 
        try {    
            // excel表格的表头，map    
            LinkedHashMap<String, String> fieldMap = ExcelUtil.getLeadInFiledPublicQuestionBank();    
            // excel的sheetName    
            String sheetName = "用户";    
            // excel要导出的数据    
            List<UserLx> list =userBiz.findAllUser(userLx);
            // 导出    
            if (list != null) {    
                //将list集合转化为excle    
                ExcelUtil.listToExcel(list, fieldMap, sheetName, response);    
            }   
            return null;  
        } catch (ExcelException e) {    
            e.printStackTrace();
        }   
        return null;  
    } 
}
