<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
String path =request.getContextPath();

String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.9.1.js" ></script>
<link rel="stylesheet" type="text/css" href="back/manager/easyui15/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="back/manager/easyui15/themes/icon.css">
<script type="text/javascript" src="back/manager/easyui15/jquery.min.js"></script>
<script type="text/javascript" src="back/manager/easyui15/jquery.easyui.min.js"></script>
<script type="text/javascript" src="back/manager/easyui15/jquery.edatagrid.js"></script>
<script type="text/javascript">

</script>
