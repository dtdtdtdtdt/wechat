<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<link rel="stylesheet" type="text/css" href="back/css/source.css">
<script type="text/javascript" src="back/js/jquery.form.js"></script>

<script type="text/javascript">
	$(function(){
		$('#manTypeTable').edatagrid({
			url:'back/findPicture.action',   //查询时加载的URL
			pagination:true,     //开启分页  
			pageSize:10,         //默认分页的条数
			pageNumber:1,        //第几页显示（默认第一页，可以省略） 
			fitColumns:true,     //自适应列
			fit:true,   	     //自动补全
			title:"查看所有图片素材",
			idField:"pid",		  //标识，会记录我们选中的一行的ID，不一定是ID，通常是主键
			rownumbers:"true",	  //显示行号
			nowrap:"true",		  //不换行显示
			sortName:"pid",		  //排序的列 这个参数会传到后台的servlet上，所以要有后台对应的接收
			sortOrder:"desc",     //排序方式
			singleSelect:true,
			//以上的四种增删改查操作，只要失败，都会回调这个onError
			onError:function(a,b){
				$.messager.alert("错误","操作失败");
			},
			columns:[[{
				field:'pid',
				title:'图片编号',
				width:80,
				align:'center',
				hidden:'true' 
			},{
				field:'pname',
				title:'图片名称',
				width:80,
				align:'center'
			},{
				field:'thumb_media_id',
				title:'封面图片素材id',
				width:220,
				align:'center'
			},{
				field:'webUrl',
				title:'图片',
				width:80,
				align:'center',
				/*将链接转成图片显示*/
				formatter:function(value,row){
				     var str = "";
				     if(value!="" || value!=null){
				     str = "<img style=\"height: 50px;width: 50px;\" src=\""+value+"\"/>";
				     return str;
				     }
				}
			},{
				field:'pdate',
				title:'保存时间',
				width:80,
				align:'center',
				/*转换日期的格式*/
				formatter:function(value,row){
					if(value!="" || value!=null){
						var d = new Date(value);
						return time=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds(); 
					}
				}
			 },{
				 field:'action',
				 title:'操作', 
				 width:60,
				 align:'center',
				 formatter:function(value,row,index){
				 	if (row){
	 					var c ='<a href="javascript:void(0)" onclick="delRow('+row.pid+')">删除</a>'; 		
						return c;
					}
				 }
			}]]
		});
	});
	
	function delRow(value){
		alert("您要删除此图片信息吗？");
		$.ajax({
			type:"POST",
			data:"pid="+value,
			url:"back/delPicture.action",
			dataType:"JSON",
			success:function(data){
				if(data.code ==1){
					location.href ="back/manager/source/managePicture.jsp"
			}else{
				alert("删除失败，"+data.msg);
				}
			}
		})
	};	
	
	function showUploadImg(obj,picid){
		//判断浏览器是否支持FileReader接口
		if(typeof FileReader == 'undefined'){
			$.messager.alert('Warning',' 当前的浏览器不支持FileReader接口');
			//使选择控件不可操作
			obj.setAttribute("disabled","disabled");
			return ;
		}
		var file =obj.files[0];
		var reader =new FileReader();
		reader.onload =function(e){
			$("#"+picid).attr("src",e.target.result);						
		}
		reader.readAsDataURL(file);		
		add1.style.display="none";	
		add2.style.display="block";
	}
	
	function submitForm(){
		$('#myform').form('submit',{
			onSubmit:function(){		
				$("#myform").ajaxSubmit({
					type:"POST",
					url:"back/addPicture.action",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){					
							location.href ="back/manager/source/managePicture.jsp"
						}else{
							alert("图片上传失败"+data.msg);
						}
					} 
				});
			}
		});
	}
</script>
<title>图片库管理</title>
</head>
<div id="tb">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#win').window('open');">添加图片</a>
	<div id="win" class="easyui-window" title="添加图片" closed="true" style="width:500px;height:380px;">
		<center>
			<p style="color:gray;font-size:24px;">添加一个图片（图片大小不得超过64KB）</p>
		</cneter>
		
		<div id="add1" onclick="fileupload.click()" ><br/><br/><br/><br/><br/><br/>					
			<center><p style="color:gray;font-size:24px;" >点&nbsp;击&nbsp;添&nbsp;加</p></cneter><br/>												
		</div>
			
		 <div id="add2"  style="display:none;">	
			<center>
				<img id="showpic" style="width:250px;height:200px;" onclick="fileupload.click()"/><br/>
				<span style="color:gray;font-size:12px;line-height:20px">点击图片进行替换</span>		
			</center>				    								
		</div>
		
		<form action="" method="post" id="myform" enctype="multipart/form-data" >
			<input type="file"  name="file" id="fileupload" onchange="showUploadImg(this,'showpic')" accept="image/*" style="display:none;" /><br/>		
		</form>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	</div>
</div>

<body>
	<table id="manTypeTable"></table>	
</body>



</html>