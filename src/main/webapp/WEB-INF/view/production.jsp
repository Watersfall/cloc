<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<style>
		.name{
			position: absolute;
			top: 0.25em;
			left: 0.25em;
		}
	</style>
	<div id="production">
		<%@include file="includes/allproduction.jsp" %>
	</div>
	<div>
		<div onclick="createProduction()" class="element">
			<img src="${pageContext.request.contextPath}/images/production/new.svg" alt="new"/>
		</div>
	</div>
<%@ include file="includes/defaultBottom.jsp" %>