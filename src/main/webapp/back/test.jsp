<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关键字回复设置</title>
</head>

<script type="text/javascript">

    
	//用于图片上传
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
	
		
		<br/>
		<hr/>
		
		图片form
		<form action="back/keyMsgToReply2.action" method="post" id="myform2"  enctype="multipart/form-data" >
			关键字:
			<input type="text" name="keyWords" >
			

			图片回复设置 <br/>
			图片:
			<input type="file" name="file"  onchange="showUploadImg(this,'showpic')" accept="image/*"//><br/>
			<input type="button" value="隐藏图片" onclick="document.getElementById('showpic').style.display = 'none';"/>
            <input type="button" value="显示图片" onclick="document.getElementById('showpic').style.display = 'block';"/>
            <br/>
			<img id="showpic" /><br/>	
	
			<input type="submit"  value="上传"/>
		</form>
		
		
		
	</center>
	</div>
	
	
</body>
</html>