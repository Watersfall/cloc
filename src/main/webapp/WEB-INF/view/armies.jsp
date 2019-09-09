<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="${pageContext.request.contextPath}/js/policies.js"></script>
<%--@elvariable id="armies" type="com.watersfall.clocgame.model.nation.NationArmies"--%>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<ul>
		<c:forEach var="army" items="${armies.armies}">
			<%@ include file="includes/army.jsp" %>
		</c:forEach>
	</ul>
</div>
</body>
</html>
