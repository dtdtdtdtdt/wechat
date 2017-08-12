<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<%@ page 
	import="java.util.*,
	net.sf.json.JSONArray,
	net.sf.json.JSONObject,
	com.wx.menu.*"

%>
<script type="text/javascript">
	//搜索菜单
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
</script>











<script type="text/javascript">function del() {
        if (confirm("确认发送吗")) {
        	//发送一个ajax请求
			$.ajax({
				type:"POST",
				url:"sendMsg.action",
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						alert("发送成功！");
					}else{
						alert("发送，原因："+data.msg);
					}
				}
			});
        } else {
            alert("不发送") ;
        }
    }
    window.onload = function() {
        var bt = document.getElementById("delMenu");
        bt.onclick = function() {
            del();
        }
    }
</script>


<title>增加菜单</title>
</head>
<body>
	<!-- 增加菜单  有先查询当前菜单  修改菜单  是否删除菜单 -->
	<table id="manTypeTable"></table>
	


		
		发送信息
		<input type="button" id="delMenu"  value="发送"/>
		<br/>

				
			

		
		
	
</body>
</html>