package com.wx.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

/**
 * 获取经纬度
 * 
 * @author jueyue 返回格式：Map<String,Object> map map.put("status",
 *         reader.nextString());//状态 map.put("result", list);//查询结果
 *         list<map<String,String>> 密钥:f247cdb592eb43ebac6ccd27f796e2d2
 */
public class GetLatAndLngByBaidu {

	/**
	 * @param addr
	 *            查询的地址
	 * @return
	 * @throws IOException
	 */
	public Object[] getCoordinate(String addr) throws IOException {
		String lng = null;// 经度
		String lat = null;// 纬度
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String key = "PQIzze6VFeGZZz1QfINQNj0bFHyz1NuS";
		String url = String.format("http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s", address, key);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
					//{"status":0,"result":{"location":{"lng":112.9793527876505,"lat":28.21347823085322},"precise":0,"confidence":12,"level":"城市"}}
					JSONObject jsonObject=new JSONObject();
					jsonObject=JSONObject.fromObject(data);
					try {
						lng=jsonObject.getJSONObject("result").getJSONObject("location").getString("lng");
						lat=jsonObject.getJSONObject("result").getJSONObject("location").getString("lat");
					} catch (Exception e) {
						System.out.println("地图数据解析错误！原因:"+e.getMessage()+jsonObject);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new Object[] { lng, lat };
	}

	public static void main(String[] args) throws IOException {
		GetLatAndLngByBaidu getLatAndLngByBaidu = new GetLatAndLngByBaidu();
		Object[] o = getLatAndLngByBaidu.getCoordinate("长沙");
		System.out.println(o[0]);// 经度
		System.out.println(o[1]);// 纬度
	}

}
