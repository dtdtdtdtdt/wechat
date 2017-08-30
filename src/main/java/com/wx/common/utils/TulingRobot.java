package com.wx.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class TulingRobot implements Serializable{
	private static final long serialVersionUID = -2160507759326872463L;
	
	//连接机器人
	public static String  Msg(String string) throws IOException{
		
//		TulingRobot tr = new TulingRobot( );
        String APIKEY = "ebe0cc6329fd4986b419f6adb820ab99"; 
        
        String question =  string ;//这是上传给云机器人的问题
        
        String INFO = URLEncoder.encode(question, "utf-8"); 
        String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO; 
        URL getUrl = new URL(getURL); 
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
        connection.connect(); 
        
        // 取得输入流，并使用Reader读取 
        BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8")); 
        StringBuffer sb = new StringBuffer(); 
        String line = ""; 
        while ((line = reader.readLine()) != null) { 
            sb.append(line); 
        } 
        reader.close(); 
        // 断开连接 
        connection.disconnect(); 
        //将json格式的数据转换为对象
        Gson gson = new Gson();
        String str = gson.toJson(sb);
        return (String) str.subSequence(29,str.length()-4 );
	}

	
	
	
	
}
