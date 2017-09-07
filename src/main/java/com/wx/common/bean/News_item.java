package com.wx.common.bean;

import java.io.Serializable;

/**
 * 微信返回的素材内容
 * @author Administrator
 *
 */
public class News_item implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;			 	 //必须	标题
	private String author;			 	 //否	作者
	private String digest;	         	 //否	图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字。
	private String content;	         	 //必须	图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源"上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
	private String content_source_url;	 //必须	图文消息的原文地址，即点击“阅读原文”后的URL
	private String thumb_media_id;	 	 //必须	图文消息的封面图片素材id（必须是永久mediaID）
	private Integer show_cover_pic;    	 //必须	是否显示封面，0为false，即不显示，1为true，即显示
	private String url;					 //图文页的URL
	private String thumb_url;		

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public Integer getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(Integer show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	
	@Override
	public String toString() {
		return "News_item [title=" + title + ", author=" + author + ", digest=" + digest + ", content=" + content
				+ ", content_source_url=" + content_source_url + ", thumb_media_id=" + thumb_media_id
				+ ", show_cover_pic=" + show_cover_pic + ", url=" + url + ", thumb_url=" + thumb_url + "]";
	}
}
	
