package com.wx.common.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.FirstMenuDb;
import com.wx.common.bean.SecondMenuDb;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.biz.FirstMenuDbBiz;
import com.wx.common.biz.SecondMenuDbBiz;
import com.wx.common.utils.GetAccessToken;
import com.wx.common.utils.WeixinUtil;
import com.wx.common.web.model.JsonModel;
import com.wx.menu.Button;
import com.wx.menu.ClickButton;
import com.wx.menu.Menu;
import com.wx.menu.ViewButton;

import net.sf.json.JSONObject;

@RestController
public class MakeMenuController {
	
	
	@Resource(name="firstMenuDbBizImpl")
	private FirstMenuDbBiz fmdb;
	
	@Resource(name="secondMenuDbBizImpl")
	private SecondMenuDbBiz smdb;
	
	@Resource(name = "accessTokenZpBizImpl")
	private AccessTokenZpBiz accessTokenZpBiz;
	
	
	//生成菜单
	@RequestMapping(value="/back/makeMenu.action")
	public JsonModel makeMenu() throws ParseException, IOException, java.text.ParseException{
		JsonModel jm = new JsonModel();
		//菜单准备
		Menu menu = new Menu();
		//获取所有一级菜单
		List<FirstMenuDb> list = fmdb.findAllFirstMenu();
		//获取所有二级菜单
		SecondMenuDb s = new SecondMenuDb();
		s.setFid(null);
		List<SecondMenuDb> secondList = smdb.findAllSecondMenuByFid(s); //fid为空则查所有
		
		//用于一级菜单
		List<ClickButton> cb = new ArrayList<ClickButton>();
		List<ViewButton>  vb = new ArrayList<ViewButton>();
		//判断类型
		if(list!=null&&list.size()>0){
			for(FirstMenuDb fmd :list){  //可能有多个点击菜单   先测试一个
				if( fmd.getType().equals("click") ){
					ClickButton cb11 = new ClickButton();
					cb11.setName( fmd.getName() );
					cb11.setType("click");
					cb11.setKey(fmd.getKey());	
					cb.add(cb11);
					
				}else if(fmd.getType().equals("view")){
					ViewButton vb12 = new ViewButton();
					vb12.setName(fmd.getName());
					vb12.setType("view");
					vb12.setUrl(fmd.getUrl() );
					vb.add(vb12);
				}else if(fmd.getType().equals("sub_button")){  //含有子菜单的也算在click中处理
					ClickButton cb11 = new ClickButton();
					cb11.setName( fmd.getName() );
					cb11.setType("sub_button");
					cb.add(cb11);
					
				}
			}
			
		}
		
		
		
		//先增加一级菜单  此时b中有所有一级菜单
		Button []b = null;
		if(list!=null&&list.size()>0){
			b = new Button[list.size()];
			int cbsize = 0;
			if(cb!=null&&cb.size()>0){
				cbsize = cb.size();
			}
			for(int i=0;i<cbsize;i++){
				b[i] = cb.get(i);
			}
			int vbsize = 0;
			if(vb!=null&&vb.size()>0){
				vbsize = vb.size();
			}
			for(int i=0;i<vbsize;i++){
				b[cbsize+i] = vb.get(i);
			}
		}
		//不包括含有子菜单的一级菜单
		int t = 0;
		for(int i=0;i<b.length;i++){
			//去除含有子菜单的一级菜单
			if( !b[i].getType().equals("sub_button") ){
				t++;
			}
		}
		Button firstButton[] = new Button[ t ];   // firstButton  不包括子菜单的所有一级菜单!
		int n = 0;
		for(int i=0;i<b.length;i++){
			//去除含有子菜单的一级菜单
			if( !b[i].getType().equals("sub_button") ){
				firstButton[n] = b[i];
				n++;
			}
		}
		
		
//		Button button[] = new Button[2];      // 2	这种方式不行  为什么？
		List<Button> buttonList = new ArrayList<Button>();
		//含有二级菜单的  先在一级菜单中查找是否有二级菜单 		先设定一个含有子菜单的    现在处理多个二级菜单的情况。。。。啊啊啊哦哦哦
		//cb存了所有一级菜单中的点击菜单    获取一级菜单中有二级菜单的菜单
		if(cb!=null&&cb.size()>0){
			for( int i=0;i<cb.size();i++ ){  // 2
				ClickButton clickButton = cb.get(i);
				//如果一级点击菜单中有子菜单的话
				if( clickButton.getType().equals("sub_button") ){	
					Button button = new Button();  
					//设置名字
					button.setName( clickButton.getName() );
					buttonList.add(button);
				}
			}
		}
		//所有二级菜单 都视为跳转菜单	二级     升级功能？让二级菜单支持点击事件  
		//TODO: 暂时不要升级功能！
		List<SecondMenuDb> secondMenu = smdb.findSecondCountGroupBy();
		if( secondMenu!=null&&secondMenu.size()>0 ){
			for(int m=0;m<smdb.findSecondCountGroupBy().size();m++){   //  长度为 2
				if(secondList!=null&&secondList.size()>0){
					//应该获取二级菜单的有效数量 即 每个一级菜单对应的二级菜单数量
					List<SecondMenuDb> secondMenuList = smdb.findSecondCountGroupBy(); // 两组每组有信息 fid  count

					//取出子菜单的数量  分组后
					for( int h=0;h<secondMenuList.size();h++ ){ // 2
						Button butt = buttonList.get(h);
						SecondMenuDb sm = secondMenuList.get(h);  //有价值的是fid 
						//根据fid查出所对应的所有二级菜单
						List<SecondMenuDb> secondMenuByFid = smdb.findAllSecondMenuByFid(sm);
						ViewButton [] secondButton = new ViewButton[ sm.getCount() ]; // 3  2
						//点击菜单
//						ClickButton [] secondClickButton = new ClickButton[];
						
						//把
						for(  int i = 0;i<  sm.getCount()  ;i++){
								SecondMenuDb smd= secondMenuByFid.get(i );   
								
								ViewButton v = new ViewButton();
								v.setName(smd.getName());
								v.setType("view");
								v.setUrl(smd.getUrl());
								secondButton[i] = v;
						}
						butt.setSub_button( secondButton ); 
					}	
				}
			}
		}






		//最终的菜单只能有三个一级菜单  					所有一级菜单的总数量
		Button [] finallyButton = new Button[  list.size() ];  //  3
		//判断是否有不包含二级菜单的一级菜单
		if( firstButton.length>0 ){ 
			//q取出一级菜单中不含子菜单的
			for(int i=0;i<firstButton.length;i++){  //2  应该去掉含有二级菜单的一级菜单    数量为t 前面已经计算好了也就是firstButton的长度
				finallyButton[i] = firstButton[i];
			}
			if( buttonList.size()>0 ){ //如果有二级菜单
				//二级菜单
				for(int i=firstButton.length;i<buttonList.size()+firstButton.length;i++){
					finallyButton[i] = buttonList.get(i-firstButton.length);
				}
			}
		}else{ //如果一级菜单中没有包含二级菜单的
			//二级菜单
			for(int i=0;i<buttonList.size();i++){
				finallyButton[i] = buttonList.get(i);
			}
		}

		menu.setButton(finallyButton);
		
		//转json数据结构
		JSONObject  jsonObject = JSONObject.fromObject(menu);
//		System.out.println( jsonObject.toString() );

		String ACCESS_TOKEN = GetAccessToken.getAT(accessTokenZpBiz);
		//先删除菜单
		String delUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+ACCESS_TOKEN;
		JSONObject  jodel = WeixinUtil.doGetStr(delUrl);
		
		String outStr = jsonObject.toString();
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ACCESS_TOKEN;
		JSONObject  jo = WeixinUtil.doPostStr(url, outStr);
//		System.out.println( jo.getInt("errcode") );
		if( jo.getInt("errcode")==0 ){
			jm.setCode(1);
		}else{
			jm.setCode(0);
			jm.setMsg("失败"+jo.getInt("errcode"));
		}
		return jm;
	}
	
}
