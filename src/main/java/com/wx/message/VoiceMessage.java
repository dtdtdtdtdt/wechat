package com.wx.message;

import java.io.Serializable;

public class VoiceMessage extends BaseMessage implements Serializable {


	private static final long serialVersionUID = 6843975523254449836L;
	
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
	
	
	
}
