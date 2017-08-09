package com.wx.message;

import java.io.Serializable;

public class Video implements Serializable {
	

	private static final long serialVersionUID = 7295032302206921788L;
	
	private String MediaId; //	是	通过素材管理中的接口上传多媒体文件，得到的id
	private String Title;	//否	视频消息的标题
	private String Description; //	否	视频消息的描述
	
	
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
	
	
	
}
