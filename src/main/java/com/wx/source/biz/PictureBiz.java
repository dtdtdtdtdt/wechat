package com.wx.source.biz;

import java.util.List;

import com.wx.common.bean.Picture;

public interface PictureBiz {
	
	/**
	 * 显示图片素材
	 * @param picture
	 * @return
	 */
	public List<Picture> findPicture(Picture picture);
	
	/**
	 * 添加图片素材
	 * @param picture
	 * @return
	 */
	public void addPicture(Picture picture);
	
	/**
	 * 删除图片素材
	 * @param picture
	 */
	public void delPicture(Picture picture);
}
