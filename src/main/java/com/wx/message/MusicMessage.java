package com.wx.message;

import java.io.Serializable;

public class MusicMessage extends BaseMessage implements Serializable {

	private static final long serialVersionUID = 6897666787393156470L;
	
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
	
	

}
