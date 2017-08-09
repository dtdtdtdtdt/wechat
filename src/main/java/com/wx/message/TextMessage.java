package com.wx.message;

import java.io.Serializable;
//回复文本信息
public class TextMessage extends BaseMessage implements Serializable {
	
	private static final long serialVersionUID = -2035244057172129189L;
	

	private String Content; //内容
	
	
	
	
	
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	private long   MediaId;//	图片消息,语音媒体,消息媒体id，可以调用多媒体文件下载接口拉取数据。

	private String Formmat;  //	Format	语音格式，如amr，speex等
	private String Recognition;//	Recognition	语音识别结果，UTF8编码
	
	private String ThumbMediaId;//	ThumbMediaId	视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

	
	//地理位置
	private double Location_X;  //	Location_X	地理位置维度
	private double Location_Y;  //	Location_Y	地理位置经度
	private String Scale;  //	Scale	地图缩放大小
	private String Label;	//	Label	地理位置信息



	//图文消息
	private String PicUrl;//	图片链接（由系统生成）
	private String Title; //	Title	消息标题述
	private String Description; //	Description	消息描
	private String Url; //	Url	消息链接
	
	
	



	
	
	
	
	
	
}
