package com.wx.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.City;
import com.wx.common.bean.UserLx;
import com.wx.common.utils.ExcelException;
import com.wx.common.utils.ExcelUtil;
import com.wx.common.utils.GetLatAndLngByBaidu;
import com.wx.common.web.model.JsonModel;
import com.wx.user.biz.CityBiz;
import com.wx.user.biz.UserBiz;

import net.sf.json.JSONObject;

@RestController
public class UserController {
	
	@Resource(name="userBizImpl")
	private UserBiz userBiz;
	
	@Resource(name="cityBizImpl")
	private CityBiz cityBiz;
	
	@RequestMapping("/back/findUsers.action")
	public void showUserList(HttpServletResponse response,UserLx userLx,
			@RequestParam(value="page",required=false) String page,
			@RequestParam(value="rows",required=false) String rows
			){
		//userBiz.refreshUser();
		int start=(Integer.valueOf(page)-1)*Integer.valueOf(rows);
		int pagesize=Integer.valueOf(rows);
		userLx.setStart(start);
		userLx.setPagesize(pagesize);
		List<UserLx> list=userBiz.findAllUser(userLx);
		Gson gson=new Gson();
		//int count=list.size();
		int count=userBiz.findUserCount(userLx);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/back/createChart.action")
	public JsonModel createChart(HttpServletRequest request){
		JsonModel jm=new JsonModel();
		List<City> cityList=cityBiz.findAllCity();
		List<City> list=new ArrayList<City>();
		GetLatAndLngByBaidu lal=new GetLatAndLngByBaidu();
		Map<String, double[]> geocoordMap=new HashMap<String, double[]>();
		double[] value=null;
		for(City c:cityList){
			try {
				value=new double[2];
				Object[] o=lal.getCoordinate(c.getCity());
				c.setValue(cityBiz.findValueByCity(c));
				list.add(c);
				value[0]=Double.valueOf(o[0].toString());
				value[1]=Double.valueOf(o[1].toString());
				geocoordMap.put(c.getCity(), value);
			} catch (IOException e) {
				System.out.println("生成地图数据失败!");
			}
		};
		JSONObject jsonObject =new JSONObject();
		jsonObject=JSONObject.fromObject(geocoordMap);
		jm.setRows(list);
		jm.setJson(jsonObject);
		return jm;
	}
}
