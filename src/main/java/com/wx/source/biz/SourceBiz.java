package com.wx.source.biz;

import java.io.IOException;
import org.apache.http.ParseException;
import com.wx.common.bean.Article;
import com.wx.common.bean.News_item;
import com.wx.common.bean.Source;
import com.wx.common.bean.SourceList;

import net.sf.json.JSONObject;

public interface SourceBiz {
	/**
	 * 组装素材
	 * @return
	 */
	public Source initSource(Article article);
	
	/**
	 * 创建素材
	 * @param token
	 * @param source
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public JSONObject createSource(String source) throws ParseException, IOException;
	
	/**
	 * 添加微信返回的素材信息
	 * @param news_item
	 */
	public void addReturnArticel(News_item news_item);
	
	/**
	 * 组装素材列表
	 * @return
	 */
	public SourceList initSourceList();
	
	/**
	 * 得到素材列表的方法
	 * @param sourceList
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public JSONObject getSourceList(String sourceList) throws ParseException, IOException;
}
