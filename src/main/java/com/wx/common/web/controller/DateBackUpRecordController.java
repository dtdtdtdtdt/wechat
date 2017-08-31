package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wx.common.bean.DateBackUpRecord;
import com.wx.common.bean.KeyReply;
import com.wx.common.biz.DateBackUpRecordBiz;
import com.wx.common.utils.CommonDateUtils;
import com.wx.common.utils.DeleteFile;
import com.wx.common.web.model.JsonModel;

@RestController
public class DateBackUpRecordController {
	
	
	@Resource(name="dateBackUpRecordBizImpl")
	private DateBackUpRecordBiz dateBackUpRecordBiz;
	
	//查找所有备份记录
	@RequestMapping(value="/back/databaseBackUpRecord.action")
	public void findAllDateBackUpRecord(HttpServletResponse response,DateBackUpRecord dateBackUpRecord,HttpServletRequest request,@RequestParam(value="page",required=false) String page) throws ParseException {

		//用于排序
		dateBackUpRecord.setOrderby(  request.getParameter("sort")   );
		dateBackUpRecord.setOrderway(  request.getParameter("order")  );
		Integer pagesize = Integer.parseInt(  request.getParameter("rows")    );
		dateBackUpRecord.setPagesize(  pagesize );
		Integer start = ( Integer.parseInt(page) -1)*pagesize;
		dateBackUpRecord.setStart( start  );
		
		
		List<DateBackUpRecord> list =  dateBackUpRecordBiz.findAllDateBackUpRecord(dateBackUpRecord);
		//试试修改时间格式
		if(list!=null&&list.size()>0) {
			for(int i=0;i<list.size();i++) {
				DateBackUpRecord du = list.get(i);
				//// 1503644224000  需要去掉最后三个0
				String str = String.valueOf(   du.getTimes().getTime()  );
				str = str.substring(0, str.length()-3);
				du.setTimesformat( Long.parseLong(str) );  
			}
		}

		int count = 0;
		if(list!=null&&list.size()>0) {
			count= list.size();
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
//			System.out.println("响应内容发送失败!");
			e.printStackTrace();
		}
		out.println(jsonstr);
		out.flush();
		out.close();
	}
	
	//根据文件路径删除备份记录同时删除文件
	@RequestMapping(value="/back/delDatabaseBackUpRecordByFilePath.action")
	public JsonModel delDatabaseBackUpRecordByFilePath(DateBackUpRecord dateBackUpRecord) {
		JsonModel jm = new JsonModel();
		DeleteFile deleteFile = new DeleteFile();
		
		if( deleteFile.deleteFile( dateBackUpRecord.getFilepath() ) ) {
			//删除备份记录
			dateBackUpRecordBiz.delDatabaseBackUpRecordByFilePath(dateBackUpRecord);
			jm.setCode(1);
			return jm;
		}
		jm.setCode(0);
		jm.setMsg("删除失败！");		
		return jm;
	}
	
	
	
	
	
	
	
	
	
	
	
}
