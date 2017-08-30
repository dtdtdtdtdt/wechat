<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String path =request.getContextPath();

String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath %>">
<title>微信公众号机器人开关控制</title>
<script src="js/jquery.min.js" ></script>
<script src="js/lc_switch.js" ></script>
<link rel="stylesheet" href="css/lc_switch.css">

<style type="text/css">
body * {
	font-family: Arial, Helvetica, sans-serif;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
}
h1 {
	margin-bottom: 10px;
	padding-left: 35px;
}
a {
	color: #888;
	text-decoration: none;
}

#second_div {
	width: 90%;
	max-width: 600px;
	min-width: 340px;
	margin: 50px auto 0;
	background: #f3f3f3;
	border: 6px solid #eaeaea;
	padding: 20px 40px 40px;
	text-align: center;
	border-radius: 2px;
}
#third_div {
	width: 90%;
	max-width: 600px;
	min-width: 340px;
	margin: 10px auto 0;
}
</style>
</head>





<body>


<div id="second_div">
  <form>
    <div style="float: left; width: 50%;">
      <p> 机器人开关控制 </p>
      <p> Tips:开启机器人默认会关闭微信墙功能 </p>
      <p id="ct" name="ct">
      	<!-- 这一段需要进行拼接  0是不启用机器人对应off  1 是启用机器人对应on -->
     	<input type="checkbox" name="check-1" value="4" class="lcs_check" autocomplete="off" /> 
   	
      </p>

    </div>

  </form>
  <div style=" clear: both;"></div>
</div>

<p></p>
<script type="text/javascript">
$(document).ready(function(e) {
	$('input').lc_switch();
	// triggered each time a field changes status
	$('body').delegate('.lcs_check', 'lcs-statuschange', function() {
		var status = ($(this).is(':checked')) ? 'checked' : 'unchecked';
		// 如果是开或者关都发送ajax请求到后台进行处理
		$.ajax({
			type:"POST",
			data: "status="+status,
			url:"back/updateRobotStatus.action",
			dataType:"JSON"
		});	
		
	});
});
</script> 
</body>
</html>

