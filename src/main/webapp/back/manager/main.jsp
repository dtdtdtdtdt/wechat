<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp"%>
<title>微信后台管理系统</title>
<script type="text/javascript">
	$(function() {
		var treeDataRight = [
				{
					"text" : "用户管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/foods/addFoods.jsp'/>"
					}
				},
				{
					"text" : "账号角色管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/foods/findFoods.jsp'/>"
					}
				},
				{

					"text" : "自定义菜单",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/addMenu.jsp'/>"
					}
				},
				{

					"text" : "用户授权",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/foods/findFoods.jsp'/>"
					}
				} ];

		var treeDataManage = [ {
			"text" : "消息管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
			}
		}, {

			"text" : "素材管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
			}
		}, {

			"text" : "图文消息留言管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
			}
		}, {

			"text" : "数据统计",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
			}
		} ];

		showTree("RightTree", treeDataRight);
		showTree("ManageTree", treeDataManage);

	});

	function showTree(treeId, data) {
		$("#" + treeId).tree({
			data : data,
			onClick : function(node) {
				if (node.attributes) {
					openTab(node);
				}
			}
		});
	}

	function openTab(node) { //node有text,url
		if ($("#mainTt").tabs("exists", node.text)) {
			$("#mainTt").tabs("select", node.text)
		} else {
			$("#mainTt").tabs("add", {
				title : node.text,
				selected : true,
				tools : [ {
					iconCls : 'icon-clear',
					handler : function() {
						$.messager.confirm('温馨提示', '确认关闭吗?', function(r){
							if (r){
								$("#mainTt").tabs("close", node.text);
							}
						});
					}
				} ],
				content : node.attributes.url
			})
		}
	}
</script>

</head>
<body class="easyui-layout layout panel-noscoll">
	<div data-options="region:'north'" style="height: 50px;">
		<div style="font-size:25px;height:auto;text-align:center">
			微信后台管理界面
			<div style="font-size:20px;float:right;margin-right:10px;">
				欢迎您，${admin.aname }&nbsp;&nbsp;<a href="#">退出</a>
			</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 50px;">
		<div style="font-size:20px;height:auto;text-align:center">
			刘翔、丁婷、朱鹏 &copy; 版权所有
		</div>
	</div>
	<div data-options="region:'east',split:true,state:closed" title="East" style="width: 50px;">右边</div>
	<div data-options="region:'west',split:true" title="菜单" style="width: 150px;">
		<div class="easyui-accordion" style="width: 150px; height: 300px;">

			<div title="安全权限" style="">
				<div class="easyui-panel" style="padding: 5px">
					<ul id="RightTree" class="easyui-tree" data-options="animate:true,state:closed,fit:true">
						
					</ul>
				</div>
			</div>

			<div title="微信后台管理" style="overflow: auto; padding: 5px;">
				<div class="easyui-panel" style="padding: 5px">
					
					<ul id=ManageTree class="easyui-tree" data-options="animate:true,state:closed,fit:true">
					</ul>
				</div>
			</div>

		</div>
	</div>

	<div data-options="region:'center',title:'主操作区'">
		<!-- tabs区，有很多的tab -->
		<div id="mainTt" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="待处理业务区">欢迎您，源辰信息！</div>
		</div>
	</div>

</body>
</html>