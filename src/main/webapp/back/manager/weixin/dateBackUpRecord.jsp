<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#operateListTable').datagrid(
			{
				url : 'back/databaseBackUpRecord.action',
				pagination : true,//分页
				pageSize : 100,
				pageList : [ 20, 30, 60, 80, 100 ],
				fitColumns : true,
				fit : true,//自适应，将分页框分布在最低端
				rownumbers : true,
				nowrap : true,//不折叠
				singleSelect : true,//一次只选取一行
				loadMsg:"正在为您加载数据......",
				idField:"did",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
				sortName:"times",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
				sortOrder:"desc",   //排序方式

				columns : [ [
						{
							field : 'operator',
							title : '备份者',
							width : 50,
							align : 'center'
						},
						{
							field : 'filename',
							title : '文件名',
							width : 80,
							align : 'center'
						},
						{
							field : 'filepath',
							title : '文件路径',
							width : 150,
							align : 'center'

						},
						{
							field : 'timesformat', // 后台转换了格式！
							title : '备份时间',
							width : 80,
							align : 'center',
							formatter:function(value,row){
								var time=new Date(parseInt(value) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");  
								return time; 
							}

						},
						{
							field : 'type',
							title : '备份类型',
							width : 80,
							align : 'center'
						},						
						{
							field : 'deadline',
							title : '保留剩余时间',
							width : 80,
							align : 'center'
						},
						{
							field : '_operate',
							title : '操作',
							width : 150,
							align : 'center',
							formatter : function formatOper(val,
									row, index) {
								var str = '<a href="javascript:void(0)" onclick="rollback('
										+ index + ')">还原</a>'
								str += ' <a href="javascript:void(0)" onclick="deleteTable('
										+ index + ')">删除</a> ';
								return str;
							}
						} ] ]
			});
	});

	//删除操作
	function deleteTable(index) {
		$('#operateListTable').datagrid('selectRow', index);//关键在这里
		var row = $('#operateListTable').datagrid('getSelected');
		$.ajax({
			url : "back/delDatabaseBackUpRecordByFilePath.action",
			type : "POST",
			data : "&filepath=" + row.filepath,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					alert("删除成功");
					location.href = "back/manager/weixin/dateBackUpRecord.jsp";
				} else {
					alert(data.msg);
				}
			}
		});
		
	}

	//还原操作
	function rollback(index) {
		$('#operateListTable').datagrid('selectRow', index);//关键在这里
		var row = $('#operateListTable').datagrid('getSelected');
		$.ajax({
			url : "back/rollBackDatabaseBackUpRecordByFilePath.action",
			type : "POST",
			data : "&filepath=" + row.filepath,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					alert("还原成功");
					$('#operateListTable').datagrid('reload');
				} else {
					alert(data.msg);
				}
			}
		});
		
	}
</script>


<body class="easyui-layout">
	<div data-options="region:'center'" title="数据备份记录" style="height: 70%">
		<table id="operateListTable"></table>
	</div>
</body>
</html>