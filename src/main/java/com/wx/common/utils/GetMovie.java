package com.wx.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wx.common.bean.Movie;
public class GetMovie {

	/**
	 * 查询是否有该电影
	 * @param movie
	 * @return
	 */
	public List<Movie> doPost(Movie movie) {
		String url="http://www.80s.tw/search";

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<Movie> list=new ArrayList<Movie>();
		List nameValuePairs = new ArrayList(); 
		nameValuePairs.add(new BasicNameValuePair("keyword", movie.getKeyword()));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			
			Document doc=Jsoup.parse(result);
			Elements elements = doc.getElementsByTag("a");// 找到所有a标签
			for (Element element : elements) {
				String relHref = element.attr("href"); // "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
				String linkHref = element.text();
				// 用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
				if (relHref.startsWith("/movie/")&&linkHref.startsWith("[")){
					relHref = "http://www.80s.tw" + relHref;
					movie=new Movie();
					//System.out.println(relHref+"==="+linkHref);
					movie.setUrl(relHref);
					movie.setName(linkHref);
					list.add(movie);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据链接查询下载链接
	 * @param movie
	 * @return
	 */
	public List<Movie> doGet(Movie movie){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<Movie> list=new ArrayList<Movie>();
		HttpGet httpGet = new HttpGet(movie.getUrl());
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				//System.out.println(result);
				
				Document doc=Jsoup.parse(result);
				Elements elements = doc.getElementsByTag("a");// 找到所有a标签
				for (Element element : elements) {
					String relHref = element.attr("href"); // "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
					String linkHref = element.text();
					// 用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
					if (((relHref.startsWith("magnet:?xt=")&&linkHref.indexOf("飞兔下载")==-1)||(relHref.startsWith("thunder://")))&&linkHref.indexOf("迅雷下载")==-1){
						movie=new Movie();
						//System.out.println(relHref+"==="+linkHref);
						movie.setUrl(relHref);
						movie.setName(linkHref);
						list.add(movie);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
