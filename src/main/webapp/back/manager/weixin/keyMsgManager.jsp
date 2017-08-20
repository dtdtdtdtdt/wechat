<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.text.*,java.util.*"%>
<%@ include file="../head.jsp" %>

<script src="js/jquery.form.js"></script>

<script type="text/javascript">
	var editFlag=undefined;
	$(function(){
		$('#manTypeTable').datagrid({
			url:'back/findAllKeyWords.action',   //查询时加载的URL
			pagination:true,   //显示分页
			pageSize:50,       //默认分页的条数
			fitColumns:true,   //自适应列
			fit:true,   	   //自动补全
			loadMsg:"正在为您加载数据......",
			title:"关键字回复管理",
			idField:"kid",		//标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	 //显示行号
			nowrap:"true",		//不换行显示
			sortName:"ktype",		//排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
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
				text:"添加文本图文",
				iconCls:'icon-add',
				handler:function(){
					$('#dlg').dialog('open').dialog('center').dialog('setTitle','');
				}
			},'-',{
				text:"添加媒体回复",
				iconCls:'icon-add',
				handler:function(){
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
								data : "kid="+row.kid,
								url : "back/deleteKeyWords.action",
								dataType : "JSON",
								success : function(data) {
									if (data.code == 1) {
										alert("删除成功！");
										location.href = "back/manager/weixin/keyMsgManager.jsp";
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
				title:'关键字',
				width:30,
				align:'center'

			},{
				field:'Content',
				title:'文本内容',
				width:50,
				align:'center'
			},{
				field:'MediaId',
				title:'媒体id',
				width:50,
				align:'center'

			},{
				field:'Title',
				title:'标题',
				width:50,
				align:'center'

			},{
				field:'Description',
				title:'详情描述',
				width:50,
				align:'center'
			},{
				field:'PicUrl',
				title:'推文封面图片链接',
				width:50,
				align:'center'
			},{
				field:'Url',
				title:'图文链接',
				width:50,
				align:'center'
			},{
				field:'ktype',
				title:'回复类型',
				align:'center',
				width:50,
				formatter:function(value,row){
				     if(value==0){
							return "文本";
					  }else if(value==1){
							return "图片";
					  }else if(value==2){
							return "语音";
					  }else if(value==3){
							return "视频";
					  }else if(value==4){
							return "图文";
					  }
				}
			}]]
		});		
	});
	
	//................
	
	//文本和图文
	function submitForm2(){
		$('#ff').form('submit',{
			onSubmit:function(){
	        	//发送一个ajax请求
				$.ajax({
					type:"POST",
					url:"back/keyMsgToReplyTexeAndNews.action",
					data:$("#ff").serialize(),
					dataType:"JSON",
					success:function(data){
						if(data.code==1){
							alert("保存成功！");
							location.href = "back/manager/weixin/keyMsgManager.jsp";
						}else{
							alert("失败，原因："+data.msg);
						}
					}
				});
				return $(this).form('enableValidation').form('validate');
			}
		});
	}
	function clearForm2(){
		$('#ff').form('clear');
	}


	//表单  上传其他类型  如 图片 ，视频 语音
		function submitForm(){
		$('#myform').form('submit',{
			onSubmit:function(){		
				$("#myform").ajaxSubmit({
					type:"POST",
					url:"back/keyMsgToReply.action",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){
	
							$('#p').progressbar('setValue', 100);
							alert("成功");
							location.href = "back/manager/weixin/keyMsgManager.jsp";
							//上传完后情况数据
							//clearForm();

						}else{
							alert("失败");
						}
					} 
			}); 

				
		    return $(this).form('enableValidation').form('validate');
			}
		});
	}
	function clearForm(){
		$('#myform').form('clear');
	}
	
    //这个方法用来当下拉框选择到视频时显示需要填写的视频信息
    function showAll(){
        var objS = document.getElementById("selectid"); //通过id获得这个元素
        var value = objS.options[objS.selectedIndex].value; //获得option中的值
		
        if(value == 1||value == 2)  //选择图片和语音
       	{
        	
        	$("#uploadFile").show();
        	$("#uploadFileRate").show();
            $("#video").hide();
            $("#video2").hide();
        }else if(value == 3){    //视频
            $("#video").show();
            $("#video2").show();
            
        }
    }

	//文本 图文切换设置
    function showAll2(){
        var objS = document.getElementById("selectid2"); //通过id获得这个元素
        var value = objS.options[objS.selectedIndex].value; //获得option中的值
		
        if(value == 0)  //文本
       	{
        	$("#textContent").show();
        	$("#newsDescription").hide();
        	$("#newsTitle").hide();
            $("#newsMessage").hide();
            $("#newsMessage2").hide();
        }else if(value == 4){    //图文
            $("#textContent").hide();
        	$("#newsDescription").show();
        	$("#newsTitle").show();
            $("#newsMessage").show();
            $("#newsMessage2").show();
            
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</script>
<title>关键字回复管理</title>
</head>
<body>

	<table id="manTypeTable"></table>

	<!-- 文本图文弹窗 -->
	<div id="dlg" class="easyui-dialog" style="text-align:center;width:440px;height:400px;padding:10px 20px" closed="true">
			<div class="easyui-panel" title="文本| 图文  关键字设置" style="width:400px">
			
		    <form id="ff" class="easyui-form" action="" method="post" data-options="novalidate:true">
		    	<table cellpadding="5">
					
					<tr>
		    			<td>关键字:</td>
		    			<td><input type="text" class="easyui-textbox" data-options="required:true" name="keywords" style="width:200px;"></input></td>
		    		</tr>
					
					<tr>
	    				<td>回复类型</td>
	    				<td>
		    				<select id="selectid2"  name="ktype" onchange="showAll2()">	
								<option value="0">文本&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<option value="4">图文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</select>
	    				</td>
	    			</tr>
					
					<tr id="textContent">
		    			<td>文本回复内容:</td>
		    			<td><input type="text" class="easyui-textbox" data-options="multiline:true,required:true" style="height:60px;width:200px;" name="Content" ></input></td>
		    		</tr>
					
		    		<tr hidden="hidden" id="newsTitle">
		    			<td>图文标题:</td>
		    			<td>
		    			<input type="text" class="easyui-textbox" data-options="required:true" name="title" style="width:200px;" ></input></td>
		    		</tr>
		    		
		    		<tr hidden="hidden" id="newsDescription">
		    			<td>图文描述:</td>
		    			<td>
		    			<input type="text" class="easyui-textbox" data-options="required:true" name="description" style="width:200px;" ></input></td>
		    		</tr>
					
					<tr hidden="hidden" id="newsMessage">
		    			<td>图文链接:</td>
		    			<td>
		    			<input type="text" class="easyui-textbox" data-options="required:true" name="url" style="width:200px;" ></input></td>
		    		</tr>
		    		
		    		<tr hidden="hidden" id="newsMessage2">
		    			<td>推文封面图片链接</td>
		    			<td>
		    			<input type="text" class="easyui-textbox" data-options="required:true" name="picUrl" style="width:200px;" ></input></td>
		    		</tr>
		    	</table>
		    </form>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm2()">提交</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm2()">清空</a>
		    </div>
		 </div>
	</div>
		

	<!-- 媒体回复弹窗 -->
	<div id="dlg2" class="easyui-dialog" style="text-align:center;width:560px;height:400px;padding:10px 20px" closed="true">
		<div class="easyui-panel" title="图片 | 语音 | 视频  关键字设置" style="width:500px">
		<div style="padding:10px 50px 20px 50px">
				<form action="" method="post" id="myform"  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data" >
					<table cellpadding="5">
						<tr>
			    			<td>关键字:</td>
			    			<td><input type="text" class="easyui-textbox" data-options="required:true" name="keywords" style="width:200px;"></input></td>
			    		</tr>

			    		<tr>
		    				<td>回复类型</td>
		    				<td>
			    				<select id="selectid"  name="ktype" onchange="showAll()">
			    					
									<option value="1">图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
									<option value="2">语音&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
									<option value="3">视频&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							
								</select>
		    				</td>
		    			</tr>
			    		
			    		<tr id="uploadFile">
			    			<td>上传文件</td>
			    			<td>
			    			<div class="uploader blue" >
			    				<input type="text" class="filename" readonly/>
								<input type="button" name="file" class="button" value="选择文件"/>
								<input type="file"  name="file" id="fileupload"  size="25"  />
							</div>
			    			</td>	 
			    		</tr>
			    		
			    		<!-- 进度条 -->
			    		<tr  id="uploadFileRate">
			    			<td>上传进度</td>
			    			<td><div id="p" class="easyui-progressbar"  style="width:200px;"></div> </td>
			    		</tr>
			    		
			    		<tr hidden="hidden" id="video">
			    			<td>视频标题:</td>
			    			<td><input type="text" class="easyui-textbox" data-options="required:true" name="title" style="width:200px;"></input></td>
			    		</tr>
			    		
			    		<tr hidden="hidden" id="video2">
			    			<td>视频描述:</td>
			    			<td>
			    			<input type="text" class="easyui-textbox" data-options="required:true" name="description" style="height:60px;width:200px;" ></input></td>
			    		</tr>
			    		
			    		
					</table>
				</form>
				<!-- 按钮 -->
				<div style="text-align:center;padding:5px">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
			    </div>
			</div>
		</div>
	</div>






</body>
</html>

<!-- 以下用于文件上传插件显示 -->
<style>.uploader{
position:relative;
display:inline-block;
overflow:hidden;
cursor:default;
padding:0;
margin:10px 0px;
-moz-box-shadow:0px 0px 5px #ddd;
-webkit-box-shadow:0px 0px 5px #ddd;
box-shadow:0px 0px 5px #ddd;

-moz-border-radius:5px;
-webkit-border-radius:5px;
border-radius:5px;
}

.filename{
float:left;
display:inline-block;
outline:0 none;
height:32px;
width:120px;
margin:0;
padding:8px 10px;
overflow:hidden;
cursor:default;
border:1px solid;
border-right:0;
font:9pt/100% Arial, Helvetica, sans-serif; color:#777;
text-shadow:1px 1px 0px #fff;
text-overflow:ellipsis;
white-space:nowrap;

-moz-border-radius:5px 0px 0px 5px;
-webkit-border-radius:5px 0px 0px 5px;
border-radius:5px 0px 0px 5px;

background:#f5f5f5;
background:-moz-linear-gradient(top, #fafafa 0%, #eee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#fafafa), color-stop(100%,#f5f5f5));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#fafafa', endColorstr='#f5f5f5',GradientType=0);
border-color:#ccc;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;

-moz-box-sizing:border-box;
-webkit-box-sizing:border-box;
box-sizing:border-box;
}

.button{
float:left;
height:32px;
display:inline-block;
outline:0 none;
padding:8px 12px;
margin:0;
cursor:pointer;
border:1px solid;
font:bold 9pt/100% Arial, Helvetica, sans-serif;

-moz-border-radius:0px 5px 5px 0px;
-webkit-border-radius:0px 5px 5px 0px;
border-radius:0px 5px 5px 0px;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;
}


.uploader input[type=file]{
position:absolute;
top:0; right:0; bottom:0;
border:0;
padding:0; margin:0;
height:30px;
cursor:pointer;
filter:alpha(opacity=0);
-moz-opacity:0;
-khtml-opacity: 0;
opacity:0;
}

input[type=button]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=button]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}
input[type=text]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=text]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}

