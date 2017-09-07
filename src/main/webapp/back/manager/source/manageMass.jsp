<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<link rel="stylesheet" type="text/css" href="back/css/source.css">
<script type="text/javascript" src="back/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	$("#sendBtn").click(function(){
	   $.ajax({
			type:"POST",
			url:"back/SendMessage.action",
			data:$("#MassForm").serialize(),
			dataType:"JSON",
			success:function(data){
				if(data.code==1){
					alert("发送成功！");
					location.href="back/manager/source/manageArticle.jsp";
				}else{
					alert("发送失败！"+data.msg);
				}
			}
		})   
   });
})

	function selectedObj(){
		 var Obj =$("#is_to_all").val();
		 if(Obj=="true"){
			num.style.display="none";
		 }else{
			 num.style.display="block";
		 }
	  };
  
  /* function selectedType(){
		 var type =$("#msgtype").val();
		 if(type=="mpnews"){
			 type_left.style.display="block";
			 type1_right.style.display="block";			
			 type2.style.display="none";
			 type3_right.style.display="none";
			 type4_right.style.display="none";
			 type5_right.style.display="none";
		 }else if(type=="text"){
			 type2.style.display="block";				
			 type_left.style.display="none";
			 type1_right.style.display="none";		 
			 type3_right.style.display="none";
			 type4_right.style.display="none";
			 type5_right.style.display="none";
			 document.getElementById("text").click();
		 }else if(type=="image"){
			 type_left.style.display="block";
			 type3_right.style.display="block";
			 type1_right.style.display="none";	
			 type2.style.display="none";
			 type4_right.style.display="none";
			 type5_right.style.display="none";
		 }else if(type=="voice"){
			 type_left.style.display="block";
			 type4_right.style.display="block";
			 type1_right.style.display="none";	
			 type2.style.display="none";
			 type3_right.style.display="none";
			 type5_right.style.display="none";
		 }else if(type=="video"){
			 type_left.style.display="block";
			 type5_right.style.display="block";
			 type1_right.style.display="none";	
			 type2.style.display="none";
			 type3_right.style.display="none";
			 type4_right.style.display="none";
		 }
	   };
   */
</script>

<p style="font-size:12px;color:gray;margin-left:30px">为保障用户体验，微信公众平台严禁恶意营销以及诱导分享朋友圈，严禁发布色情低俗、暴力血腥、政治谣言等各类违反法律法规及相关政策规定的信息。一旦发现，我们将严厉打击和处理。</p>
<form id="MassForm" method="post" style="margin-top:20px;margin-left:50px">
	群发对象：<select id="is_to_all" name="is_to_all" style="width:150px;" onchange="selectedObj()">
			    <option value="true">全部用户</option>
			    <option value="false">按标签选择</option>			   					    
			</select><br/><br/>		
	
	<span id="num" style="display:none">分组编号：<input type="text" id="tag_id" name="tag_id" value="" style="width:145px;" /><br/><br/></span>
	
	消息类型：<select id="msgtype" name="msgtype" style="width:150px;" onchange="selectedType()">
			    <option value="mpnews">图文消息</option>
			    <option value="text">文本消息</option>			   
			    <option value="image">图片消息</option>
			    <option value="voice">语音消息</option>	
			    <option value="video">视频消息</option>							    
			</select><br/><br/>	
			
	<div id="type">
		<!-- <div id ="type_left" ></div>
			
		<div  id ="type1_right" ></div>
		
		<div  id ="type2" style="display:none;">
			<textarea id="text" style="width:900px;height:240px;resize:none;border-style:none;" placeholder="请输入文本消息"></textarea>
		</div>
	
		<div  id ="type3_right" style="display:none;"></div>
		
		<div  id ="type4_right" style="display:none;"></div>
		
		<div  id ="type5_right" style="display:none;"></div>
	 -->
		<div id="skin2"><br/>
			<div style="margin-left:42px">
				<input type="text" size="30"  style="border-style:none;line-height:5px;" readonly="readonly" id="viewtitle" value="${map.title }" /><br/>
				<div id="skinpic2" >			
					<img id="viewpic" src="${map.thumb_url }" style="width:250px;height:200px"/><br/>
				</div>
				<input type="text" size="30"  style="border-style:none;line-height:5px" readonly="readonly" id="viewdigest" value="${map.digest }" /><br/><br/>	
			</div>  				   				
		</div>				   							
	</div><br/>		
	
	<input type="button" id="sendBtn" value="发送" style="margin-left:480px"/>								
			
</form>	
	
	

		
		
		
		
		
		