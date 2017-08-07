<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<script type="text/javascript" src="back/manager/ckeditor/ckeditor.js" ></script>

<script type="text/javascript">
	/**
		选择图片，马上预览
		使用方式： <input type="file" name="pic" onchange="showUploadImg(this,'showpic')" accept="image/*"/>
		参数说明：obj：input=file的表单文件对象
				picid： 用于显示图片的<img />标签的ID号				
	*/
	function showUploadImg(obj,picid){
		//判断浏览器是否支持FileReader接口
		if(typeof FileReader == 'undefined'){
			$.messager.alert('Warning',' 当前的浏览器不支持FileReader接口');
			//使选择控件不可操作
			obj.setAttribute("disabled","disabled");
			return ;
		}
		var file =obj.files[0];
		var reader =new FileReader();
		reader.onload =function(e){
			$("#"+picid).attr("src",e.target.result);
		}
		reader.readAsDataURL(file);		
	}
</script>
<title>添加食物</title>

	<center>
		<hr/>
		<form id="addFoodForm" action="back/addFood.action" method="post" enctype="multipart/form-data">
			<div style="text-align:left;">
				<input type="hidden" name="op" value="add" />
				食物名称：<input type="text" name="fname" id="fname" /><br/>
				食物原价：<input type="text" name="normprice" id="normprice" /><br/>
				食物现价：<input type="text" name="realprice" id="realprice" /><br/>					
				食物图片：<input type="file" name="fphoto" id="fphoto" onchange="showUploadImg(this,'showpic')" accept="image/*" /><br/>
				<input type="button" value="隐藏图片" onclick="document.getElementById('showpic').style.display ='none';"/>
				<input type="button" value="显示图片" onclick="document.getElementById('showpic').style.display ='block';"/>
				<br/>
				<img id="showpic" /><br/>
				食物详情：<textarea class="ckeditor" name="detail"></textarea><br/>
				<input type="submit" id="addBtn" value="添加" />
			</div>
		</form>
	</center>
















    