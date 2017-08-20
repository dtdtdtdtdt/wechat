<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
	var editFlag=undefined;
	$(function(){
		$('#manTypeTable').datagrid({
			url:'back/findRoles.action',   //查询时加载的URL
			pagination:true,   //显示分页
			pageSize:50,       //默认分页的条数
			fitColumns:true,   //自适应列
			fit:true,   	   //自动补全
			loadMsg:"正在为您加载数据。。。",
			idField:"rid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	 //显示行号
			nowrap:"true",		//不换行显示
			sortName:"rid",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			sortOrder:"desc",   //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},
			
			toolbar:[{
				text:"添加",
				iconCls:'icon-add',
				handler:function(){
					$('#dlg').dialog('open').dialog('center').dialog('setTitle','添加角色');
					
					$('#menuTable').datagrid({
						url:'back/findMenu.action',
						fitColumns:true,   //自适应列
						loadMsg:"正在为您加载数据。。。",
						idField:"mid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
						rownumbers:"true",	 //显示行号
						nowrap:"true",		//不换行显示
						singleSelect:false,
						//以上的四种增删改查操作，只要失败，都会回调这个onError
						onError:function(a,b){
							$.messager.alert("错误","操作失败");
						},
						
						columns:[[{
							field:'ck',
							checkbox:'true'
						},{
							field:'mtitle',
							title:'菜单名称',
							width:100,
							align:'center'
						},{
							field:'menu',
							title:'菜单树',
							width:100,
							align:'center',
							hidden:"true"
						}]]
					});
				}
			},'-',{
				text:"删除",
				iconCls:'icon-remove',
				handler:function(){
					$.messager.confirm('温馨提示', '确定删除角色?', function(r) {
		                if (r) {
		                	var row=$("#manTypeTable").datagrid('getSelected');
							if (row!=null){
								$.ajax({
									type : "POST",
									data : "role="+row.role,
									url : "back/deleteRole.action",
									dataType : "JSON",
									success : function(data) {
										if (data.code == 1) {
											alert("删除成功！");
											location.href = "back/manager/weixin/manageRole.jsp";
										} else {
											alert("删除失败！" + data.msg);
										}
									}
								});
							}
		                }  
		            });
				}
			}],
			
			columns:[[{
				field:'ck',
				checkbox:'true'
			},{
				field:'rid',
				title:'角色编号',
				width:100,
				align:'center',
				hidden:'true'
			},{
				field:'role',
				title:'角色名',
				width:100,
				align:'center',
				editor:{
					type:'validatebox',
					options:{
						required:true
					}
				}
			},{
				field:'mtitle',
				title:'权限',
				width:100,
				align:'center',
				editor:{
					type:'validatebox',
					options:{
						required:true
					}
				}
			}]]
		});		
	});
	
	function add() {
		// 返回被选中的行 然后集成的其实是 对象数组  
        var row = $('#menuTable').datagrid('getSelections');
		var role=$("#role").val();
        var i = 0;  
        var strTitle = "";
        var strMenu = "";
        for(i;i<row.length;i++){
            strTitle += row[i].mtitle;
            strMenu += row[i].menu;
            if(i < row.length-1){
            	strTitle += ',';
            	strMenu += ',';
            }else{
                break;  
            }
        }
		$.ajax({
			type : "POST",
			data : "role="+role+"&strTitle="+strTitle+"&strMenu="+strMenu,
			url : "back/addRole.action",
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					alert("添加成功！");
					location.href = "back/manager/weixin/manageRole.jsp";
				} else {
					alert("添加失败！" + data.msg);
				}
			}
		});
	}
	
</script>
<title>角色管理</title>
</head>
<body class="easyui-layout">
	<table id="manTypeTable" data-options="rownumbers:true,singleSelect:true"></table>
	
	<%--弹窗 --%>
	<div id="dlg" class="easyui-dialog" style="text-align:center;width:400px;height:300px;padding:10px 20px" closed="true">
		<form id="roleForm" method="post" novalidate>
			<br>
			<div>
				<label>&nbsp;角  色  名：</label>
				<input id="role" name="role" class="easyui-textbox" required>
			</div><br>
			<div>
				<label>权限选择：</label>
				<table id="menuTable" >
				</table>  

			</div><br>
			<div id="dlg-buttons">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onClick="add()">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
		</form>
	</div>
	
</body>
</html>