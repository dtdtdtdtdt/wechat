<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp"%>


<link href="css/index.css" rel="stylesheet" />
<link href="css/labelauty.css" rel="stylesheet" />
<link href="css/demo.css" rel="stylesheet" />
<link href="css/ladda.min.css" rel="stylesheet" />
<script src="js/ga.js"></script>
<script src="js/spin.min.js"></script>
<script src="js/ladda.min.js"></script>

<script src="js/labelauty.js"></script>

<script type="text/javascript">

		//数据库中所有的表获取下拉框中的值
		$.ajax({
			   url: "back/databaseList.action",
			   type: "POST",
			   dataType: "JSON",
			   success: function (data) {
					$("#tt").html("");
					$(data.obj).each(function(index,item){
						var str ='';
						str+='<option value='+ item +'>'+ item+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>';
						$("#tt").html($("#tt").html()+str);
					});	
			   }
		});


	// 保存功能
	function Save() {
		
		Ladda.bind( '.button-demo button', {
			callback: function( instance ) {
				var val = $('input:radio[name="rd"]:checked').val();// 选择的是结构还是结构与数据
				var table = $('#tt').val();  //表名
				var date1 = new Date();
				$.ajax({
					url : "back/databaseBackUp.action",
					type : "POST",
					data : "&types=" + val + "&table="+ table,
					dataType : "JSON",
					success : function(d) {
						if (d.code == 1) {
							var date2 = new Date();
							var date3=date2.getTime()-date1.getTime();  //时间差的毫秒数
							//转圈圈...
							var interval = setInterval( function() {
								instance.stop();
								clearInterval( interval );
								alert("备份成功！查看备份记录请先刷新！");
								location.href = "back/manager/weixin/dateBackUp.jsp";
							}, date3 );
							
							
						} else {
							alert(d.msg);
						}
					}
				});
				

			}
		} );
		
		
		

	}
</script>



<script>
    $(document).ready(function () {
        //参数{input类名，选择类型(单选or多选)}
        $(".rdolist").labelauty("rdolist", "rdo");

    });
</script>

<body >
	<div id="dd">
		<br/>
		
	<div style="margin: 30px 80px">
		Tip:数据库文件备份可手动 <br/>
		同时系统会在每晚凌晨2:00自动备份,文件保留30天  <br/><br/>
		<label>
			请选择要备份的表:<select id="tt" name="tt" class="table"></select>
		</label>
		<div class="rdo">
	        <input type="radio" name="rd" class="rdolist" checked="checked" value="结构"/>
	        <label class="rdobox">
	            <span class="check-image"></span>
	            <span class="radiobox-content" >结构</span> 
	        </label>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" name="rd" class="rdolist" checked="checked" value="结构和数据" />
	        <label class="rdobox">
	            <span class="check-image"></span>
	            <span class="radiobox-content">结构和数据</span>
	        </label>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" name="rd" class="rdolist" checked="checked" value="备份所有表结构和数据" />
	        <label class="rdobox">
	            <span class="check-image"></span>
	            <span class="radiobox-content">备份所有表结构和数据</span>
	        </label>
	        
	    </div>
	</div>
			
		
		<section class="button-demo">
			<button class="ladda-button" data-color="blue" 
				data-style="contract-overlay" style="z-index: 10;">
				<span class="ladda-label"  onclick="Save()" >备份</span>
				<span class="ladda-spinner"></span>
			</button>
		</section>
		
	

	
	


	</div>
</body>
</html>