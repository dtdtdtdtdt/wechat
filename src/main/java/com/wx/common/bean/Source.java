package com.wx.common.bean;

import java.io.Serializable;
import java.util.Arrays;

public class Source implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Article[] articles;

	public Article[] getArticles() {
		return articles;
	}

	public void setArticles(Article[] articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "Source [articles=" + Arrays.toString(articles) + "]";
	}
	
}
