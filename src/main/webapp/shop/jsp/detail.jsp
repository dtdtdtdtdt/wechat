<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>




	<body>
		<div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{}' >
			  <ul class="am-slides">
			      <li><img src="../../${ws.detaila }"> </li>
			      <li><img src="../../${ws.detailb }"> </li>
			  </ul> 
		</div>
		<div class="detail">
			<h2>${ws.name }</h2>
			<div class="price">
				<b>${ws.normprice }</b><span>( 积分可抵扣5元 )</span>
			</div>
			<div class="kucun">
				<p>库存:${ws.stock }</p>
				<p>运费:免运费</p>
			</div>
		</div>
		<!--  
		<div class="comment">
			<h2>宝贝评价（0）</h2>
			<ul>
				<li><a hhref="">有图（0）</a></li>
				<li><a hhref="">好评（0）</a></li>
				<li><a hhref="">中评（0）</a></li>
				<li><a hhref="">差评（0）</a></li>
			</ul>
		</div>
		-->
        <div class="detail-con">
        	<center>
				<p>${ws.detail }</p>
			</center>
        	
        </div>
		<div class="h50"></div>
		<ul class="fixed-btn">
			<li ><a href="../shop/toCart.action?fid=${ws.fid }" class="current">立即购买</a></li>  <!-- 立即购买也是加购物车在买呀！ -->
			<li><a href="../shop/toCart.action?fid=${ws.fid }">加入购物车</a></li>
		</ul>
		
	</body>
</html>
