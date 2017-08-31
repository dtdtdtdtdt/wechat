<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>

<%
String path =request.getContextPath();
String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/shop/";
%>
<base href="<%=basePath %>">


<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title></title>

<style>
*{ margin: 0; padding: 0;}
body{font: 14px/1.4 Microsoft YaHei,SimSun,Arial;}
.errorwarp{ padding-top: 100px;text-align: center;}
p{ line-height: 94px;font-size: 18px;color: #14191e;}
a{ font-size: 18px; color: #f01414; display: block; margin-left: auto; margin-right: auto; text-decoration: none;}
.feedback{ font-size: 14px; margin-top: 38px;}
</style>

<style>html, * {-webkit-user-select:text!important; -moz-user-select:text!important;}</style></head>
<body>

<div class="errorwarp">
    <img src="images/404.gif">
    <p>Sorry 你请求的页面不存在！</p>
    
</div>


</body></html>








