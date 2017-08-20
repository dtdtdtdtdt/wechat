<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp"%>
<title>微信后台管理系统</title>
<script type="text/javascript">
	$(function() {
		var safeTree = [
				{
					"text" : "用户管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/manageAdmins.jsp' />"
					}
				},
				{
					"text" : "角色管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/manageRole.jsp' />"
					}
				}];

		var messageTree = [
			{
			"text" : "关注消息",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			}, {

			"text" : "所有消息",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			}, {

			"text" : "添加视频",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			}, {

			"text" : "添加音乐",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			}, {

			"text" : "添加图片",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			}, {

			"text" : "添加文本",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' />"
				}
			},{

				"text" : "关键字回复",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' />"
					}
			}];

		var sourceTree = [
			{
				"text" : "添加素材",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "修改素材",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{

				"text" : "删除素材",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{

				"text" : "所有素材",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			}];
		
		var userTree = [
			{
				"text" : "查看粉丝",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/findUsers.jsp' />"
				}
			},
			{
				"text" : "粉丝统计",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			}];
		
		var menuTree = [
			{
				"text" : "一级菜单",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "二级菜单",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "删除菜单",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "所有菜单",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			}];
		
		var smallTree = [
			{
				"text" : "机器人",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "游戏",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "智能服务",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			},
			{
				"text" : "待定",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'/>"
				}
			}];
		
		showTree("safeTree", safeTree);
		showTree("messageTree", messageTree);
		showTree("sourceTree", sourceTree);
		showTree("userTree", userTree);
		showTree("menuTree", menuTree);
		showTree("smallTree", smallTree);
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
	};
</script>

</head>
<body class="easyui-layout layout panel-noscoll">
	<div data-options="region:'north'" style="height: 50px;">
		<div style="float:left;">
			<embed style="height:45px;" src="swf/style_time.swf"></embed>
		</div>
		<div style="font-size:25px;float:left;margin-left:350px;margin-top:5px">
			微信后台管理界面
		</div>
		<div style="font-size:15px;float:right;margin-right:5px;margin-top:10px;">
				欢迎您，${admin.aname }&nbsp;&nbsp;<a href="adminLogout.action">退出</a>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 50px;">
		<div style="font-size:20px;height:auto;text-align:center">
			刘翔、丁婷、朱鹏 &copy; 版权所有
		</div>
	</div>
	<div data-options="region:'east',split:true" title="工具箱"  style="width:187px;">
		<div class="easyui-calendar" style="width: 30%, height: 40%"></div>
		<a onClick="closeAll()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭所有窗口</a>
	</div>
	<div data-options="region:'west',split:true,state:closed" title="菜单" style="width: 150px;">
		<div class="easyui-accordion" style="width: 143px; height: 350px;">

			<c:forEach items="${menuList }" var="m">
				<div title="${m.mtitle }" style="split:true">
					<div class="easyui-panel" style="padding: 5px">
						<ul id="${m.menu }" class="easyui-tree" data-options="animate:true,state:closed,fit:true">
							
						</ul>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div data-options="region:'center',title:'操作显示页面'">
		<!-- tabs区，有很多的tab -->
		<div id="mainTt" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="操作"></div>
		</div>
	</div>

</body>
</html>