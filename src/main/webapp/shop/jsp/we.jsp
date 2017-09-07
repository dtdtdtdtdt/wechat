<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>



	<body>
		<div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{}' >
			  <ul class="am-slides">
			      <li><img src="images/banner3.png"> </li>
			      <li><img src="images/banner4.png"> </li>
			  </ul> 
		</div>
		<div class="am-tabs qiehuan" data-am-tabs>
			  <ul class="am-tabs-nav am-nav am-nav-tabs">
			    <li class="am-active"><a href="#tab1">店铺介绍</a></li>
			    <li><a href="#tab2">建议留言</a></li>
			  </ul>
			  <div class="am-tabs-bd">
			    <div class="am-tab-panel am-fade am-in am-active" id="tab1">
			                  妈妈厨房是一家24小时经营以港式粤菜为基础的中西融合菜，特聘香港融合菜大师主理，打造新派主题时尚餐厅。它专业的厨师团队，开发和研究新派融合菜，定期推出特色菜品，聘请专业艺术团队研发造型，让茉莉每一款菜品成为一件艺术品，让客人感受别样的饮食文化。
			       <iframe src="jsp/map.html " width="100%" height="100%"></iframe>
			    </div>
			    <div class="am-tab-panel am-fade" id="tab2">
			          <input type="text" placeholder="姓名" class="tab-input" />
			          <input type="text" placeholder="电话" class="tab-input" />
			          <textarea placeholder="建议" class="tab-input"></textarea>
			          <button type="button" class="tab-btn">提交</button>
			    </div>
	
			  </div>
		</div>
		
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
		                <span class=""><img src="images/nav03.png"/></span>
		                <span class="am-navbar-label">我们</span>
		            </a>
		          </li>
		          <li >
		            <a href="jsp/member.jsp" class="">
		                <span class=""><img src="images/nav4.png"/></span>
		                <span class="am-navbar-label">我的</span>
		            </a>
		          </li>
		   
		      </ul>
		</div>

	</body>
</html>		
		
		