/* White Color Scheme ------------------------ */

.white .button{
color:#555;
text-shadow:1px 1px 0px #fff;
background:#ddd;
background:-moz-linear-gradient(top, #eeeeee 0%, #dddddd 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#eeeeee), color-stop(100%,#dddddd));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#eeeeee', endColorstr='#dddddd',GradientType=0);
border-color:#ccc;
}

.white:hover .button{
background:#eee;
background:-moz-linear-gradient(top, #dddddd 0%, #eeeeee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#dddddd), color-stop(100%,#eeeeee));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dddddd', endColorstr='#eeeeee',GradientType=0);
}

/* Blue Color Scheme ------------------------ */

.blue .button{
color:#fff;
text-shadow:1px 1px 0px #09365f;
background:#064884;
background:-moz-linear-gradient(top, #3b75b4 0%, #064884 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#3b75b4), color-stop(100%,#064884));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#3b75b4', endColorstr='#064884',GradientType=0);
border-color:#09365f;
}

.blue:hover .button{
background:#3b75b4;
background:-moz-linear-gradient(top, #064884 0%, #3b75b4 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#064884), color-stop(100%,#3b75b4));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#064884', endColorstr='#3b75b4',GradientType=0);
}

/* Green Color Scheme ------------------------ */

.green .button{
color:#fff;
text-shadow:1px 1px 0px #6b7735;
background:#7d8f33;
background:-moz-linear-gradient(top, #93aa4c 0%, #7d8f33 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#93aa4c), color-stop(100%,#7d8f33));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#93aa4c', endColorstr='#7d8f33',GradientType=0);
border-color:#6b7735;
}

.green:hover .button{
background:#93aa4c;
background:-moz-linear-gradient(top, #7d8f33 0%, #93aa4c 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#7d8f33), color-stop(100%,#93aa4c));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#7d8f33', endColorstr='#93aa4c',GradientType=0);
}

/* Red Color Scheme ------------------------ */

.red .button{
color:#fff;
text-shadow:1px 1px 0px #7e0238;
background:#90013f;
background:-moz-linear-gradient(top, #b42364 0%, #90013f 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#b42364), color-stop(100%,#90013f));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#b42364', endColorstr='#90013f',GradientType=0);
border-color:#7e0238;
}

.red:hover .button{
background:#b42364;
background:-moz-linear-gradient(top, #90013f 0%, #b42364 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#90013f), color-stop(100%,#b42364));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#90013f', endColorstr='#b42364',GradientType=0);
}

/* Orange Color Scheme ------------------------ */

.orange .button{
color:#fff;
text-shadow:1px 1px 0px #c04501;
background:#d54d01;
background:-moz-linear-gradient(top, #f86c1f 0%, #d54d01 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#f86c1f), color-stop(100%,#d54d01));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#f86c1f', endColorstr='#d54d01',GradientType=0);
border-color:#c04501;
}

.orange:hover .button{
background:#f86c1f;
background:-moz-linear-gradient(top, #d54d01 0%, #f86c1f 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#d54d01), color-stop(100%,#f86c1f));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#d54d01', endColorstr='#f86c1f',GradientType=0);
}

/* Black Color Scheme ------------------------ */

.black .button{
color:#fff;
text-shadow:1px 1px 0px #111111;
background:#222222;
background:-moz-linear-gradient(top, #444444 0%, #222222 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#444444), color-stop(100%,#222222));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#444444', endColorstr='#222222',GradientType=0);
border-color:#111111;
}

.black:hover .button{
background:#444444;
background:-moz-linear-gradient(top, #222222 0%, #444444 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#222222), color-stop(100%,#444444));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#222222', endColorstr='#444444',GradientType=0);
}
</style>
<script>$(function(){
	$("input[type=file]").change(function(){$(this).parents(".uploader").find(".filename").val($(this).val());});
	$("input[type=file]").each(function(){
	if($(this).val()==""){$(this).parents(".uploader").find(".filename").val("未选择文件");}
	});
});
</script>








