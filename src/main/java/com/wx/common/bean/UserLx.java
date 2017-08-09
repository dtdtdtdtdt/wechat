package com.wx.common.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author 刘翔
 *
 */
public class UserLx implements Serializable{
	private static final long serialVersionUID = 2454542358870962981L;
	
private Integer total;
	
	private Integer count;
	// 用户的标识  
    private String openid;
	// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息  
    private Integer subscribe;  
    // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间  
    private Integer subscribe_time;  
    // 昵称  
    private String nickname;  
    // 用户的性别（1是男性，2是女性，0是未知）  
    private Integer sex;  
    // 用户所在国家  
    private String country;  
    // 用户所在省份  
    private String province;  
    // 用户所在城市  
    private String city;  
    // 用户的语言，简体中文为zh_CN  
    private String language;  
    // 用户头像  
    private String headimgurl;
    //微信端的备注
	private String remark;
	private Integer groupid;
	private String tagid_list;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	public Integer getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(Integer subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		if(country!=null&&country.length()>0){
			this.country = country;
		}else{
			this.country = "未知";
		}
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		if(province!=null&&province.length()>0){
			this.province = province;
		}else{
			this.province = "未知";
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if(city!=null&&city.length()>0){
			this.city = city;
		}else{
			this.city = "未知";
		}
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public String getTagid_list() {
		return tagid_list;
	}
	public void setTagid_list(String tagid_list) {
		this.tagid_list = tagid_list;
	}
	
}
