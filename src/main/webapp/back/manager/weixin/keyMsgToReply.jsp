<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用表单使用ajax上传图片的js -->
<script type="text/javascript" src="back/js/jquery.form.js"></script>
<title>关键字回复设置</title>
</head>

<script type="text/javascript">
	//用于文本回复
	function add() {
        if (confirm("确认保存吗")) {
        	//发送一个ajax请求
			$.ajax({
				type:"POST",
				url:"back/keyMsgToReply.action",
				data:$("#myform").serialize(),
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						alert("保存成功！");
					}else{
						alert("失败，原因："+data.msg);
					}
				}
			});
        } else {
            alert("不保存") ;
        }
    }
    window.onload = function() {
        var bt = document.getElementById("addKeyReply");
        bt.onclick = function() {
            add();
        }
    }

    
	$(function(){
		$(".btn-block").click(function(){
			$("#resume_frm").ajaxSubmit({
				type:"POST",
				url:"back/keyMsgToReplyImg.action",
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						alert("成功");
					}else{
						alert("shibai");
					}
		
	
				} //end success
		}); //end ajaxSubmit
		}); //end click
		});
    
    
    
	//用于图片显示
	//判断浏览器是否支持FileReader接口
	function showUploadImg(obj,picid){
		if (typeof FileReader == 'undefined') {
		   $.messager.alert('Warning','当前浏览器不支持FileReader接口');
		   //使选择控件不可操作
		   obj.setAttribute("disabled","disabled");
		   return;
		}

		var file = obj.files[0];
		var reader = new FileReader();
		reader.onload = function (e) {
		    $("#"+picid).attr("src",e.target.result);
		}
		reader.readAsDataURL(file)
	}
</script>

<body>




	
	<div>   
	<center>
	
		
		<!-- 上传图片我只需要获取地址即可  然后在调用微信工具类上传文件 -->
		文本信息回复
		<form action="" method="post" id="myform"  >
			关键字:
			<input type="text" class="easyui-textbox" name="keywords" >
			文本回复设置
			<input type="text" class="easyui-textbox" name="Content" > <br/>
			
			<input type="button" class="easyui-linkbutton" id="addKeyReply"  value="保存"/>
		</form>
		
		
		
		<br/>
		<hr/>
		
		图片,语音,视频回复
		Tips:    
		图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
    	语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
    	视频（video）：10MB，支持MP4格式
    	<br/>
		<form enctype="multipart/form-data" id="resume_frm">
			关键字设置:
			<input type="text" class="easyui-textbox" name="keywords" > <br/>
			请选择要回复的类型 <br/>
		
			<select class="easyui-combobox"  name="ktype">
			  <option value="1">图片</option>
			  <option value="2">语音</option>
			  <option value="3">视频</option>
			</select>			
			回复设置 <br/>
			图片:
			<input type="file" name="file" id="fileupload"  multiple   onchange="showUploadImg(this,'showpic')" accept="image/*"//><br/>
			<input type="button" class="easyui-linkbutton" value="隐藏图片" onclick="document.getElementById('showpic').style.display = 'none';"/>
            <input type="button" class="easyui-linkbutton" value="显示图片" onclick="document.getElementById('showpic').style.display = 'block';"/>
            <br/>
			<img id="showpic" /><br/>	
			视频标题<input class="easyui-textbox" type="text" name="title" > <br/>
			视频描述<input class="easyui-textbox" type="text" name="description" > <br/>
			
			
			
			<button type="button" class="btn btn-primary btn-lg btn-block">提交</button>
		</form>
	
		
	
		
		
		
	</center>
	</div>
	
	
</body>
</html>