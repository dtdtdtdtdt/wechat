<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>


	<body>
		<header data-am-widget="header" class="am-header am-header-default header">
		  <div class="am-header-left am-header-nav">
		     <a href="javascript:history.back(-1)" class=""> 
		       <i class="am-header-icon am-icon-angle-left"></i>
		     </a>
		  </div>
		  <h1 class="am-header-title"> <a href="#title-link" class="" style="color: #333;">全部订单</a></h1>
		  <div class="am-header-right am-header-nav">
		     <a href="#right-link" class=""> </a>
		  </div>
	  </header>
		<div class="cate-search" style="position: relative; top: 0; border-bottom: 0;">
	    	<input type="text" class="cate-input" placeholder="搜索全部订单">
	    	<input type="button" class="cate-btn">
	    </div>
	    <ul class="order-style">
	    	<li class="current"><a href="allorder.html">全部</a></li>
	    	<li><a href="#">待付款</a></li>
	    	<li><a href="#">待收货</a></li>
	    	<li><a href="#">待评价</a></li>
	    	<li><a href="#">退换货</a></li>
	    </ul>
	    
	   
	     
	    
		<!-- 购物车中的商品   购物车-->
		<c:if test="${cart!=null }">
			<c:forEach items="${cart }" var="ci">
					<div class="c-comment">
							<span class="c-comment-num">订单编号:${roid } </span>
							<span class="c-comment-suc">待付款</span>
						</div>
						<div class="c-comment-list" style="border: 0;">
							<a class="o-con" href="">
				            	<div class="o-con-img"><img src="${ci.value.wxShop.cover }"></div>
				                <div class="o-con-txt">
				                	<p>${ci.value.wxShop.name }</p>
				                	<p class="price">￥${ci.value.wxShop.normprice } </p>
				                	<p>合计：<span>￥${ci.value.wxShop.normprice }</span></p>
				                </div>
				            	<div class="o-con-much"> <h4 >x${ci.value.num }</h4></div>
				            </a>
				            <div class="c-com-money">共${ci.value.num }个商品 实付金额：<span >￥${ci.value.wxShop.normprice*ci.value.num } </span></div>
						</div>
						<div class="c-com-btn">
							<a href="../shop/doOrder.action?roid=${roid }&fid=${ci.value.wxShop.fid}&num=${ci.value.num}&normprice=${ci.value.wxShop.normprice}">立即支付</a>
							<!-- 未存数据库，移除map中的一个键即可！！ -->
							<a href="../shop/cancelOrder.action?fid=${ci.value.wxShop.fid}">取消订单</a> 
						</div>
					<div class="clear"></div>
			</c:forEach>
		</c:if>
		
		
		
		<!-- 付款成功后的订单信息展示！ -->
	<c:if test="${allWxshopOrder!=null }"> 
		<c:forEach items="${allWxshopOrder }" var="a">  	
			<div class="c-comment">
				<span class="c-comment-num">订单编号: ${a.roid}</span>
				<span class="c-comment-suc">卖家已发货</span>
			</div>
			<div class="c-comment-list" style="border: 0;">
				<a class="o-con" href="#">
	            	<div class="o-con-img"><img src="${a.cover }"></div>
	                <div class="o-con-txt">
	                	<p>${a.name }</p>
	                	<p class="price">￥${a.dealprice }</p>
	                	<p>合计：<span>￥${ a.dealprice*a.num}</span></p>
	                </div>
	            	<div class="o-con-much"> <h4>x${a.num }</h4></div>
	            	
	            </a>
	            <div class="c-com-money">共${a.num }个商品 实付金额：<span>￥ ${ a.dealprice*a.num}</span></div>
			</div>
			<div class="c-com-btn">
				<c:if test="${a.confirmstatus==1 }">
					<a href="#">订单完成</a>
				</c:if>
				<c:if test="${a.confirmstatus==0}">
					<a href="../shop/confirm.action?roid=${a.roid}">确认收货</a>
				</c:if>
			</div>
			<div class="clear"></div>
		</c:forEach>
	</c:if>
		
		
		
		
	</body>
</html>





