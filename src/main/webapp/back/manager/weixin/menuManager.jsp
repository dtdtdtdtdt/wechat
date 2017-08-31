<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.text.*,java.util.*"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
	
	//一进来就加载一级菜单
	$(function(){
	
		$('#manTypeTable').datagrid({
			url:'back/findAllFirstMenu.action',   //查询时加载的URL
			pagination:true,   //显示分页
			pageSize:10,       //默认分页的条数
			fitColumns:true,   //自适应列
			fit:true,   	   //自动补全
			loadMsg:"正在为您加载数据......",
			//title:"微信公众号菜单管理",
			idField:"fid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	 //显示行号
			nowrap:"true",		//不换行显示
			// sortName:"ktype",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			// sortOrder:"desc",   //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},
			
			
			
			//一级二级菜单切换工具条
			
			toolbar:[{
				text:"添加一级菜单",
				iconCls:'icon-add',
				handler:function(){
					$('#dlg').dialog('open').dialog('center').dialog('setTitle','');
				}
			},'-',{
				text:"添加二级菜单",
				iconCls:'icon-add',
				handler:function(){
					//发送一个ajax将dlg2中的拼接好
					$.ajax({
						type : "POST",
						url : "back/findAllFirstMenuUse.action",
						dataType : "JSON",
						success : function(data) {
							if (data.code == 1) {
								$("#selectidfirst").html("");
								$(data.obj).each(function(index,item){
									var str ='';
									str+='<option value='+ item.fid +'>'+ item.name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
									$("#selectidfirst").html($("#selectidfirst").html()+str);
								});	
							} else {
								alert("查询失败！" + data.msg);
							}
						}
					});
					
					$('#dlg2').dialog('open').dialog('center').dialog('setTitle','');
				}
			},'-',{
				text:"删除",
				iconCls:'icon-remove',
				handler:function(){
					var row=$("#manTypeTable").datagrid('getSelected');
					if (row!=null){
				        if (confirm("确认删除吗")) {
				        	//发送一个ajax请求
							$.ajax({
								type : "POST",
								data : "fid="+row.fid,
								url : "back/deleteFirstMenuByFid.action",
								dataType : "JSON",
								success : function(data) {
									if (data.code == 1) {
										alert("删除成功！");
										location.href = "back/manager/weixin/menuManager.jsp";
									} else {
										alert("删除失败！" + data.msg);
									}
								}
							});
				        } else {
				            alert("不删除") ;
				        }
					}else{
						alert("请选择你要删除的选项!") ;
					}
				}
			},'-',{
				text:"生成菜单",
				iconCls:'icon-tip',
				handler:function(){
					//发一个ajax请求
					$.ajax({
						type: "POST",
						url: "back/makeMenu.action",
						dataType: "JSON",
						success:function(data){
							if (data.code == 1) {
								alert("生成成功！" );
							} else {
								alert("生成失败！" + data.msg);
							}
						}
						
					});
				}
			}],
			
			
			columns:[[{
				field:'name',  //这些属性对象Javabean中的名字，注意大小写
				title:'一级菜单名称',
				width:30,
				align:'center'

			},{
				field:'type',
				title:'类型',
				width:50,
				align:'center',
				formatter:function(value,row){
				     if(value=="click"){
							return "点击事件";
					  }else if(value=="view"){
							return "浏览事件";
					  }else if(value=="sub_button"){
							return "包含子菜单";
					  }
					}
			},{
				field:'url',
				title:'跳转链接',
				align:'center',
				width:50
			},{
				field:'fid',  
				title:'一级菜单编号',
				width:30,
				align:'center'

			}]]
		});		
	});

	
	


	

	//选择二级菜单时弹窗显示二级菜单
    function showSecondMenu(){		
        var objS = document.getElementById("menuModuleId"); //通过id获得这个元素
        var value = objS.options[objS.selectedIndex].value; //获得option中的值
        if(value == 2){ 
        	//发送一个ajax请求获取数据 获取所有一级菜单的名字
			$('#manTypeTable').datagrid({
				url:'back/findAllSecondMenu.action',   //查询时加载的URL
				pagination:true,   //显示分页
				pageSize:20,       //默认分页的条数
				fitColumns:true,   //自适应列
				fit:true,   	   //自动补全
				loadMsg:"正在为您加载数据......",
				//title:"微信公众号菜单管理",
				idField:"sid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
				rownumbers:"true",	 //显示行号
				nowrap:"true",		//不换行显示
				// sortName:"ktype",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
				// sortOrder:"desc",   //排序方式
				singleSelect:true,
				//以上的四种增删改查操作，只要失败，都会回调这个onError
				onError:function(a,b){
					$.messager.alert("错误","操作失败");
				},
				
				
				
				toolbar:[{
					text:"添加一级菜单",
					iconCls:'icon-add',
					handler:function(){
						$('#dlg').dialog('open').dialog('center').dialog('setTitle','');
					}
				},'-',{
					text:"添加二级菜单",
					iconCls:'icon-add',
					handler:function(){
						$.ajax({
							type : "POST",
							url : "back/findAllFirstMenuUse.action",
							dataType : "JSON",
							success : function(data) {
								if (data.code == 1) {
									$("#selectidfirst").html("");
									$(data.obj).each(function(index,item){
										var str ='';
										str+='<option value='+ item.fid +'>'+ item.name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
										$("#selectidfirst").html($("#selectidfirst").html()+str);
									});	
								} else {
									alert("查询失败！" + data.msg);
								}
							}
						});
						$('#dlg2').dialog('open').dialog('center').dialog('setTitle','');
					}
				},'-',{
					text:"删除",
					iconCls:'icon-remove',
					handler:function(){
						var row=$("#manTypeTable").datagrid('getSelected');
						if (row!=null){
					        if (confirm("确认删除吗")) {
					        	//发送一个ajax请求
								$.ajax({
									type : "POST",
									data : "sid="+row.sid,
									url : "back/deleteSecondBySid.action",
									dataType : "JSON",
									success : function(data) {
										if (data.code == 1) {
											alert("删除成功！");
											location.href = "back/manager/weixin/menuManager.jsp";
										} else {
											alert("删除失败！" + data.msg);
										}
									}
								});
					        } else {
					            alert("不删除") ;
					        }
						}else{
							alert("请选择你要删除的选项!") ;
						}
					}
				},'-',{
					text:"生成菜单",
					iconCls:'icon-tip',
					handler:function(){
						//发一个ajax请求
						$.ajax({
							type: "POST",
							url: "back/makeMenu.action",
							dataType: "JSON",
							success:function(data){
								if (data.code == 1) {
									alert("生成成功！" );
								} else {
									alert("生成失败！" + data.msg);
								}
							}
							
						});
					}
				}],
				
				
				//二级菜单
				columns:[[{
					field:'name',  //这些属性对象Javabean中的名字，注意大小写
					title:'二级菜单名称',
					width:20,
					align:'center'
				},{
					field:'type',
					title:'类型',
					width:20,
					align:'center',
					formatter:function(value,row){
					     if(value=="click"){
								return "点击事件";
						  }else if(value=="view"){
								return "浏览事件";
						  }else if(value=="sub_button"){
								return "包含子菜单";
						  }else {
							  return value;
						  }
						}
				},{
					field:'url',
					title:'跳转链接',
					align:'center',
					width:20
				},{
					field:'fid',  //这些属性对象Javabean中的名字，注意大小写
					title:'所属一级菜单编号',
					width:20,
					align:'center'
	
				}]]
			});	
        }else if(value==1){
    		$('#manTypeTable').datagrid({
    			url:'back/findAllFirstMenu.action',   //查询时加载的URL
    			pagination:true,   //显示分页
    			pageSize:10,       //默认分页的条数
    			fitColumns:true,   //自适应列
    			fit:true,   	   //自动补全
    			loadMsg:"正在为您加载数据......",
    			//title:"微信公众号菜单管理",
    			idField:"fid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
    			rownumbers:"true",	 //显示行号
    			nowrap:"true",		//不换行显示
    			// sortName:"ktype",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
    			// sortOrder:"desc",   //排序方式
    			singleSelect:true,
    			//以上的四种增删改查操作，只要失败，都会回调这个onError
    			onError:function(a,b){
    				$.messager.alert("错误","操作失败");
    			},
    			
    		
    			
    			//一级二级菜单切换工具条
    			
				toolbar:[{
					text:"添加一级菜单",
					iconCls:'icon-add',
					handler:function(){
						$('#dlg').dialog('open').dialog('center').dialog('setTitle','');
					}
				},'-',{
					text:"添加二级菜单",
					iconCls:'icon-add',
					handler:function(){
						$.ajax({
							type : "POST",
							url : "back/findAllFirstMenuUse.action",
							dataType : "JSON",
							success : function(data) {
								if (data.code == 1) {
									$("#selectidfirst").html("");
									$(data.obj).each(function(index,item){
										var str ='';
										str+='<option value='+ item.fid +'>'+ item.name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
										$("#selectidfirst").html($("#selectidfirst").html()+str);
									});	
								} else {
									alert("查询失败！" + data.msg);
								}
							}
						});
						$('#dlg2').dialog('open').dialog('center').dialog('setTitle','');
					}
				},'-',{
					text:"删除",
					iconCls:'icon-remove',
					handler:function(){
						var row=$("#manTypeTable").datagrid('getSelected');
						if (row!=null){
					        if (confirm("确认删除吗")) {
					        	//发送一个ajax请求
								$.ajax({
									type : "POST",
									data : "fid="+row.fid,
									url : "back/deleteFirstMenuByFid.action",
									dataType : "JSON",
									success : function(data) {
										if (data.code == 1) {
											alert("删除成功！");
											location.href = "back/manager/weixin/menuManager.jsp";
										} else {
											alert("删除失败！" + data.msg);
										}
									}
								});
					        } else {
					            alert("不删除") ;
					        }
						}else{
							alert("请选择你要删除的选项!") ;
						}
					}
				},'-',{
					text:"生成菜单",
					iconCls:'icon-tip',
					handler:function(){
						//发一个ajax请求
						$.ajax({
							type: "POST",
							url: "back/makeMenu.action",
							dataType: "JSON",
							success:function(data){
								if (data.code == 1) {
									alert("生成成功！" );
								} else {
									alert("生成失败！" + data.msg);
								}
							}
							
						});
					}
				}],
    			
    			columns:[[{
    				field:'name',  //这些属性对象Javabean中的名字，注意大小写
    				title:'一级菜单名称',
    				width:30,
    				align:'center',
    				editor:{
    					type:'validatebox',
    					options:{
    						required:true
    					}
    				}

    			},{
    				field:'type',
    				title:'类型',
    				width:50,
    				align:'center',
    				formatter:function(value,row){
    				     if(value=="click"){
    							return "点击事件";
    					  }else if(value=="view"){
    							return "浏览事件";
    					  }else if(value=="sub_button"){
    							return "包含子菜单";
    					  }
    					}
    			},{
    				field:'url',
    				title:'跳转链接',
    				align:'center',
    				width:50
    			},{
    				field:'fid',  //这些属性对象Javabean中的名字，注意大小写
    				title:'一级菜单编号',
    				width:30,
    				align:'center'

    			}]]
    		});	
        	
        	
        	
        	
        	
        }
    }
	
   

	
	//用户点击视图url 则显示url填写框 
	function showAll() {
		var objS = document.getElementById("typeselect"); //通过id获得这个元素
		var value = objS.options[objS.selectedIndex].value; //获得option中的值
		if( value==1){
			$("#bangDingEvent").show();
			$("#url3").show();
			$("#url2").hide();
		}
		 else if (value == 2)  //视图url和包含子菜单类型
		{
			$("#url2").show();
			$("#bangDingEvent").hide();  //选择点击类型的事件选择
			$("#url3").hide();

		} else if(value==3){ // 点击和包含子菜单
			$("#url2").hide();
			$("#bangDingEvent").hide();
			$("#url3").hide();

		}
	}
	
	//二级菜单选择
	function showAll2() {
		var objS = document.getElementById("typeselect2"); //通过id获得这个元素
		var value = objS.options[objS.selectedIndex].value; //获得option中的值
		if( value==1){
			$("#2url").hide(); //点击
			$("#url4").show();
			$("#bangDingEvent2").show();
		}
		 else if (value == 2)  //视图url
		{
			$("#2url").show();
			$("#bangDingEvent2").hide();
			$("#url4").hide();
		}
	}
	
	
	//提交一级菜单的增加
	function submitForm(){
		var name = $("#name").val();
		if(name!=null&&name!=""){
			$('#ff').form('submit',{
				onSubmit:function(){
		        	//发送一个ajax请求
					$.ajax({
						type:"POST",
						url:"back/addFirstMenu.action",
						data:$("#ff").serialize(),
						dataType:"JSON",
						success:function(data){
							if(data.code==1){
								alert("增加成功！");
								location.href = "back/manager/weixin/menuManager.jsp";
							}else{
								alert("失败，原因："+data.msg);
							}
						}
					});
				}
			});
		}else{
			alert("请填写名称！");
		}

	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	//二级级菜单的增加
	function submitForm2(){
		var name2 = $("#name2").val();
		var url2 = $("#2url2").val();
		if( name2!=""&&url2!=""){
			$('#ff2').form('submit',{
				onSubmit:function(){
		        	//发送一个ajax请求
					$.ajax({
						type:"POST",
						url:"back/addSecondMenu.action",
						data:$("#ff2").serialize(),
						dataType:"JSON",
						success:function(data){
							if(data.code==1){
								alert("增加成功！");
								location.href = "back/manager/weixin/menuManager.jsp";
							}else{
								alert("失败，原因："+data.msg);
							}
						}
					});
					return $(this).form('enableValidation').form('validate');
				}
			});
		}else{
			alert("名称和链接url不能为空！");
		}

	}
	function clearForm2(){
		$('#ff2').form('clear');
	}

	
	
	
	
	
	
	
	
	
</script>
<title>微信公众号菜单管理</title>
</head>
<body>

	<select id="menuModuleId" style="width: 100px" onchange="showSecondMenu()">
		<option value="1">一级菜单</option>
		<option value="2">二级菜单</option>
	</select>
	<br/>

	<table id="manTypeTable"></table>

	<!-- 增加一级菜单弹窗 -->
	<div id="dlg" class="easyui-dialog" style="text-align:center;width:450px;height:400px;padding:10px 20px" closed="true">
		
		<div class="easyui-panel" title="增加一级菜单" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>名称</td>
	    			<td><input class="easyui-textbox" type="text" name="name" id="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>类型</td>
	    			<td>
	    				<select  id="typeselect" onchange="showAll()" name="type">
	    					<option value="1">点击</option>
	    					<option value="2">视图</option>
	    					<option value="3">包含子菜单</option>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<!-- 点击事件选择什么样式的推送 -->
	    		<tr  id="bangDingEvent">
	    			<td>绑定事件</td>
	    			<td>
	    				<select  id="bangDing" onchange="showAll()" name="bangdingType">
	    					<option value="1">图片推送</option>
	    					<option value="2">语音推送</option>
	    					<option value="3">视频推送</option>
	    					<option value="4">图文推送</option>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<!-- 点击事件推送素材来自回复关键字设置  避免重复代码太多 -->
	    		<tr   id="url3">
	    			<td>推送内容请选择回复关键字素材</td>
	    			<td><input class="easyui-textbox" name="bangdingName" data-options="multiline:true" style="height:60px;width:100px"></input></td>
	    		</tr>
	    		
	    		
	    		<tr  hidden="hidden" id="url2">
	    			<td>链接url</td>
	    			<td><input class="easyui-textbox" name="url" data-options="multiline:true" style="height:60px;width:200px"></input></td>
	    		</tr>

	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
	    </div>
	    </div>
	</div>
		


	<!-- 增加二级菜单弹窗 -->
	<div id="dlg2" class="easyui-dialog" style="text-align:center;width:450px;height:400px;padding:10px 20px" closed="true">
		
		<br/>
		<br/>
		
		<div class="easyui-panel" title="增加二级菜单" style="width:400px">
		<div style="padding:2px 60px 20px 60px">
	    <form id="ff2" method="post">
	    	<table cellpadding="5">
	    	
	    		<tr>
	    			<td>所属一级菜单</td>
	    			<td>
	    				<select id="selectidfirst" name="ftype" onchange="showAllFirstMenu()">
							<!-- 这个名字需要来自所有一级菜单  所有查到数据后进行拼接！ -->
							<option value="1">签到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
						</select>
					</td>
	    		</tr>
	    	
	    	
	    		<tr>
	    			<td>名称</td>
	    			<td><input class="easyui-textbox" type="text" name="name" id="name2" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>类型</td>
	    			<td>
	    				<select  id="typeselect2" onchange="showAll2()" name="type">
	    					<!-- 点击菜单暂时去掉 -->
	    					<!-- <option value="1">点击</option>  -->
	    					<option value="2">视图</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr   id="2url">
	    			<td>链接url</td>
	    			<td><input class="easyui-textbox" name="url" id="2url2" data-options="multiline:true" style="height:60px;width:200px"></input></td>
	    		</tr>
	    		
	    		
	    		<!-- 点击事件选择什么样式的推送 -->
	    		<tr hidden="hidden"  id="bangDingEvent2">
	    			<td>绑定事件</td>
	    			<td>
	    				<select  id="bangDing" onchange="showAll2()" name="bangdingType">
	    					<option value="1">图片推送</option>
	    					<option value="2">语音推送</option>
	    					<option value="3">视频推送</option>
	    					<option value="4">图文推送</option>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<!-- 点击事件推送素材来自回复关键字设置  避免重复代码太多 -->
	    		<tr  hidden="hidden"  id="url4">
	    			<td>推送内容请选择回复关键字素材</td>
	    			<td><input class="easyui-textbox" name="bangdingName" data-options="multiline:true" style="height:60px;width:100px"></input></td>
	    		</tr>
	    		

	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm2()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm2()">清空</a>
	    </div>
	    </div>
	</div>




</body>
</html>


