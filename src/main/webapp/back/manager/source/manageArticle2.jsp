<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<script type="text/javascript">
$(function(){
   $.ajax({
		type:"POST",
		url:"back/getSourceList2.action",
		data:"",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				alert("发送成功！");
				//location.href="back/manager/source/manageArticle.jsp";
			}else{
				alert("发送失败！"+data.msg);
			}
		}
	})   
});
</script>
<title>管理素材消息</title>
</head>
<body>
	<div>
		<div id="skin2"><br/>
			<div style="margin-left:42px">
				<c:forEach items="${Sourcelist }" var="Sourcelist">
					<input type="text" size="30"  style="border-style:none;line-height:5px;" readonly="readonly" id="viewtitle" value="${Sourcelist.title }" /><br/>
					<input type="text" size="30"  style="border-style:none;line-height:5px;" readonly="readonly" id="viewtitle" value="${Sourcelist.author }" /><br/>
					<div id="skinpic2" >			
<%-- 						<img id="viewpic" style="width:250px;height:200px" src=${Sourcelist.content } /><br/>
 --%>						<p>${Sourcelist.content }</p>
					</div>
					<input type="text" size="30"  style="border-style:none;line-height:5px" readonly="readonly" id="viewdigest" value="${Sourcelist.digest }" /><br/><br/>						
				</c:forEach>
			</div>  				   				
		</div>	
	</div>		
</body>
</html>
