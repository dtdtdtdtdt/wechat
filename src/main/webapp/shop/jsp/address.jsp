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
		  <h1 class="am-header-title"> <a href="#title-link" class="" style="color: #333;">收货地址</a></h1>
		  <div class="am-header-right am-header-nav">
		     <a href="#right-link" class=""> </a>
		  </div>
	  </header>

		  <ul class="address-list">
		    	<li class="curr">
		    		<p>收货人: 文先生&nbsp;&nbsp;153****1010</p>
		    		<p class="order-add1">收货地址:上海市浦东新区</p>
		    	    <hr>
		    	    <div class="address-cz">
		    	    	<label class="am-radio am-warning">
	                       <input type="radio" name="radio3" value="" data-am-ucheck="" checked="" class="am-ucheck-radio"><span class="am-ucheck-icons"><i class="am-icon-unchecked"></i><i class="am-icon-checked"></i></span> 设为默认
	                    </label>
	                    <a href="#"><img src="images/bj.png" style="width: 18px;">&nbsp;编辑</a>
	                    <a href="#">删除</a>
	                    <a href="../shop/confirmAddresss.action">确认</a>
		    	    </div>
		    	</li>
		    </ul>

	    
	</body>
</html>










