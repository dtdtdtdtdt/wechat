<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>


<style>
    .image{ 
       border-radius:240px;
    }
</style>
	<body>
		<div class="member">
			<div class="member-pic">
				<!-- 用户头像 -->
				<img class="image"  src="${user.getHeadimgurl() } " />
			</div>
			<div class="member-infor">${user.getNickname() } </div>
		</div>
		<ul class="member-nav">
			<li><a href="jsp/address.jsp"><i class="am-icon-map-marker"></i><span>收货地址</span></a></li>
			<li><a href="jsp/order.jsp"><i class="am-icon-newspaper-o"></i><span>我的订单</span></a></li>
			<li><a href=""><i class="am-icon-cart-arrow-down"></i><span>购物车</span></a></li>
			<li><a href=""><i class="am-icon-bell-o"></i><span>系统通知</span></a></li>
			<li><a href=""><i class="am-icon-credit-card"></i><span>会员卡</span></a></li>
			<li><a href="yhq.html"><i class="am-icon-cc-mastercard"></i><span>优惠券</span></a></li>
			<li><a href=""><i class="am-icon-dollar"></i><span>积分</a></li>
		</ul>
		<ul class="member-nav mt">
			<li><a href=""><i class="am-icon-phone"></i>联系我们</a></li>
		</ul>

<div class="h50"></div>
		<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default footer "  id="nav">
		      <ul class="am-navbar-nav am-cf am-avg-sm-4">
		          <li >
		            <a href="jsp/index.jsp" class="">
		                <span class=""><img src="images/nav0.png"/></span>
		                <span class="am-navbar-label">点餐</span>
		            </a>
		          </li>
		          <li>
		            <a href="jsp/speak.jsp" class="">
		                <span class=""><img src="images/nav2.png"/></span>
		                <span class="am-navbar-label">客说</span>
		            </a>
		          </li>
		          <li>
		            <a href="jsp/we.jsp" class="">
		                <span class=""><img src="images/nav3.png"/></span>
		                <span class="am-navbar-label">我们</span>
		            </a>
		          </li>
		          <li >
		            <a href="jsp/member.jsp" class="">
		                <span class=""><img src="images/nav04.png"/></span>
		                <span class="am-navbar-label">我的</span>
		            </a>
		          </li>
		   
		      </ul>
		</div>

	</body>
</html>		
		
		