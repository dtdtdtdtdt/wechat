package com.wx.source.biz.impl;

import java.io.IOException;
import javax.annotation.Resource;
import org.apache.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wx.common.bean.AccessTokenZp;
import com.wx.common.bean.Article;
import com.wx.common.bean.News_item;
import com.wx.common.bean.Source;
import com.wx.common.bean.SourceList;
import com.wx.common.biz.AccessTokenZpBiz;
import com.wx.common.dao.BaseDao;
import com.wx.common.utils.WeixinUtil;
import com.wx.source.biz.SourceBiz;
import net.sf.json.JSONObject;

@Service
@Transactional
public class SourceBizImpl implements SourceBiz{
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	@Resource(name="accessTokenZpBizImpl")
	private AccessTokenZpBiz atzb;
	
	//组装素材
	@Override
	public Source initSource(Article article){
		Source source =new Source();
		source.setArticles(new Article[]{article});		
//		List<Article> articleList =this.baseDao.findAll(article, "findArticle");	
//		Article[] a=new Article[articleList.size()];
//		if(articleList!=null && articleList.size()>0){
//			for(int i=0;i<articleList.size();i++){
//				a[i]=articleList.get(i);
//			}
//		}
//		source.setArticle(a);
		return source;
	}

	//获取上传图文消息后  微信返回的mediaID（根据此mediaID可以得到上传到微信的素材 可以实现图文消息的群发等） 
	@Override
	public JSONObject createSource(String source) throws ParseException, IOException {
		AccessTokenZp at =new AccessTokenZp();
		at =atzb.serachAccessToken();
		String url =WeixinUtil.SOURCESE_URL.replace("ACCESS_TOKEN", at.getAccess_token());		
		JSONObject jsonObject =WeixinUtil.doPostStr(url,source);	
		return jsonObject;		
	}

	//组装素材列表参数
	@Override
	public SourceList initSourceList(){
		SourceList sourceList =new SourceList();
		sourceList.setType("news");		
		sourceList.setOffset(0);
		sourceList.setCount(10);
		return sourceList;
	}
	
	//获取素材列表	
	@Override
	public JSONObject getSourceList(String sourceList) throws ParseException, IOException {
		AccessTokenZp at =new AccessTokenZp();
		at =atzb.serachAccessToken();
		String url =WeixinUtil.GET_SOURCELIST_URL.replace("ACCESS_TOKEN", at.getAccess_token());		
		JSONObject jsonObject =WeixinUtil.doPostStr(url,sourceList);
		return jsonObject;		
	}
	
	//存储微信返回的图文素材MediaID
	@Override
	public void addReturnArticel(News_item news_item) {
		this.baseDao.save(news_item, "addReturnArticle");		
	}
}
