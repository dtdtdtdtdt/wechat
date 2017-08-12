package com.wx.common.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.AccessTokenZp;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.TransFormUtil;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.utils.XmlAndMap;
import com.wx.common.web.model.JsonModel;
import com.wx.menu.Button;
import com.wx.menu.ClickButton;
import com.wx.menu.ViewButton;
import com.wx.message.TextMessage;
import com.wx.template.TemplateData;
import com.wx.template.WxTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class MenuController {
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	
	//查询公众号菜单
	@RequestMapping(value="/searchMenu.action")
	public JsonModel searchMenu(HttpServletRequest request,HttpSession sesion) throws ParseException, IOException, java.text.ParseException {
		JsonModel jm = new JsonModel();
		//使用access_token查询
		try {
			String access_token = GetAccessToken.getAT(accessTokenZpBiz);
			String url = WeixinUtil.QUERY_MENU_URL.replace("ACCESS_TOKEN", access_token);
			JSONObject jo = WeixinUtil.doGetStr(url); 
			//json转字符串
			String jsonStr = jo.toString();
			
			JSONObject jsonObject = JSONObject.fromObject( jo.toString() );  
			JSONObject menu = jsonObject.getJSONObject("menu");
			System.out.println(  menu );
			
			
			JSONArray array  = menu.getJSONArray("button");  
			//存放在一个list集合中  
			List<ClickButton> clickButton = new ArrayList<ClickButton>();
			List<ViewButton>  viewButton = new ArrayList<ViewButton>();
			List<Button>  bt = new ArrayList<Button>();
	        for(int i=0;i<array.size();i++){  
	            JSONObject obj = array.getJSONObject(i);  
	            String name = obj.getString("name");  
	            if(obj.containsKey("type")){  
	                String type = obj.getString("type");  
	                String str = null;  
	                //点击类型
	                if(type.equals("click")){  
	                    str = obj.getString("key");  
	                    ClickButton cb = new ClickButton();
	                    cb.setName(name);
	                    cb.setType(type);
	                    cb.setKey(str);  //key
	                    clickButton.add(cb);
	                //视图类型  
	                }else if(type.equals("view")){  
	                    str = obj.getString("url");  
	                    ViewButton vb = new ViewButton();
	                    vb.setName(name);
	                    vb.setType(type);
	                    vb.setUrl(str);
	                    viewButton.add(vb);
	                }  
	                System.out.println("name:"+name+"|type:"+type+"|str:"+str);  
	            }else{  
	            //含有二级菜单的处理
	            	Button b = new Button();
	            	//子菜单  点击
	            	ClickButton cb = new ClickButton();
	            	//子菜单  视图
	            	ViewButton vb = new ViewButton();
	            	
	            	b.setName(name);  //含有二级菜单的一级菜单名字
	                System.out.println("一级菜单名称："+name);  
	                JSONArray sub_arr = obj.getJSONArray("sub_button");  
	                for(int j=0;j<sub_arr.size();j++){  
	                    JSONObject sub_menu = sub_arr.getJSONObject(j);  
	                    String type = sub_menu.getString("type");  
	                    String sub_name = sub_menu.getString("name");  
	                    String str = null;  
	                    //子菜单  点击菜单
	                    if(type.equals("click")){  
	                        str = sub_menu.getString("key");  
	                        cb.setName(sub_name);
	                        cb.setType(type);
	                        cb.setKey(str);
	                        
	                        //
	                        bt.add(cb);
	                        
	                    //子菜单  视图菜单
	                    }else if(type.equals("view")){  
	                        str = sub_menu.getString("url"); 
	                        vb.setName(sub_name);
	                        vb.setType(type);
	                        vb.setUrl(str);  
	                        //
	                        bt.add(vb);
	                        
	                    }  
	                    //存入Button中
	                    
	                    b.setSub_button(  new Button[]{cb,vb} );     
	                    System.out.println("子菜单name:"+sub_name+"|type:"+type+"|str:"+str);  
	                  
	                }  
	            }  
	           
	        } 
	        //存入request中
	        ClickButton c = clickButton.get(0);
	        //注意！在使用了iframe处理时，使用request获取不到值了
//	        request.setAttribute("clickButton", c);
//	        sesion.setAttribute("clickButton2", c);
	        ViewButton v = viewButton.get(0);
	        sesion.setAttribute("viewButton2", v);
	        
//	        System.out.println( clickButton  );
//	        ClickButton c = clickButton.get(0);
//	        System.out.println( c.getName()+c.getType()+c.getKey() );
//	        
//	        System.out.println( viewButton );
//	        ViewButton v = viewButton.get(0);
//	        System.out.println( v.getName()+v.getType()+v.getUrl() );
//	        
//	        System.out.println( bt.size() );
//	        for( int i=0;i<bt.size();i++ ){
//	        	System.out.println( bt.get(i).getName() );
//	        	
//	        	Button b = bt.get(i);
//	        	System.out.println( b );
//	        	
//	        }
				
		
			
			jm.setCode(1);
			jm.setObj(jsonStr);
		}catch (IOException e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg( "菜单为空！请先创建菜单！" );
		}
		return jm;
	}
	
	
	//删除菜单
	@RequestMapping(value="/delMenu.action",method=RequestMethod.POST)
	public JsonModel delMenu(HttpServletResponse resp) throws ParseException, IOException, java.text.ParseException{
		
		JsonModel jm = new JsonModel();
		//获取token
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		//删除菜单url
		String url = WeixinUtil.DELETE_MENU_URL.replace("ACCESS_TOKEN", access_token);
//		JSONObject jo = WeixinUtil.doGetStr(url);
//		int errcode = (int) jo.get("errcode");
//		if( errcode==0 ){
//			jm.setCode(1);
//			jm.setObj(jo.toString() );
//		}else if( 46003==errcode ){ //菜单不存在
//			jm.setCode(0);
//			jm.setMsg( "菜单为空,不能删除,请先创建菜单！" );
//		}else{
//			jm.setCode(0);
//			jm.setMsg("删除失败！");
//		}
		

		

		
		
		return jm;
	}
	
	//发送模板业务消息
	@RequestMapping(value="/sendMsg.action")
	public JsonModel sendMsg(HttpServletRequest req,HttpServletResponse resp) throws ParseException, IOException, java.text.ParseException {
		
		JsonModel jm = new JsonModel();
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser("oeFt8wYmSWPGqc8BJKxBQDU_px7U"); 
		temp.setUrl("http://campus.163.com/#/home");
		temp.setTemplate_id("KT2g5VK9vGC7PB2T1O6MW9m0MtfOWBEHoA_Y00kXj9E");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//创建TemplateData 
		TemplateData first= new TemplateData();
		first.setValue(  "您的浦发银行个人信用卡本期账单: \n\n\n" );
//		first.setColor("#000000");
		map.put("first", first);
		//支付金额
		TemplateData keyword1 = new TemplateData();  
		keyword1.setColor("#FF8C00");  
		keyword1.setValue(  new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"\n");   
		map.put("keyword1", keyword1);
		//商品信息
		TemplateData keyword2 = new TemplateData();  
		keyword2.setColor("#FF0000");  
		keyword2.setValue("您无需还款 \n\n");  
		map.put("keyword2", keyword2);
		
		TemplateData keyword3 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword3.setValue(" 人民币-5.27\n");  
		map.put("keyword3", keyword3);
		
		TemplateData keyword4 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword4.setValue(" 人民币0.00\n\n");  
		map.put("keyword4", keyword4);
		
		TemplateData remark = new TemplateData();
		remark.setValue("点击全文可进入您的云账单,办理账单分期,积分查询,快速还款");
		map.put("remark", remark);
		
		
		//这句在最后 不能丢！
		temp.setData(  map );
		
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
		System.out.println( json+"\n" );
		//发送模板
		String access_token = GetAccessToken.getAT(accessTokenZpBiz);
		String url ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		JSONObject jo2;
		try {
			jo2 = WeixinUtil.doPostStr(  url , json);
			System.out.println( jo2 );
			
			jm.setCode(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg("发送失败");
		} 	

		
		
		return jm;
	}

	
	
	
	
	
	
}
