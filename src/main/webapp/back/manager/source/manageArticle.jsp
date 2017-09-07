<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
	$(function(){
		$('#SourceTable').edatagrid({
			url:'back/getSourceList.action',   //查询时加载的URL
			pagination:true,     //开启分页  
			pageSize:10,         //默认分页的条数
			pageNumber:1,        //第几页显示（默认第一页，可以省略） 
			fitColumns:true,     //自适应列
			fit:true,   	     //自动补全
			title:"查看图文素材列表",
			idField:"title",		  //标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	  //显示行号
			nowrap:"true",		  //不换行显示
			sortName:"title",		  //排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			sortOrder:"desc",     //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},
			columns:[[{
				field:'title',
				title:'标题',
				width:80,
				align:'center'
			},{
				field:'author',
				title:'作者',
				width:80,
				align:'center'		
			},{
				field:'digest',
				title:'摘要',
				width:100,
				align:'center'		
			},{
				field:'content',
				title:'内容',
				width:120,
				align:'center'	
			 },{
				 field:'action',
				 title:'操作', 
				 width:60,
				 align:'center',
				 formatter:function(value,row,index){
				 	if (row){
	 					var c ='<a href="javascript:void(0)" onclick="delRow('+row.pid+')">删除</a>'; 		
						return c;
					}
				 }
			}]]
		});
	});
	
	/* function delRow(value){
		alert("您要删除此图片信息吗？");
		$.ajax({
			type:"POST",
			data:"pid="+value,
			url:"back/delPicture.action",
			dataType:"JSON",
			success:function(data){
				if(data.code ==1){
					location.href ="back/manager/source/managePicture.jsp"
			}else{
				alert("删除失败，"+data.msg);
				}
			}
		})
	};	 */
</script>
<title>管理素材消息</title>
</head>
<body>
	<table id="SourceTable"></table>	
</body>
</html>
