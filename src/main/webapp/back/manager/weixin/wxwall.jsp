<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="../head.jsp" %>


<%
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<!-- 微信	墙样式css -->
<link rel="stylesheet" type="text/css" href="css/wxwall.css">

<script type="text/javascript" src="js/jquery-1.9.1.js"></script>



<title>微信墙</title>

</head>

<script>
	

	var path = '<%=basePath%>';
	var uid= 1  ;
	if(uid==-1){
		location.href="<%=basePath2%>";
	}
	var from=uid;
	var fromName="马小跳";
	var to=uid==1?2:1;
	
	var websocket;
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://" + path + "/ws?uid="+uid);
	} else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://" + path + "/ws"+uid);
	} else {
		websocket = new SockJS("http://" + path + "/ws/sockjs"+uid);
	}
	websocket.onopen = function(event) {
		console.log("WebSocket:已连接");
		console.log(event);
	};
	websocket.onmessage = function(event) {
		var data=JSON.parse(event.data);
		console.log("WebSocket:收到一条消息",data);
		var textCss=data.from==-1?"sfmsg_text":"fmsg_text";
		$("#content").append("<div class='fmsg'><label class='name'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='"+textCss+"'>"+data.text+"</div></div>");
		scrollToBottom();
	};
	websocket.onerror = function(event) {
		console.log("WebSocket:发生错误 ");
		console.log(event);
	};
	websocket.onclose = function(event) {
		console.log("WebSocket:已关闭");
		console.log(event);
	}
	function sendMsg(){
		
		var v=$("#msg").val();
		if(v==""){
			return;
		}else{
			var data={};
			data["from"]=from;
			data["fromName"]=fromName;
			data["to"]=to;
			data["text"]=v;
			websocket.send(JSON.stringify(data));
			$("#content").append("<div class='tmsg'><label class='name'>我&nbsp;"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</label><div class='tmsg_text'>"+data.text+"</div></div>");
			scrollToBottom();
			$("#msg").val("");
		}
	}
		
	function scrollToBottom(){
		var div = document.getElementById('content');
		div.scrollTop = div.scrollHeight;
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	function send(event){
		var code;
		 if(window.event){
			 code = window.event.keyCode; // IE
		 }else{
			 code = event.which; // Firefox
		 }
		if(code==13){ 
			sendMsg();            
		}
	}
	




			

</script>






<body>

	<div id="content"></div>		
	<input type="text" placeholder="请输入要发送的信息" id="msg" class="msg" onkeydown="send(event)">
	<input type="button" value="发送" class="send" onclick="sendMsg()" >


	
</body>
</html>




