package com.wx.message;

import java.io.Serializable;

/**
 * 回复图片信息
 * @author Administrator
 *
 */
public class ImageMessage extends BaseMessage implements Serializable {

	private static final long serialVersionUID = 4224067748027611516L;
	
	
	
	
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}


	
	
	
	
	
	
	
	
	
}
