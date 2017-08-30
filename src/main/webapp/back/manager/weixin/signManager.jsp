<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.text.*,java.util.*"%>
<%@ include file="../head.jsp" %>


<script type="text/javascript">

	$(function(){
		$('#operateListTable').datagrid({
			url:'back/findAllSign.action',   //查询时加载的URL
			pagination:true,   //显示分页
			pageSize:50,       //默认分页的条数
			fitColumns:true,   //自适应列
			fit:true,   	   //自动补全
			loadMsg:"正在为您加载数据......",
			idField:"sid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	 //显示行号
			nowrap:"true",		//不换行显示
			sortName:"integration",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			sortOrder:"desc",   //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},


			columns:[[{
				field:'UserName',  //这些属性对象Javabean中的名字，注意大小写
				title:'用户名',
				width:30,
				align:'center'

			},{
				field:'headUrl',
				title:'头像',
				align:'center',
				width:50,
				formatter:function(value,row){
				     var str = "";
				     if(value!="" || value!=null){
				     str = "<img style=\"height: 50px;width: 50px;\" src=\""+value+"\"/>";
				     return str;
				     }
				}
			},{
				field:'signCount',
				title:'签到天数',
				width:50,
				align:'center'
			},{
				field:'integration',
				title:'签到总积分',
				width:50,
				align:'center'

			},{
				field:'Timesformat',
				title:'最近签到日期',
				width:50,
				align:'center',
				formatter:function(value,row){
					var time=new Date(parseInt(value) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");  
					return time; 
				}

			}]]
		});		
	});
	




	
	
</script>
<title>粉丝签到管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" title="数据备份记录" style="height: 70%">
		<table id="operateListTable"></table>
	</div>

</body>
</html>





