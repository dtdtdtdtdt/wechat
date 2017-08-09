<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
	$(function(){
		$.ajax({
			type:"POST",
			url:"searchMenu.action",
			dataType:"JSON",
			success:function(data){
				alert(data.obj);
				if(data.code== 1){
					$("#showMenu").html("");
					var str ="";
					for( var i=0;i<data.obj.length;i++){
						str+="";
					}
				}else{
					alert("获取失败！原因"+data.msg);
				}
			}
		});	
	});
	
	
	//点击删除按钮删除菜单请求
	$(function(){
		$("#delMenu").click(function(){
			$.ajax({
				type:"POST",
				url:"delMenu.action",
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						alert("删除菜单成功！");
						location.href="manage.action";
					}else{
						alert("删除菜单失败，原因："+data.msg);
					}
				}
			});
		});	
	});
	
	//操作DOM如果是选择点击则弹出是否增加二级菜单  若是视图则选择增加url
	//var v = document.getElementById("sele").value
	//alert(v);
	
	
</script>
<title>增加菜单</title>
</head>
<body>
	<!-- 增加菜单  有先查询当前菜单  修改菜单  是否删除菜单 -->
	<table id="manTypeTable"></table>
	
		当前菜单为<br/>
		<div id="showMenu">  </div>
		
		
		<br/>
		<!-- 增加菜单在没有菜单的基础上 -->
		增加菜单<br/>
		增加一个菜单
		<form action="" method="POST">
			菜单名:<input type="text" name="" >
			类&nbsp;型:
			<select id="sele">
			  <option value="click">点击</option>
			  <option value="view">视图</option>
			</select>
			
			是否增加二级菜单:
			是<input type="radio" name="sub_button" value="是" >
			否<input type="radio" name="sub_button" value="否" >
			
			
		</form>

		
		删除菜单？		
		<input type="button" id="delMenu"  value="添加"/>
				
			

		
		
	
</body>
</html>