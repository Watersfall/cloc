<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<script>
	document.addEventListener("DOMContentLoaded", function(){
		setFreeFactories(${home.freeFactories});
	});
	</script>
	<h1>Production</h1>
	<h2 id="factories">${home.freeFactories} Free Factories</h2>
	<div id="production">
		<%@include file="includes/allproduction.jsp" %>
	</div>
	<div>
		<div onclick="createProduction()" class="element">
			<img src="${pageContext.request.contextPath}/images/production/new.svg" alt="new"/>
		</div>
	</div>
<%@ include file="includes/defaultBottom.jsp" %>