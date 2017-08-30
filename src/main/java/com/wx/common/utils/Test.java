package com.wx.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) {
		String url="http://www.80s.tw/search";
		String keyword="春风十里不如你";

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			List nameValuePairs = new ArrayList(); 
			nameValuePairs.add(new BasicNameValuePair("keyword", keyword));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
		
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			//System.out.println(result);
			Document doc=Jsoup.parse(result);
			Elements elements = doc.getElementsByTag("a");// 找到所有a标签
			List<String> list=new ArrayList<String>();
			for (Element element : elements) {
				String relHref = element.attr("href"); // "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
				String linkHref = element.text();

				// 用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
				if (relHref.startsWith("/movie/")&&linkHref.indexOf(keyword)!=-1){
					relHref = "http://www.80s.tw" + relHref;
					System.out.println(relHref+"==="+linkHref);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
