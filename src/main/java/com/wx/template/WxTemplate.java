package com.wx.template;

import java.io.Serializable;
import java.util.Map;

//微信业务模板  
//用于微信发送业务模板信息  如  微信联通公众号提示花费余额不足！
public class WxTemplate implements Serializable {

	private static final long serialVersionUID = 1642056173168187905L;

    private String template_id;//模板ID  
    private String touser;//目标客户  
    private String url;//用户点击模板信息的跳转页面  
    private Map<String,TemplateData> data;//模板里的数据  一条模板包含多条数据，模板数据类封装
    
    
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, TemplateData> getData() {
		return data;
	}
	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}
    
    
    
    
	
	
}
