<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content=" width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<title>电影小助手</title>
<link rel="stylesheet" href="css/movie.css" />
</head>
<%
String path =request.getContextPath();

String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.9.1.js" ></script>
<script type="text/javascript">
	$(function(){
		var list="<%=session.getAttribute("movieList")%>";
		if(list!="null"&&list!="[]"){
			$("#main").html('<br><a style="margin-left:40%;color:white;" onclick="back()">重新搜索</a>');
		}
	});
	
	function submit(){
		$.ajax({
			url:"getMovie.action",
			type:"POST",
			data:$("#zn_search_form").serialize(),
			dataType:"JSON",
			success:function(data){
				if(data.code==0){
					alert("搜索失败!");
				}else{
					location.href="movie.jsp";
					$("#main").html('<br><a style="margin-left:40%;color:white;" onclick="back()">重新搜索</a>');
				}
			}
		});
	};
</script>
<body>
<div class="login">
	<div id="main">
		<div class="welcome"><img src="images/welcome.png"></div>
		<form class="login-form" id="zn_search_form" action="getMovie.action" method="post" target="_self">
			<div class="login-inp"><label>电影名：</label><input id="keyword" type="text" name="keyword" /></div>
			<div class="login-inp"><a href="javascript:submit();" >立即搜索</a></div>
		</form>
		<div class="login-txt">丁婷、刘翔、朱鹏</div>
	</div>
	<c:if test="${movieList!=null }">
		<div style="margin-top:5%;margin-bottom:5%;weight:85%;height:90%;overflow-y:scroll;">
			<c:forEach items="${movieList }" var="movie">
				<input type="radio" id="url" style="margin-left:5%;" name="url" checked value="${movie.url }"/>
				<input type="text" id="name" name="name" style="width:80%" disabled value="${movie.name }"/><hr>
			</c:forEach>
		</div>
	</c:if>
</div>

<script type="text/javascript">
$('input:radio[name="url"]').click(function(){ 
	if($(this).is(":checked")){ 
		
    	var url=$('input:radio[name="url"]:checked').val();
  		
  		if(url.indexOf("thunder")==-1&&url.indexOf("magnet:?xt=")==-1){
	    	var zoneNames=document.getElementsByName("url");
	    	for(var i=0;i<zoneNames.length;i++){   
	    		var zoneName=zoneNames[i];   
	    	    if(zoneName.checked){
	    	    	var name = document.getElementsByTagName("input")[(i*2)+1].value;
	    	    	$.ajax({
	    	    		url:"getMovie.action",
	    				type:"POST",
	    				data:"url="+url+"&name="+name,
	    				dataType:"JSON",
	    				success:function(data){
	    					if(data.code==0){
	    						alert("搜索失败!");
	    					}else{
	    						location.href="movie.jsp";
	    						$("#main").html('<br><a style="margin-left:40%;color:white;" onclick="back()">重新搜索</a>');
	    					}
	    				}
	    			});
	    	    }
	    	}
  		}else{
  			tishi(url);
  		}
    }  
});

function tishi(content) {
	var html = '<div class="xiaoxi none" id="msg" style="z-index:9999;left: 5%;width: 90%;position: fixed;background:none;top:50%;">'+
	' <p class="msg" style="background: none repeat scroll 0 0 #fff; border-radius: 30px;color: #000; margin: 0 auto;padding: 1.5em;text-align: center;width: 70%;opacity: 0.8;word-wrap:break-word;word-break:break-all;overflow: hidden;"></p></div>';
	$(document.body).append(html);
	$("#msg").show();
	$(".msg").html(content);
	setTimeout('$("#msg").fadeOut()', 10000);
}

function back(){
	$.ajax({
		url:"getMovie.action",
		type:"POST",
		data:"name=refresh",
		dataType:"JSON",
		success:function(data){
			location.href="movie.jsp";
		}
	});
}
</script>

</body>
</html>
