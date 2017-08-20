package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.FirstMenuDb;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.web.model.JsonModel;

//菜单管理
@RestController
public class MenuManagerController {
	
	
	@Resource(name="firstMenuDbBizImpl")
	private FirstMenuDbBiz firstMenuDbBiz;
	
	@Resource(name="secondMenuDbBizImpl")
	private SecondMenuDbBiz secondMenuDbBiz;
	
	
	
	//查找所有一级菜单
	@RequestMapping("/back/findAllFirstMenu.action")
	public void findAllFirstMenu(HttpServletResponse response){
		
		List<FirstMenuDb> list = firstMenuDbBiz.findAllFirstMenu();
		int count = firstMenuDbBiz.findAllFirstMenuCount();
		//easyui要求的格式
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", count);
		map.put("rows", list);
		
		Gson gson=new Gson();
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


	
	//增加一级菜单
	@RequestMapping("/back/addFirstMenu.action")
	public JsonModel addFirstMenu(FirstMenuDb firstMenuDb,HttpServletRequest request){
		
		String bangdingType = request.getParameter("bangdingType");
		String bangdingName = request.getParameter("bangdingName");
//		System.out.println( "绑定名称"+bangdingName+"---绑定类型"+bangdingType );
		
		JsonModel jm = new JsonModel();
		//先判断一级菜单的数量  <=3才可以继续增加
		int count = firstMenuDbBiz.findAllFirstMenuCount();
		if(count>=3){
			jm.setCode(0);
			jm.setMsg("一级菜单最多为三个,不能再增加了！");
			return jm;
		}
		//firstMenuDb.getType()  分为 1点击 2视图 3包括子菜单 

		int type = Integer.parseInt( firstMenuDb.getType() );
		if( type==1  ){
			firstMenuDb.setName(  firstMenuDb.getName() );
			firstMenuDb.setType("click");
			//让可以随机生成
			String str = String.valueOf( new Date().getTime() ) ;
			firstMenuDb.setKey( str );
			//插入数据库
			firstMenuDbBiz.addFirstMenu(firstMenuDb);
			
		}else if(type==2){ //视图 有url
			firstMenuDb.setName(  firstMenuDb.getName() );
			firstMenuDb.setType("view");
			firstMenuDb.setUrl(  firstMenuDb.getUrl() );
			//插入数据库
			firstMenuDbBiz.addFirstMenu(firstMenuDb);
		}else if(type==3){ //视图 有url
			firstMenuDb.setName(  firstMenuDb.getName() );
			firstMenuDb.setType("sub_button");
			//插入数据库
			firstMenuDbBiz.addFirstMenu(firstMenuDb);
		}

		//生成菜单发给微信！
		
		
		
		jm.setCode(1);
		return jm;
	}
	
	
	//根据fid删除一级菜单
	@RequestMapping("/back/deleteFirstMenuByFid.action")
	public JsonModel deleteFirstMenuByFid(FirstMenuDb firstMenuDb){
		// firstMenuDb 传过来只有 fid 还需要根据fid查找所有信息
		FirstMenuDb  fdm = firstMenuDbBiz.findFirstMenuByFid(firstMenuDb.getFid());
		JsonModel jm = new JsonModel();
		
		//根据fid查是否有二级菜单
		SecondMenuDb secondMenuDb = new SecondMenuDb();
		secondMenuDb.setFid(firstMenuDb.getFid());
		List<SecondMenuDb> list = secondMenuDbBiz.findAllSecondMenuByFid(secondMenuDb);
	
		
		//有子菜单无法直接删除一级菜单  应该先查找二级菜单！ 如果二级菜单下的子菜单为空则可以删除
		if( fdm.getType().equals( "sub_button"   )  ){ 
			if(list!=null&&list.size()>0){
				jm.setCode(0);
				jm.setMsg("该菜单包含子菜单无法直接删除！请先删除该菜单下的子菜单后再试！");
				return jm;
			}
		}
		firstMenuDbBiz.deleteFirstMenuByFid(fdm);
		jm.setCode(1);
		
		return jm;
	}
	
	
	
	
	//查找所有二级菜单用于在表单中显示
	@RequestMapping("/back/findAllSecondMenu.action")
	public void findAllSecondMenu(HttpServletResponse response){
		
		SecondMenuDb secondMenuDb = new SecondMenuDb();
		//easyui要求的格式
		Map<String,Object> map=new HashMap<String,Object>();
		List<SecondMenuDb> list = secondMenuDbBiz.findAllSecondMenuByFid(secondMenuDb);
		if( list!=null&&list.size()>0){
			map.put("rows", list);
		}else{
//			map.put("rows", null);
		}
		int count = secondMenuDbBiz.findAllSecondMenuCountByFid(secondMenuDb);
		if(count!=0 ){
			map.put("total", count);
		}else{
//			map.put("total", 0);
		}
		
		
		Gson gson=new Gson();
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
	
	//查找所有一级菜单用于在表单中显示
	/**
	 * 应该查询有二级菜单的一级菜单然后将一级菜单显示过去！
	 * @param response
	 * @return
	 */
	@RequestMapping("/back/findAllFirstMenuUse.action")
	public JsonModel findAllSecondMenuUse(HttpServletResponse response){
		
		JsonModel jm = new JsonModel();
		
		SecondMenuDb secondMenuDb = new SecondMenuDb();
		//查找所有一级菜单
		List<FirstMenuDb> firstList = firstMenuDbBiz.findAllFirstMenu();
		List<FirstMenuDb> list = new ArrayList<FirstMenuDb>();
		for( FirstMenuDb fmd: firstList ){  
			if( fmd.getType().equals("sub_button") ){
				list.add(fmd);
			}
			
		}
		jm.setCode(1);
		jm.setObj(list);
		return jm;
	}
	
	
	//增加二 2级菜单
	@RequestMapping("/back/addSecondMenu.action")
	public JsonModel addSecondMenu(SecondMenuDb secondMenuDb,HttpServletRequest request){
		JsonModel jm = new JsonModel();
		SecondMenuDb smd = new SecondMenuDb();
		int fid =  Integer.parseInt( request.getParameter("ftype")  );
		
		String type = secondMenuDb.getType();  // 1是点击 2是视图
		if( type.equals("1") ){
			smd.setName( secondMenuDb.getName() );
			smd.setFid(fid);
			smd.setType("click");
		}else{
			smd.setName( secondMenuDb.getName() );
			smd.setFid(fid);
			smd.setType("view");
			smd.setUrl(secondMenuDb.getUrl());
		}
		
		secondMenuDbBiz.addSecondMenu(smd);
		
		jm.setCode(1);
		return jm;
	}

	
	
	//根据sid删除二级菜单
	@RequestMapping("/back/deleteSecondBySid.action")
	public JsonModel deleteSecondMenuBySid(HttpServletRequest request ){
		
		int sid = Integer.parseInt(  request.getParameter("sid") );

		JsonModel jm = new JsonModel();

		secondMenuDbBiz.deleteSecondBySid(sid);
		
		jm.setCode(1);
		return jm;
	}
	
	
}
