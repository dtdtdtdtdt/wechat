package com.wx.common.bean;

import java.io.Serializable;

//关键回复
public class KeyReply extends CommonBean implements Serializable {

	private static final long serialVersionUID = 7143706070218017489L;

	private Integer kid;
	private String keywords;  //关键字
	private String Content;  //文本回复内容
	private String MediaId;
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	private Integer ktype;  //回复类型
	
	
	public Integer getKtype() {
		return ktype;
	}
	public void setKtype(Integer ktype) {
		this.ktype = ktype;
	}
	public Integer getKid() {
		return kid;
	}
	public void setKid(Integer kid) {
		this.kid = kid;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
