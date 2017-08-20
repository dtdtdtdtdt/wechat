<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<<<<<<< HEAD
<script type="text/javascript">

</script>
=======

<script type="text/javascript">
//关闭所有的tab  
function closeAll(){  
    var tiles = new Array();  
      var tabs = $('#mainTt').tabs('tabs');      
      var len =  tabs.length;           
      if(len>0){  
        for(var j=0;j<len;j++){  
            var a = tabs[j].panel('options').title;               
            tiles.push(a);  
        }  
        for(var i=0;i<tiles.length;i++){               
            $('#mainTt').tabs('close', tiles[i]);  
        }  
      }  
}  
</script>
>>>>>>> branch 'master' of https://github.com/dtdtdtdtdt/wechat.git
