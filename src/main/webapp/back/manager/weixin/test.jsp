<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>


<title>测试文件上传</title>
<!-- 引用表单使用ajax上传图片的js -->
<script type="text/javascript" src="back/js/jquery.form.js"></script>
</head>
<body>
	
<script>
	$(function(){
		$(".btn-block").click(function(){
			$("#resume_frm").ajaxSubmit({
				type:"POST",
				url:"test.action",
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

</script>

	
	
<form enctype="multipart/form-data" id="resume_frm">
		关键字:
	    <input type="text" name="keyWords" >

        <input id="fileupload" type="file" name="file" multiple>
  
    <button type="button" class="btn btn-primary btn-lg btn-block">提交</button>
</form>
	


	
</body>
</html>