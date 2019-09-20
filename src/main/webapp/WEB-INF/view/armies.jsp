<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<%--@elvariable id="armies" type="com.watersfall.clocgame.model.nation.NationArmies"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:set var="manpower" value="${home.freeManpower - (home.freeManpower % 2000)}"/>
	<h1>Armies</h1>
	<p>Available Manpower: <fmt:formatNumber value="${manpower / 1000}"/>k</p>
	<ul>
		<c:forEach var="army" items="${armies.armies}">
			<%@ include file="includes/army.jsp" %>
		</c:forEach>
	</ul>
</div>
</body>
</html>
