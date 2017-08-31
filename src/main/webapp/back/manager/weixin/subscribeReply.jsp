<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.text.*,java.util.*"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
	
	//一进来就加载
	$(function(){
	
		$('#manTypeTable').datagrid({
			url:'back/findAllSubscribeReply.action',   //查询时加载的URL
			pagination:true,   //显示分页
			pageSize:10,       //默认分页的条数
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
				text:"添加关注回复设置",
				iconCls:'icon-add',
				handler:function(){
					$('#dlg').dialog('open').dialog('center').dialog('setTitle','');
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
								url : "back/delSubscribeReplyBySid.action",
								dataType : "JSON",
								success : function(data) {
									if (data.code == 1) {
										alert("删除成功！");
										location.href = "back/manager/weixin/subscribeReply.jsp";
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
			}],
			
			columns:[[{
				field:'keywords',  //这些属性对象Javabean中的名字，注意大小写
				title:'关注回复',
				width:50,
				align:'center'

			}]]
		});		
	});

	
	function submitForm(){
		var keywords = $("#keywords").val();
		if(keywords!=null&&keywords!=""){
			$('#ff').form('submit',{
				onSubmit:function(){
		        	//发送一个ajax请求
					$.ajax({
						type:"POST",
						url:"back/addSubscribeReply.action",
						data:$("#ff").serialize(),
						dataType:"JSON",
						success:function(data){
							if(data.code==1){
								alert("增加成功！");
								location.href = "back/manager/weixin/subscribeReply.jsp";
							}else{
								alert("失败，原因："+data.msg);
							}
						}
					});
				}
			});
		}else{
			alert("请填写关键字回复设置中的关键字！");
		}

	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	
	
	
</script>
<title>关注回复推送设置</title>
</head>
<body>



	<table id="manTypeTable"></table>

	<div id="dlg" class="easyui-dialog" style="text-align:center;width:450px;height:250px;padding:10px 20px" closed="true">
		
		<div class="easyui-panel" title="增加关注回复" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>对应关键字</td>
	    			<td><input class="easyui-textbox" type="text" name="keywords" id="keywords" data-options="required:true"></input></td>
	    		</tr>

	    		

	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
	    </div>
	    </div>
	</div>
		


	


</body>
</html>


