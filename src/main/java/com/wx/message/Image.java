package com.wx.message;

import java.io.Serializable;

public class Image implements Serializable {
	

	private static final long serialVersionUID = -7176482810793201078L;
	
	
	private String MediaId;		 //通过素材管理中的接口上传多媒体文件，得到的id。


	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	
	
}
