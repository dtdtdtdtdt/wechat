<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../head.jsp" %>
<script type="text/javascript">

$(function(){
	$('#manTypeTable').datagrid({
		url:'back/findAllWxshopUser.action',   //查询时加载的URL
		pagination:true,   //显示分页
		pageSize:50,       //默认分页的条数
		fitColumns:true,   //自适应列
		fit:true,   	   //自动补全
		loadMsg:"正在为您加载数据。。。",
		idField:"userid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
		rownumbers:"true",	 //显示行号
		nowrap:"true",		//不换行显示
		sortName:"openid",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
		singleSelect:true,
		//以上的四种增删改查操作，只要失败，都会回调这个onError
		onError:function(a,b){
			$.messager.alert("错误","操作失败");
		},
		
		toolbar:[{
			text:"待定",
			iconCls:'icon-add',
			handler:function(){  
			}
		}],
		
		columns:[[{
			field:'ck',
			checkbox:'true'
		},{
			field:'userid',
			title:'用户编号',
			width:15,
			align:'center',
			hidden:'true'
		},{
			field:'openid',
			title:'微信id',
			width:30,
			align:'center'
		},{
			field:'name',
			title:'姓名',
			width:50,
			align:'center'
		},{
			field:'tel',
			title:'电话',
			align:'center',
			width:10
		},{
			field:'address',
			title:'地址',
			width:20,
			align:'center'
		}]]
	});		
});

</script>
</head>
<body>
	<table id="manTypeTable"></table>
</body>
</html>