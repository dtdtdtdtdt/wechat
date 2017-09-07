<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/rotaryDraw.js"></script>
<title>每日抽奖赚积分</title>
<style>
*{padding:0;margin:0;}
body{
	background:#f03200;
}
.box{
	position:relative;
	max-width:750px;
	margin:0 auto;
}
.box .img01{
	width:100%;
}
.box .drawBtn{
	position:absolute;
	width:10rem;
	height:auto;
	left:50%;
	margin-left:-5rem;
}
.box1{
	position:relative;
	max-width:750px;
	margin:0 auto;
}
.box1 .img01{
	width:100%;
	margin-top:20%;
}
.box1 .drawBtn2{
	position:absolute;
	width:8rem;
	height:auto;
	left:50%;
	margin-left:-4rem;
	margin-top:20%;
}
</style>
</head>
<body>

<div class="box1">
	<img src="images/img_04.png" class="img01" alt="">
	<img src="images/img_03.png" class="drawBtn2">
</div>
<script>

$(".box1 .img01").load(function() {
	var obj = $(".drawBtn2");
	var hei = $(this).height();
	var hei2 = obj.height();
	obj.css("top",(hei - hei2)/2);
});
</script>
<script>
//share份额[数字没有默认],
//speed速度[单位s,最小0.1s],
//velocityCurve速度曲线[linear匀速，ease慢快慢，ease-in慢慢开始，ease-out慢慢结束，ease-in-out慢快慢等，用的是css3的速度曲线],可以不写，ease默认值；
//callback回调函数
//weeks几周[默认2周，可以不写]

//几份和回调函数这两个参数是必填


function callbackB(ind)
{
	var openid=<%=session.getAttribute("openId")%>;
	var wcount="";
	if(ind==1){
		wcount=5;
		alert("恭喜获得5积分！");
	}else if(ind==2){
		wcount=1;
		alert("恭喜获得1积分！");
	}else if(ind==3){
		alert("不服再来！");
	}else if(ind==4){
		wcount=1;
		alert("恭喜获得1积分！");
	}else if(ind==5){
		wcount=3;
		alert("恭喜获得3积分！");
	}else if(ind==6){
		alert("运气先攒着！");
	}else if(ind==7){
		wcount=1;
		alert("恭喜获得1积分！");
	}else if(ind==8){
		wcount=3;
		alert("恭喜获得3积分！");
	}else if(ind==9){
		alert("继续加油！");
	}else if(ind==10){
		wcount=10;
		alert("恭喜获得10积分！");
	}else if(ind==11){
		wcount=1;
		alert("恭喜获得1积分！");
	}else if(ind==12){
		alert("再接再厉！");
	}
	alert(openid+"==="+wcount);
	$.ajax({
		type : "POST",
		data:"openid="+openid+"&wcount="+wcount,
		url : "back/addWard.action",
		dataType : "JSON",
		success : function(data) {
			if (data.code == 0) {
				alert("记录抽奖信息失败！" + data.msg);
			}
		}
	});
};

var newdraw2 =new turntableDraw('.drawBtn2',{
	share:12,
	speed:"3s",
	velocityCurve:"ease",
	weeks:6,
	callback:function(num)
	{
		callbackB(num);
	},
});

$(".drawBtn2").click(function(event) {
	//ajax
	newdraw2.goto(parseInt(Math.random()*12)+1);
});
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p>版权所有&copy;丁婷、刘翔、朱鹏</p>
</div>
</body>
</html>
