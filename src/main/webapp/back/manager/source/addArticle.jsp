<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<link rel="stylesheet" type="text/css" href="back/css/source.css">
<script type="text/javascript" src="back/js/jquery.form.js"></script>
<script type="text/javascript" src="back/manager/ckeditor/ckeditor.js" ></script>

<script type="text/javascript">
	$(function(){
		$.ajax({
			type:"POST",
			url:"back/findPicture.action"
		})
	
		//获取thumb_media_id的值  并将图片显示出来
		$('input:radio[name="radio"]').change(function(){  
			if($(this).is(":checked")){ 
				
		    	var thumb_media_id=$('input:radio[name="radio"]:checked').val();
		    	$("#thumb_media_id").val(thumb_media_id);
		  
		    	var zoneNames=document.getElementsByName("radio");
		    	for(var i=0;i<zoneNames.length;i++){   
		    		var zoneName=zoneNames[i];   
		    	    if(zoneName.checked){
		    	    	var webUrl = document.getElementsByTagName("img")[i+2].src;
		    	    	$("#showpic").attr("src",webUrl);
		    	    	showpic.style.display="block";
		    	    	$("#viewpic").attr("src",webUrl);
		    	    	viewpic.style.display="block";		    	    	
		    	    	choice.style.display="block";
		    	    }   
		    	} 		    		 		        
		        $('#win1').window('close');
		    }  
		});
		
		//获取show_cover_pic的值
		$('input:radio[name="show_cover_pic"]').change(function(){  
			if($(this).is(":checked")){  
		    	var value=$('input:radio[name="show_cover_pic"]:checked').val();  
		    }  
		});
		
		//处理CKEDITOR的值
		function Ckupdate() {
			for(instance in CKEDITOR.instances){
				CKEDITOR.instances[instance].updateElement();
			}
		}				
		
		$("#saveBtn").click(function(){
			Ckupdate();   //在提交表单前处理CKEDITOR
			$.ajax({
				type:"POST",
				url:"back/addArticle.action",
				data:$("#addArticleForm").serialize(),
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						alert("添加成功！");
						//location.href="voteuser/voteSubject_toView.action?vsid="+$("#vsid").val();
					}else{
						alert("添加失败！"+data.msg);
					}
				}
			})
		 });
		
	 	$("#saveAndSend").click(function(){	
			Ckupdate();   //在提交表单前处理CKEDITOR
			$.ajax({
				type:"POST",
				url:"back/addAndSendArticle.action",
				data:$("#addArticleForm").serialize(),
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						location.href="back/manager/source/manageMass.jsp";
					}else{
						alert("失败！"+data.msg);
					}
				}
			})
		 });	
	 	
	}); 
	
	//计算文本输入统计的监听事件
	function textCoun(){
	    var sendTextarea =document.getElementById("digest");	             
	    sendTextarea.oninput = function(){
	        text =sendTextarea.value;
	        $("#viewdigest").val(text);
	        counter =text.length;
	       	document.getElementById('use').innerHTML=counter;       
	       	if(text.length>119){
	       		$("#digest").val(text.substr(0,119));	       		
	       	}
	    }
	}	
	
	//文本输入的监听事件
	function textAdd1(){
	    var text1 =document.getElementById("title");
	    text1.oninput = function(){
		   	 text =text1.value;
	    	$("#viewtitle").val(text);
	    }	 	    
	}	
	
	//点击上传  显示图片
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
			$("#viewpic").attr("src",e.target.result);
			
		}
		reader.readAsDataURL(file);
		
		document.getElementById("submitPic").click();
	}
	
	//封面图片上传
	function submitForm(){
		$('#myform').form('submit',{
			onSubmit:function(){		
				$("#myform").ajaxSubmit({
					type:"POST",
					url:"back/addPicture.action",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){	
							showpic.style.display="block";		
					    	viewpic.style.display="block";
					    	choice.style.display="block";
							var obj =eval(data);							
					    	$("#thumb_media_id").val(obj.msg);
						}else{
							alert("图片上传失败"+data.msg);
						}
					} 
				});
			}
		});
	}
</script>

<title>添加素材</title>

<div id="whole">
	<div id="addArticle">
		<form id="addArticleForm" method="post" enctype="multipart/form-data" >
			<div style="text-align:left;">
				标题<br/>
				<input type="text"  size="73" name="title" id="title" oninput="textAdd1()" /><br/><br/>
				
				作者（选填）<br/>
				<input type="text" size="73" name="author" id="author" /><br/><br/>	
			
				封面<br/>
				<input  type="button" id="upload" value="上传" onclick="fileupload.click()" />
								
				<input  type="button" id="selectBut" onclick="$('#win1').window('open')" value="从图片库中选择" /><br/><br/>
				<div id="win1" class="easyui-window" title="图片库" closed="true" style="width:800px;height:400px">					
					<c:forEach items="${pictureList }" var="pictureList" varStatus="status">
						<div id="show">	
							<input type="radio" id="radio" name="radio" value="${pictureList.thumb_media_id }" />
							<img id="picture" style="width:100px;height:100px" src="${pictureList.webUrl } " />
						</div>		
					</c:forEach>							
				</div>
				
				<input  type="hidden" id="thumb_media_id" name="thumb_media_id" value=""/>
	 			
	 			<img id="showpic" src="" style="display:none;width:250px;height:200px"/>
	 			
	 			<p id="choice" style="display:none" >
				封面图片是否显示在正文中：  是<input type="radio" id="show_cover_pic" name="show_cover_pic" value="1" />
						  		 否<input type="radio" id="show_cover_pic" name="show_cover_pic" value="0" />
				</p>
						  		 
				摘要（选填）<br/>
				<textarea id="digest" style="width:595px;height:60px;resize:none;" name="digest" oninput="textCoun()" oninput="textAdd2()"></textarea>
				<p id="total">/120</p><p id="use">0</p>
				<br/><br/><br/>
				正文<br/>			
				<textarea class="ckeditor" id="content" name="content"></textarea><br/>
				
				原文地址（选填）<br/>
				<input type="text" size="73" name="content_source_url" id="content_source_url" /><br/><br/>	
				
				<input type="button" id="saveBtn" value="保存" /> 
				<input type="button" id="saveAndSend" value="保存并群发" /> 				
			</div>
		</form>		
	</div>
</div>

<!-- 左边的小框 -->
<div id="skin">
	<br/>
	<div style="margin-left:22px">
		<input type="text" size="30"  style="border-style:none;line-height:5px;" readonly="readonly" id="viewtitle"/><br/>
		<div id="skinpic" >			
			<img id="viewpic" src="" style="display:none;width:250px;height:200px"/><br/>
		</div>
		<input type="text" size="30"  style="border-style:none;line-height:5px" readonly="readonly" id="viewdigest"  /><br/><br/>	
	</div>  				   				
</div>

<form action="" method="post" id="myform" enctype="multipart/form-data" >
	<input type="file"  name="file" id="fileupload" onchange="showUploadImg(this,'showpic')" accept="image/*" style="display:none;" /><br/>		
</form>
<a href="javascript:void(0)" id="submitPic" onclick="submitForm()" style="display:none;"></a>










    