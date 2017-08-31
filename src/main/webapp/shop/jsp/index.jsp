<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>



<body>
	<div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{}' >
		  <ul class="am-slides">
		      <li><img src="images/banner.jpg"> </li>
		      <li><img src="images/banner1.jpg"> </li>
		  </ul> 
	</div>
	<!-- 去掉这块
		<a href="jsp/search.jsp" class="search">
			开启你的美食之旅...
		</a>
	 -->
	<ul class="nav">
		<li>
			<a href="jsp/search.jsp">
				<img src="images/icon.jpg" />
				<p>最新推荐</p>
			</a>
		</li>
		<li>
			<a href="jsp/search.jsp">
				<img src="images/icon1.jpg" />
				<p>热门菜谱</p>
			</a>
		</li>
		<li>
			<a href="jsp/search.jsp">
				<img src="images/icon2.jpg" />
				<p>人气菜肴</p>
			</a>
		</li>
		<li>
			<a href="jsp/yhq.jsp">
				<img src="images/icon3.jpg" />
				<p>优惠券</p>
			</a>
		</li>
	</ul>
	<div data-am-widget="titlebar" class="am-titlebar am-titlebar-default title" >
	    <h2 class="am-titlebar-title ">   积分菜品 </h2>
	    <nav class="am-titlebar-nav">
	        <a href="#more" class="">more &raquo;</a>
	    </nav>
	</div>
    <ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-3 am-avg-lg-4 am-gallery-default product">
	      
	      <c:forEach items="${wxshop }" var="w">
		      	<li>
		        <div class="am-gallery-item">
		            <a href="../shop/detail.action?fid=${w.fid }" class="">
		              <img src="${w.cover }"  alt=""/>
		              <h3 class="am-gallery-title">${w.name }</h3>
		              <div class="am-gallery-desc">
		              	<em>${w.normprice }</em><i class="am-icon-cart-plus"></i>
		              </div>
		            </a>
		        </div>
		      </li>
	      </c:forEach>
	 </ul>
		 
		 
<%@ include file="buttom.jsp" %>		 
		 
		 