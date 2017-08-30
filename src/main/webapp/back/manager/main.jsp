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
				},{
					"text" : "角色管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/manageRole.jsp' />"
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
			},
			{
				"text" : "粉丝签到数据",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/signManager.jsp' />"
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
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/robot.jsp'/>"
				}
			},
			{
				"text" : "游戏",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/robot2.jsp'/>"
				}
			},
			{
				"text" : "智能服务",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'  />"
				}
			},
			{
				"text" : "微信墙",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%'    />"
				}
			},			
			{
				"text" : "数据安全备份",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/dateBackUp.jsp'/>"
				}
			},
			{
				"text" : "数据备份记录",
				"attributes" : {
					"url" : "<iframe width='100%' height='100%' src='back/manager/weixin/dateBackUpRecord.jsp'/>"
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
			//判断微信自定义菜单被打开而且选中时发送ajax请求获取菜单信息展示
			/*
			if(node.text==="自定义菜单"){
				$.ajax({
					   url: "back/findFirstAndSecondMenu.action",
					   type: "POST",
					   dataType: "JSON",
					   success: function (data) {
						   
						   document.getElementById("menushowpanel").style.display="block";
						   
							$(data.rows).each(function(index,item){
								$("#menutree").html();
								var str ="";
								//先显示一级菜单
								if(item.type!="sub_button"){
									str+="<ul><li ><span>"+item.fname+"</span></li></ul>";
								}
								$("#menutree").html($("#menutree").html()+str);
							});	
							//显示含有二级菜单的一级菜单显示
							for(var i=0;i<data.rows.length;i++){
								var str ="";
								var item = data.rows[i];
								if( item.type=="sub_button" ){
									str+="<ul><li ><span>"+item.fname+"</span></li></ul>";
									//同时显示所属二级菜单
									$("#menutree").html($("#menutree").html()+str);
									break;
								}
							}
							// 所有二级菜单显示
							for(var i=0;i<data.rows.length;i++){
								var str ="";
								var item = data.rows[i];
								if( item.type=="sub_button" ){
									
									str+="<ul><ul><li>"+item.sname+"</li></ul></ul>";
								
									
									
								}
								$("#menutree").html($("#menutree").html()+str);
							}
							
							
							
							
							
					   }
				});
			}
			*/
			
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
		<br/>
		<br/>
		<!-- 用于微信自定义菜单展示处理 -->
		<!-- 
		<div class="easyui-panel" style="padding:5px"  id="menushowpanel" >
			<ul class="easyui-tree" id="menutree">
				<span>菜单</span>
				
			</ul>
		</div>
 		-->
		   
	
		
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