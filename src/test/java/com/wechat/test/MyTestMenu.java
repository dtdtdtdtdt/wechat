package com.wechat.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import com.google.gson.Gson;
import com.wx.common.utils.TransFormUtil;
import com.wx.common.utils.WeixinUtil;
import com.wx.menu.Button;
import com.wx.menu.ClickButton;
import com.wx.menu.ViewButton;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyTestMenu {
	public static void main(String[] args) throws ParseException, IOException {
		//测试查询菜单
		String access_token = "FugWynNeVVH5TaY_9Bn-4z3JpagQz0veQS19zr_GpMeEWSo1YspLa6idSjaO0olmZMoFuiikNYRETGtDogNNYZx-WP0lb9dRLvs7hAY85-DltDe4GwIKojLV61rWuOOoTNAfACAOIV";

		String url = WeixinUtil.QUERY_MENU_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject jo = WeixinUtil.doGetStr(url);
		

		

		JSONObject jsonObject = JSONObject.fromObject( jo.toString() );  
		JSONObject menu = jsonObject.getJSONObject("menu");
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
                    System.out.println("子菜单name:"+sub_name+"|type:"+type+"|str:"+str);  
                  
                }  
            }  
           
        } 

			

		
		
        
	}
}
