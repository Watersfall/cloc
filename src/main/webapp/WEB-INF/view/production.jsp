<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<script>
	document.addEventListener("DOMContentLoaded", function(){
		setFreeFactories(${home.freeFactories});
	});
</script>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<div class="container">
	<%@ include file="includes/results.jsp"%>
	<div class="main">
		<h1>Production</h1>
		<h2 id="factories">${home.freeFactories} Free Factories</h2>
		<ul style="padding-inline-start: 0" id="production">
			<%@include file="includes/allproduction.jsp" %>
		</ul>
		<ul>
			<li onclick="createProduction()" class="cityElements">
				<img src="${pageContext.request.contextPath}/images/production/new.svg" alt="new"/>
			</li>
		</ul>
	</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>