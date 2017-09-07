<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp"%>
<title>微信后台管理系统</title>
<script type="text/javascript">
	$(function() {
		var safeTree = [
				{
					"text" : "用户管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/manageAdmins.jsp'/>"
					}
				},{
					"text" : "角色管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/manageRole.jsp'/>"
					}
				},{
					"text" : "数据安全备份",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/dateBackUp.jsp'/>"
				    }
			    },{
					"text" : "数据备份记录",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/dateBackUpRecord.jsp'/>"
					}
				}];

		var messageTree = [
			{
				"text" : "关注消息",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/subscribeReply.jsp'/>"
				}
			},
			{
				"text" : "关键字管理",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/keyMsgManager.jsp'/>"
				}
			}];

		var sourceTree = [
			{
			"text" : "添加素材",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' src='back/manager/source/addArticle.jsp' />"
			}
		}, {
			"text" : "图片库管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' src='back/manager/source/managePicture.jsp'/>"
			}
		}, {
			"text" : "群发消息管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' src='back/manager/source/manageMass.jsp'/>"
			}
		}, {
			"text" : "素材列表管理",
			"attributes" : {
				"url" : "<iframe width='100%' height='100%' src='back/manager/source/manageArticle.jsp'/>"
			}
		}];
		
		var userTree = [
			{
				"text" : "查看粉丝",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/findUsers.jsp'/>"
				}
			},
			{
				"text" : "粉丝统计",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/showChart.jsp'/>"
				}
			},
			{
				"text" : "粉丝签到数据",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/signManager.jsp'/>"
				}
			}];
		
		var menuTree = [
			{
				"text" : "自定义菜单",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/menuManager.jsp'/>"
				}
			}];
		
		var smallTree = [
			{
				"text" : "机器人",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/robotStatus.jsp'/>"
				}
			},
			{
				"text" : "微信墙",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/wxwallStatus.jsp'//>"
				}
			},
			{
				"text" : "游戏",
				"attributes" : {
					"url" : "<iframe width='40%' height='100%' src='http://www.kxtui.com/c2/i.jsp?idcm=1392787&jgiscre=jHnMkp57rLsvSN3JPoR7UXieqcdF118w'/>"
				}
			},
			{
				"text" : "抽奖赚积分",
				"attributes" : {
					"url" : "<iframe width='40%' height='100%' src='ward/toIndex.action'/>"
				}
			},
			{
				"text" : "电影小助手",
				"attributes" : {
					"url" : "<iframe width='40%' height='100%' src='movie.jsp'/>"
				}
			}];
		
		var shopTree = [
			{
				"text" : "会员中心",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/shop/manageWxshopUser.jsp'/>"
				}
			},{
				"text" : "订单管理",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='404.jsp'/>"
				}
			},{
				"text" : "商品管理",
				"attributes" : {
					"url" : "<iframe width='100%' scrolling='no' height='100%' src='back/manager/shop/manageFood.jsp'/>"
				}
			}];
		
		
		showTree("safeTree", safeTree);
		showTree("messageTree", messageTree);
		showTree("sourceTree", sourceTree);
		showTree("userTree", userTree);
		showTree("menuTree", menuTree);
		showTree("smallTree", smallTree);
		showTree("shopTree", shopTree);
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
			});
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
	<div data-options="region:'south'" style="height: 20px;">
		<div style="font-size:15px;height:auto;text-align:center">
			刘翔、丁婷、朱鹏 &copy; 版权所有
		</div>
	</div>

	<div data-options="region:'east',split:true" title="工具箱"  style="width:187px;">
		<div class="easyui-calendar" style="width: 30%, height: 40%"></div>
		<a onClick="closeAll()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭所有窗口</a>
		<br/>
		<br/>
		   
	
		
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
			<div title="欢迎首页">欢迎您！</div>

		</div>
	</div>

</body>
</html>