package com.wx.common.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 缩略图
 * @author Administrator
 *
 */
public class Picture extends CommonBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pid ;
	private String pname;
	private String thumb_media_id ;
	private String webUrl;
	private Date pdate;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}	
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
}
