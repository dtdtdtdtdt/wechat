package com.wx.message;

import java.io.Serializable;

public class VideoMessage extends BaseMessage implements Serializable {
	

	private static final long serialVersionUID = -9075308796181950041L;
	private Video Video;
	
	public Video getVideo() {
		return Video;
	}
	public void setVideo(Video video) {
		Video = video;
	}
	
	
	
}
