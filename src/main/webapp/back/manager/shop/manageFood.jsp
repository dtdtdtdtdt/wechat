<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../head.jsp" %>
<script src="back/manager/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript">
	
	function showUploadImg(obj,picid){
		//判断浏览器是否支持FileReader接口
        if (typeof FileReader == 'undefined') {
            $.messager.alert('提示','当前浏览器不支持FileReader接口！');
            //使选择控件不可操作
            obj.setAttribute("disabled", "disabled");
            return;
        }
        var file = obj.files[0];
        var reader = new FileReader();
        reader.onload=function(e){
        	$("#"+picid).attr("src",e.target.result);
        }
        reader.readAsDataURL(file)
	}
</script>
<script type="text/javascript">
var editFlag=undefined;

$(function(){
	$('#manTypeTable').datagrid({
		url:'back/findAllWxShop.action',   //查询时加载的URL
		pagination:true,   //显示分页
		pageSize:50,       //默认分页的条数
		fitColumns:true,   //自适应列
		fit:true,   	   //自动补全
		loadMsg:"正在为您加载数据。。。",
		idField:"fid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
		rownumbers:"true",	 //显示行号
		nowrap:"true",		//不换行显示
		sortName:"fid",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
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
				$('#addFood').dialog('open').dialog('center').dialog('setTitle','添加菜品');
			}
		},{
			text:"删除",
			iconCls:'icon-remove',
			handler:function(){  
				$.messager.confirm('温馨提示', '确定删除该菜品?', function(r) {
	                if (r) {
	                	var row=$("#manTypeTable").datagrid('getSelected');
						if (row!=null){
							$.ajax({
								type : "POST",
								data : "fid="+row.fid,
								url : "back/deleteWxShop.action",
								dataType : "JSON",
								success : function(data) {
									if (data.code == 1) {
										alert("删除成功！");
										location.href = "back/manager/shop/manageFood.jsp";
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
		},{
			text:"保存",
			iconCls:'icon-save',
			handler:function(){
				$("#manTypeTable").datagrid('endEdit',editFlag);
				var row=$("#manTypeTable").datagrid('getSelected');
				if (row!=null){
					$.ajax({
						type : "POST",
						data : "fid="+row.fid+"&name="+row.name+"&normprice="+row.normprice+"&realprice="+row.realprice+"&stock="+row.stock,
						url : "back/updateWxShop.action",
						dataType : "JSON",
						success : function(data) {
							if (data.code == 1) {
								alert("修改成功！");
								location.href = "back/manager/shop/manageFood.jsp";
							}else{
								alert("修改失败！" + data.msg);
							}
						}
					});
				}else{
					alert("请选择操作的行！");
				}
			}
		}],
		
		rowStyler:function(index,row){   
	        if (row.stock==0){   
	            return 'background-color:red;color:white;';   
	        }   
	    },
		
		columns:[[{
			field:'ck',
			checkbox:'true'
		},{
			field:'fid',
			title:'菜品编号',
			width:15,
			align:'center',
			hidden:'true'
		},{
			field:'name',
			title:'菜名',
			width:50,
			align:'center',
			editor:{
				type:'validatebox',
				options:{
					required:true
				}
			}
		},{
			field:'normprice',
			title:'原价',
			align:'center',
			width:10,
			editor:{
				type:'validatebox',
				options:{
					required:true
				}
			}
		},{
			field:'realprice',
			title:'现价',
			width:10,
			align:'center',
			editor:{
				type:'validatebox',
				options:{
					required:true
				}
			}
		},{
			field:'detail',
			title:'详情',
			width:100,
			align:'center'
		},{
			field:'cover',
			title:'封面图片',
			width:20,
			align:'center',
			formatter:function(value,row){
			     var str = "";
			     if(value!="" || value!=null){
			     str = "<img style=\"height: 50px;width: 50px;\" src=\""+value+"\"/>";
			     return str;
			     }
			}
		},{
			field:'detaila',
			title:'详情图片a',
			width:20,
			align:'center',
			formatter:function(value,row){
			     var str = "";
			     if(value!="" || value!=null){
			     str = "<img style=\"height: 50px;width: 50px;\" src=\""+value+"\"/>";
			     return str;
			     }
			}
		},{
			field:'detailb',
			title:'详情图片b',
			width:20,
			align:'center',
			formatter:function(value,row){
			     var str = "";
			     if(value!="" || value!=null){
			     str = "<img style=\"height: 50px;width: 50px;\" src=\""+value+"\"/>";
			     return str;
			     }
			}
		},{
			field:'stock',
			title:'库存',
			width:20,
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

</script>
</head>
<body>
	<table id="manTypeTable"></table>
	<!-- 添加菜品弹窗 -->
	<div id="addFood" class="easyui-dialog" style="text-align:center;width:800px;height:450px;" closed="true">
		<form id="addFoodForm" action="back/addWxShop.action" method="post" enctype="multipart/form-data">
			<div style="margin:0 auto;"><br/>
				菜品名称：<input type="text" name="name" id="title" class="easyui-textbox" required />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				封面 图片：<input type="file" name="pic" id="cover" onchange="showUploadImg(this,'showpic')" accept="image/*" /><br/><br/>
				菜品原价：<input type="text" name="normprice" id="normprice" class="easyui-textbox" required />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				详情图片a：<input type="file" name="pic" id="detaila" onchange="showUploadImg(this,'showpic1')" accept="image/*" /><br/><br/>
				菜品现价：<input type="text" name="realprice" id="realprice" class="easyui-textbox" required />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				详情图片b：<input type="file" name="pic" id="detailb" onchange="showUploadImg(this,'showpic2')" accept="image/*" /><br/><br/>
				菜品库存：<input type="text" name="stock" id="stock" class="easyui-textbox" required />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<br/><br/>封面 图片：<img id="showpic" style="width:50px;height:50px;"/>&nbsp;&nbsp;&nbsp;&nbsp;详情图片a：<img id="showpic1" style="width:50px;height:50px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;详情图片b：<img id="showpic2" style="width:50px;height:50px;"/><br/>
				<br/><h3 style="float:left;">菜品详情：</h3><textarea class="ckeditor" name="detail"></textarea><br/><br/>
				<input type="submit" class="easyui-linkbutton" id="addBtn" value="添加" />
			</div>
		</form>
	</div>
	
</body>
</html>