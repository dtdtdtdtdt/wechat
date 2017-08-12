package com.wechat.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;

import com.wx.common.utils.TransFormUtil;
import com.wx.common.utils.WeixinUtil;
import com.wx.template.TemplateData;
import com.wx.template.WxTemplate;

import net.sf.json.JSONObject;

public class MyTestTeamplate {

	public static void main(String[] args) throws ParseException, IOException {
		//设置行业属性
		String access_token = "KHlgrfe4mDSBfVfC5Vk8Z1wSnnDcGeaXgGhWKHHhQmQDTu6cf_gruhQ1jrGzGTxj2V-b4YXuQ0-mKoqbUdKK74T0EBvCiSSHbuIRhykMc3uS6s2Xg2rc0n8maijov3rLHKEcAAAQTN";
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+access_token;
		
		StringBuffer outStr = new StringBuffer();
		outStr.append("{");
		outStr.append("\"industry_id1\":\"1\",");
		outStr.append("\"industry_id2\":\"2\"");
		outStr.append("}");
	         
		
//		JSONObject jo = WeixinUtil.doPostStr(url, outStr.toString());
//		System.out.println( jo );

		//查询行业信息
		String url2 = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+access_token;
//		JSONObject jo2 = WeixinUtil.doGetStr(url2);
//		System.out.println( jo2 );
		
		
		//获得模板ID 
		String url3 = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="+access_token;
		StringBuffer outStr3 = new StringBuffer();
		outStr3.append("{");
		outStr3.append("\"template_id_short\":\"TM00015\"");
		outStr3.append("}");
//		JSONObject jo3 = WeixinUtil.doPostStr(url3, outStr3.toString());
		
//		System.out.println( jo3 );
		// template_id:  lhEuVUzBy-GszWhamUT_BHixatnp_gWKEW8R6EPrV1k
		
		//获取模板列表
		String url4 = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token="+access_token;
		JSONObject jo4 = WeixinUtil.doGetStr(url4);
		System.out.println( jo4 );
//		{"template_list":[{"template_id":"lhEuVUzBy-GszWhamUT_BHixatnp_gWKEW8R6EPrV1k","title":"订单支付成功","primary_industry":"IT科技","deputy_industry":"互联网|电子商务","content":"{{first.DATA}}\n\n支付金额：{{orderMoneySum.DATA}}\n商品信息：{{orderProductName.DATA}}\n{{Remark.DATA}}","example":"我们已收到您的货款，开始为您打包商品，请耐心等待: )\n支付金额：30.00元\n商品信息：我是商品名字\n\n如有问题请致电400-828-1878或直接在微信留言，小易将第一时间为您服务！"}]}
		//自己申请的
//		{"template_list":[{"template_id":"lhEuVUzBy-GszWhamUT_BHixatnp_gWKEW8R6EPrV1k","title":"订单支付成功","primary_industry":"IT科技","deputy_industry":"互联网|电子商务","content":"{{first.DATA}}\n\n支付金额：{{orderMoneySum.DATA}}\n商品信息：{{orderProductName.DATA}}\n{{Remark.DATA}}","example":"我们已收到您的货款，开始为您打包商品，请耐心等待: )\n支付金额：30.00元\n商品信息：我是商品名字\n\n如有问题请致电400-828-1878或直接在微信留言，小易将第一时间为您服务！"},
//						  {"template_id":"lUJgf3nqrN_AeF5qB0VlG59YsjGzs9jrS0syxl0Wu54","title":"信用卡\"微\"账单","primary_industry":"","deputy_industry":"","content":"{ {first.DATA} }\n  账单日期：{ {keyword1.DATA} }           \n  到期还款日：{ {keyword2.DATA} }\n  本期应还款总额：{ {keyword3.DATA} }  \n  本期最低还款额：{ {keyword4.DATA} }                      \n  { {remark.DATA} }","example":""}]}

//		删除模板  暂时不用
		String del = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token="+access_token;
		StringBuffer outStrDel = new StringBuffer();
		outStrDel.append("{\"template_id\" : \"lhEuVUzBy-GszWhamUT_BHixatnp_gWKEW8R6EPrV1k\"}");
		
//		JSONObject jsondel = WeixinUtil.doPostStr(del, outStrDel.toString());
//		System.out.println( jsondel );
		
		
		
//		发送模板消息
		String url5  ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		StringBuffer outStr5 = new StringBuffer();
		outStr5.append("{");
		outStr5.append("\"touser\":\"oeFt8wTSJGKxsH_lt9mTGwfmHSwo\",");
		outStr5.append("\"template_id\":\"kWWvgEfts8SbTvFDC9Zm37rDc6q2ZZNi0ONUWWx37CY\",");
		outStr5.append("\"url\":\"http://campus.163.com/#/home\",");
		outStr5.append("\"data\":{");
		outStr5.append("\"first\": {");
		outStr5.append("\"value\":\"恭喜你购买成功！感谢购买！\",\"color\":\"#173177\"");
		outStr5.append("},");
		outStr5.append("\"orderMoneySum\":{\"value\":\"支付金额48元!\",\"color\":\"#173177\"");
		outStr5.append("},");
		outStr5.append("\"orderProductName\":{\"value\":\"程序员哥哥一枚\",\"color\":\"#173177\"");
		outStr5.append("}");
		outStr5.append("}");
		outStr5.append("}");
//		JSONObject jo5 = WeixinUtil.doPostStr(url5, outStr5.toString());
//		System.out.println( jo5 );
		
		
		WxTemplate temp = new WxTemplate();
		temp.setTouser("oeFt8wYmSWPGqc8BJKxBQDU_px7U"); 
		temp.setUrl("http://campus.163.com/#/home");
		temp.setTemplate_id("KT2g5VK9vGC7PB2T1O6MW9m0MtfOWBEHoA_Y00kXj9E");
		
		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
		//创建TemplateData 
		TemplateData first= new TemplateData();
		first.setValue(  "您的浦发银行个人信用卡本期账单: \n\n\n" );
//		first.setColor("#000000");
		map.put("first", first);
		//支付金额
		TemplateData keyword1 = new TemplateData();  
		keyword1.setColor("#FF8C00");  
		keyword1.setValue(  new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"\n");   
		map.put("keyword1", keyword1);
		//商品信息
		TemplateData keyword2 = new TemplateData();  
		keyword2.setColor("#FF0000");  
		keyword2.setValue("您无需还款 \n\n");  
		map.put("keyword2", keyword2);
		
		TemplateData keyword3 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword3.setValue(" 人民币-5.27\n");  
		map.put("keyword3", keyword3);
		
		TemplateData keyword4 = new TemplateData();  
//		orderProductName.setColor("#00FF00");  //默认黑色
		keyword4.setValue(" 人民币0.00\n\n");  
		map.put("keyword4", keyword4);
		
		TemplateData remark = new TemplateData();
		remark.setValue("点击全文可进入您的云账单,办理账单分期,积分查询,快速还款");
		map.put("remark", remark);
		
		
		//这句在最后 不能丢！
		temp.setData(  map );
		
		//对象转json
		String json =TransFormUtil.beanToJson(temp);
		System.out.println( json+"\n" );
		//发送模板
		JSONObject jo2  = WeixinUtil.doPostStr(url5, json);		
		System.out.println( jo2 );
		
		
		
		
		
		
		
		
	}

}
