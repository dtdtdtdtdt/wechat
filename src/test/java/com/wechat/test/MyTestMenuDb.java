package com.wechat.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.utils.WeixinUtil;
import com.wx.menu.Button;
import com.wx.menu.ClickButton;
import com.wx.menu.ViewButton;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:beans.xml")
public class MyTestMenuDb {
	
	@Autowired
	ApplicationContext ac;
	
	@Test
	public void test() throws ParseException, IOException{
		
		
		FirstMenuDbBiz fmdb = (FirstMenuDbBiz) ac.getBean("firstMenuDbBizImpl");
		
//		FirstMenuDb fm = new FirstMenuDb();
//		fm.setName("一级2");
//		fm.setType("view");
//		fm.setUrl("https://www.nowcoder.com/");
//		fm.setKey("");
//		fmd.addFirstMenu(fm);
		
		SecondMenuDbBiz smdb = (SecondMenuDbBiz) ac.getBean("secondMenuDbBizImpl");
		
//		SecondMenuDb sm = new SecondMenuDb();
//		sm.setName("微信扫码");
//		sm.setType("view");
//		sm.setUrl("http://www.baidu.com");
//		sm.setFid(6);
//		
//		smdb.addSecondMenu(sm);
//		
		

		
		//测试查询菜单
		String access_token = "qX1yXcbqDrtjwvJJLFl1HMpPJ8vt3GZAVuhLe9FemApOd_LXoxR5yhDic9t208g3rMpv_nGjUZLcr7RQ0SZf7ciRAQ5Oe2nfa0duk1KYfRALJWfACAWUH";
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
                    
                    FirstMenuDb fmd = new FirstMenuDb();
                    fmd.setName(name);
                    fmd.setType(type);
                    fmd.setKey(str);
                    //存数据库
                    fmdb.addFirstMenu(fmd);
                    
                    
                    
                //视图类型  
                }else if(type.equals("view")){  
                    str = obj.getString("url");  
                    ViewButton vb = new ViewButton();
                    vb.setName(name);
                    vb.setType(type);
                    vb.setUrl(str);
                    viewButton.add(vb);
                    
                    FirstMenuDb fmd = new FirstMenuDb();
                    fmd.setName(name);
                    fmd.setType(type);
                    fmd.setUrl(str);
                    //存数据库
                    fmdb.addFirstMenu(fmd);
                    

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
                
                //保存一级菜单名字
                FirstMenuDb fmd = new FirstMenuDb();
                fmd.setName(name);
                //设置sub_button方便知道有子菜单
                fmd.setType("sub_button");  
                //存数据库
                fmdb.addFirstMenu(fmd);
                //需要最新的fid！
                Integer fid = fmd.getFid();
                System.out.println( fid );
                
                
                
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
                        
                        SecondMenuDb sm = new SecondMenuDb();
                        sm.setName(sub_name);
                        sm.setType(type);
                        sm.setKey(str);
                        sm.setFid(fid);
                        //存数据库
                        smdb.addSecondMenu(sm);
                        
                        
                    //子菜单  视图菜单
                    }else if(type.equals("view")){  
                        str = sub_menu.getString("url"); 
                        vb.setName(sub_name);
                        vb.setType(type);
                        vb.setUrl(str);  
                        //
                        bt.add(vb);
                        
                        
                        SecondMenuDb sm = new SecondMenuDb();
                        sm.setName(sub_name);
                        sm.setType(type);
                        sm.setUrl(str);
                        sm.setFid(fid);
                        //存数据库
                        smdb.addSecondMenu(sm);
                        
                    }else{
                    	
                        SecondMenuDb sm = new SecondMenuDb();
                        sm.setName(sub_name);
                        sm.setType(type);
                        sm.setKey(str);
                        sm.setFid(fid);
                        //存数据库
                        smdb.addSecondMenu(sm);
                    	
                    }
                    
                    System.out.println("子菜单name:"+sub_name+"|type:"+type+"|str:"+str);  
                  
                }  
            }  
           
        } 
		
		
	}
	
}
