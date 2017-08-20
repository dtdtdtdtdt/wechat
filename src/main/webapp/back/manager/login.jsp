<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>管理员登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="js/jquery-1.9.1.js"></script>
<script src="js/common.js"></script>
<!-- css files -->
<link href="css/style.css" rel='stylesheet' type='text/css' media="all" />
<!-- /css files -->
<script>
	$(function() {
		$("#inputbutton").click(function() {
			$.ajax({
				type : "POST",
				data : $("#myform").serialize(),
				url : "adminLogin.action",
				dataType : "JSON",
				success : function(data) {
					if (data.code == 1) {
						//alert("登录成功！");
						location.href = "back/toMain.action";
					} else {
						alert("登录失败！" + data.msg);
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<h1></h1>
	<div class="log">
		<div class="content1">
			<h2>管理员登录</h2>
			<form action="adminLogin.action" id="myform" method="post">
				<input type="text" name="aname" placeholder="请输入用户名">
				<input type="password" name="apwd" placeholder="请输入密码">
				<input type="zccode" name="zccode" placeholder="请输入验证码">
				<label >
					<img src="image.jsp" onclick="changeVilidateCode(this)" border="0" title="点击图片刷新验证码" />
				</label>
				<div class="button-row">
					<input id="inputbutton" type="button" class="sign-in" value="登录">
					<input type="reset" class="sign-in" value="重置">
					<div class="clear"></div>
				</div>
			</form>
			<p style="color: #fff;">刘翔、丁婷、朱鹏 &copy; 版权所有</p>
		</div>
	</div>

</body>
</html>