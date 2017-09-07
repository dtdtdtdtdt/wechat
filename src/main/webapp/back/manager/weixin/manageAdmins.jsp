<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ page import="java.util.*,com.wx.common.bean.*" %>
<script type="text/javascript">
	var editFlag=undefined;
	$(function(){
		<% 
			List<Role> list=(List<Role>)session.getAttribute("roleList");
			if(list!=null){
				String str="";
				for(Role r:list){
					str+="{\"text\":\""+r.getRole()+"\"},";
				}
		%>
		var roleName=[<%=str%>];
		<%
			}
		%>
		
		
		$('#manTypeTable').datagrid({
			url:'back/findAdmins.action',   //查询时加载的URL
			fitColumns:true,   //自适应列
			fit:true,   	   //自动补全
			loadMsg:"正在为您加载数据。。。",
			idField:"aid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	 //显示行号
			nowrap:"true",		//不换行显示
			sortName:"aid",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			sortOrder:"desc",   //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},
			
			//双击编辑
			onDblClickCell:function(rowIndex,field,value){
				if(editFlag != undefined){
					$("#manTypeTable").datagrid('endEdit',editFlag);
				}if(editFlag == undefined){
					$("#manTypeTable").datagrid('beginEdit',rowIndex);
					editFlag=rowIndex;
				}
			},
			
			onAfterEdit:function(){
				editFlag=undefined;
			},
			
			toolbar:[{
				text:"添加",
				iconCls:'icon-add',
				handler:function(){
					$.ajax({
						type : "POST",
						url : "back/reloadRole.action",
						dataType : "JSON",
						success : function(data) {
							if (data.code == 0) {
								alert("加载角色失败！" + data.msg);
							}
						}
					});
					$('#dlg').dialog('open').dialog('center').dialog('setTitle','添加管理员');
				}
			},'-',{
				text:"删除",
				iconCls:'icon-remove',
				handler:function(){
					$.messager.confirm('温馨提示', '确定删除用户?', function(r) {
		                if (r) {
		                	var row=$("#manTypeTable").datagrid('getSelected');
							if (row!=null){
								$.ajax({
									type : "POST",
									data : "aid="+row.aid,
									url : "back/deleteAdmins.action",
									dataType : "JSON",
									success : function(data) {
										if (data.code == 1) {
											alert("删除成功！");
											location.href = "back/manager/weixin/manageAdmins.jsp";
										} else {
											alert("删除失败！" + data.msg);
										}
									}
								});
							}else{
								alert("请选择操作的行！");
							}
		                }  
		            });
				}
			},'-',{
				text:"保存",
				iconCls:'icon-save',
				handler:function(){
					$("#manTypeTable").datagrid('endEdit',editFlag);
					var row=$("#manTypeTable").datagrid('getSelected');
					if (row!=null){
						$.ajax({
							type : "POST",
							data : "aid="+row.aid+"&aname="+row.aname+"&apwd="+row.apwd+"&role="+row.role,
							url : "back/updateAdmins.action",
							dataType : "JSON",
							success : function(data) {
								if (data.code == 1) {
									alert("修改成功！");
									location.href = "back/manager/weixin/manageAdmins.jsp";
								} else {
									alert("修改失败！" + data.msg);
								}
							}
						});
					}else{
						alert("请选择操作的行！");
					}
				}
			}],
			
			columns:[[{
				field:'ck',
				checkbox:'true'
			},{
				field:'aid',
				title:'用户编号',
				width:100,
				align:'center',
				hidden:'true'
			},{
				field:'aname',
				title:'用户名',
				width:100,
				align:'center',
				editor:{
					type:'validatebox',
					options:{
						required:true
					}
				}
			},{
				field:'apwd',
				title:'密码',
				width:100,
				align:'center',
				editor:{
					type:'validatebox',
					options:{
						required:true
					}
				},
				formatter:function(value,row){
				     var str = "";
				     if(value!="" || value!=null){
				     str = "******";
				     return str;
				     }
				}
			},{
				field:'role',
				title:'角色',
				width:100,
				align:'center',
				formatter:function(value,row){
					return row.role;
				},
				editor:{
					type:'combobox',
					options:{
						editable:false,
						required:true,
						data:roleName,
						valueField:'text',  
						textField:'text'
					}
				}
			},{
				field:'atime',
				title:'创建时间',
				width:100,
				align:'center',
				formatter:function(value,row){
					var d = new Date(value);
					return time=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
				}
			}]]
		});		
	});
	
	function add() {
		var aname=$("#aname").val();
		var apwd=$("#apwd").val();
		if(aname!=""&&apwd!=""){
			$.ajax({
				type : "POST",
				data : $("#adminForm").serialize(),
				url : "back/addAdmins.action",
				dataType : "JSON",
				success : function(data) {
					if (data.code == 1) {
						alert("添加成功！");
						location.href = "back/manager/weixin/manageAdmins.jsp";
					} else {
						alert("添加失败！" + data.msg);
					}
				}
			});
		}else{
			alert("用户名和密码不能为空！");
		}
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
	    equals: {
			validator: function(value,param){
				return value == $(param[0]).val();
			},
			message: '两次密码不正确！'
	    }
	});
</script>
<title>角色管理</title>
</head>
<body class="easyui-layout">
	<table id="manTypeTable" data-options="rownumbers:true,singleSelect:true"></table>
	
	<%--弹窗 --%>
	<div id="dlg" class="easyui-dialog" style="text-align:center;width:400px;height:300px;" closed="true">
		<form id="adminForm" method="post" novalidate>
			<br>
			<div>
				<label>&nbsp;角  色  名：</label>
					<select class="easyui-combobox" name="role">
						<c:forEach items="${roleList}" var="role">
							<option value="${role.role }">${role.role }</option>
						</c:forEach>
					</select>
			</div><br>
			<div>
				<label>&nbsp;账  户  名：</label>
				<input id="aname" name="aname" class="easyui-textbox" required>
			</div><br>
			<div>
				<label>&nbsp;&nbsp;密&nbsp;&nbsp;码&nbsp;&nbsp;：</label>
				<input id="apwd" name="apwd" class="easyui-textbox" type="password" required>
			</div><br>
			<div>
				<label>重复密码：</label>
				<input id="reapwd" type="password" class="easyui-textbox" validType="equals['#apwd']" required>
			</div><br>
			<div id="dlg-buttons">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onClick="add()">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
		</form>
	</div>
	
</body>
</html>