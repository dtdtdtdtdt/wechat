<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width,initial-scale=1"/>
<link rel="stylesheet" href="back/css/bootstrap.min.css">
<link rel="stylesheet" href="back/css/login.css">
<script src="back/js/jquery-1.9.1.js"></script>
<script src="back/js/bootstrap.min.js"></script>
<script src="back/js/common.js"></script>
<script>
	$(function(){ 
		$(".container").css("position","fixed").css("top",($(window).height()-$(".container").height())/2)
		.css("left",($(window).width()-$(".container").width())/2);
		
		$('.close-button').click(function(){
			$(this).parent().removeClass("slidePageInFromLeft").addClass("slidePageBackLeft");
		});
		
		$(window).resize(function(){
			$(".container").css("position","fixed").css("top",($(window).height()-$(".container").height())/2)
			.css("left",($(window).width()-$(".container").width())/2);
		});
			
	});
	
	function login(id,role) {
    	var flag = $("#"+id).text();
    	$("#"+role).val(flag);
	}
	
	function showRegisterPage(){
		$(".register-page").addClass("slidePageInFromLeft").removeClass("slidePageBackLeft");
	}
	
	function backlogin(){
		$(".register-page").removeClass("slidePageInFromLeft").addClass("slidePageBackLeft");
	}
	
	$(function() {
		$("#inputbutton").click(function() {
			$.ajax({
				type : "POST",
				data : $("#myform").serialize(),
				url : "adminLogin.action",
				dataType : "JSON",
				success : function(data) {
					if (data.code == 1) {
						alert("登录成功！");
						location.href = "toMain.action";
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
	<div class="container">
		<div class="row">
            <div class="col-md-5 col-md-offset-3">
            	<div class="panel">
                	<div class="panel-heading login-top">用户登录</div>
                    <div class="panel-body">
                    	<form class="form-group col-lg-10 col-md-offset-1" id="myform"  role="form">
                           <input type="hidden" name="op" value="login"/>
                            <div class="input-group">
                            	<label for="aname" class="input-group-addon">用户名</label>
                                <input type="text" class="form-control" name="aname" id="raname" required placeholder="请输入用户名"/>
                            </div>
                            <br />
                            <div class="input-group">
                            	<label for="pwd" class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</label>
                                <input type="password" class="form-control" name="apwd" id="rapwd" required  placeholder="请输入密码"/>
                            </div>
                            <br />
                            <div class="input-group">
                            	<label for="vcode" class="input-group-addon">验证码</label>
                                <input type="text" class="form-control" name="zccode" id="vcode" required placeholder="请输入右边的验证码"/>
                                <label class="input-group-btn">&nbsp;&nbsp;<img src="image.jsp"/>&nbsp;&nbsp;</label>
                            </div>
                            <br/>
                            <div class="input-group">
                            	<input id="inputbutton" type="button" value="登陆" class="btn btn-success mybtn"/>
                                <input type="reset" value="重置" class="btn btn-warning mybtn"/>
                            </div>
                   
                		</form>
                    </div>
                <div class="panel-footer login-footer">源辰信息 &copy; 版权所有</div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
	</div>
    
</body>
</html>
