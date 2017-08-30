<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<html>
<head>
<title>欢乐微信墙</title>
<meta charset="UTF-8">
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<link href="js/swiper.min.css" rel="stylesheet" type="text/css"/>
<script src="js/swiper.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>
<script type="text/javascript" src="js/jquery.barrager.js"></script>
<style>
	.dm .d_screen .d_mask{width:255;height:200px;position:absolute;top:0;left:0;opacity:0.5;
						  filter:alpha(opacity=50) ;z-index:1;}
	.dm .d_screen .d_show{position: relative;z-index:2;}
	.dm .d_screen .d_show div{color:#fff; background:rgba(1,1,1,.4);border-radius:5px; margin:5px; }

	.d_show div{
		width:1080px;font-size:60px;color:#fff; line-height:100px;
	}
	.d_show img{width:60px; height:60px;border-radius:50%; padding:8px;}


	.NYwishes{width:1368px; height:640px; position:fixed; bottom:0; right:0; background:rgba(0,0,0,.6);}
	.NYwishes .swiper-container{ height:600px;}
	#conts{margin:0 auto ;padding-left:2%;padding-right:2%;height:500px;}

</style>
</head>
<body style="padding:0px;margin:0px;" > 
<div class="NYwishes">
<br/>
<div class="swiper-container">
	<div  id="conts"> 
		<div class="dm">
			<!--d_screen start-->
			<div class="d_screen">
				<div class="d_mask"></div>
				<div class="d_show">
					<div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
				</div>
			</div>
		</div>
	</div> 
</div> 
</div>		
<script>



var goEasy = new GoEasy({
    appkey: 'BC-e44baa9b32d64f40abf5cddeffa3aa54'
});
//GoEasy-OTP可以对appkey进行有效保护，详情请参考：GoEasy-Reference


$(function(){
	var date = [{'id':1,'val':'欢迎来到欢乐微信墙~'},{'id':2,'val':'准备好了没？'},{'id':3,'val':'活动马上开始！'},{'id':4,'val':'惊叫声在哪里？！'},{'id':5,'val':'请开始你的表演'}];
	var t = 1;
	var str;
	goEasy.subscribe({
	    channel: 'BS-8ecca8bee3204a06a16f5d584262bee0',
	    onMessage: function(message){
	    	
	    	
	    	if(date.length==10){
		    	// 移除数组第一个元素
				date.shift();
	    	}

	    	date.push({'id':date.length,'val': message.content });
			
	    }
		
		
	
	});

	var i=0;
	setInterval(function(){
		if($(".d_show").height()<100){
			if(i<=date.length){
				var str = date[i].val.split('#$'); 
				input(str)
				i++;
			}else{
				i=0	
				var str = date[i].val.split('#$');
				input(str)
				i++;
			}
		}else{
			init_screen();
			$($(".d_show").children("div").get(0)).remove();
			if(i<date.length){
				var str = date[i].val.split('#$'); 
				input(str)
				i++;
			}else{
				i=0	
				var str = date[i].val.split('#$'); 
				input(str)
				i++;
			}
		}
	},1000);
	

})
function init_screen(){
	$(".d_show").find("div").show().each(function () {
		setInterval(function(){
		 $($(".d_show").children("div").get(0)).toggle(1000);
		},4000);
	});
}	
function input(val){
	if(typeof(val[1])=="undefined"){
		val[1] = 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLAtPuHEAeXfN6Via2o37RWBTM9l2icia7xfzzDc933ehq9VNcUySialccicRLI7NybshogbQUiboMosmtibA/0';
	}
	var div=$("<div><img src="+val[1]+"/>"+val[0]+"</div>");
	$(".d_show").append(div.fadeIn(1000));
}
</script>


</body>
</html>


