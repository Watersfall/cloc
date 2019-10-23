<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<%--@elvariable id="declarations" type="java.util.ArrayList"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">
	<h1>Global Declarations</h1>
		<c:forEach var="declaration" items="${declarations}">
			<table id="nation">
				<tr><td><a href="${pageContext.request.contextPath}/nation.jsp?id=${declaration.sender.id}"><img class="medium" style="margin-right: 1em;" src="${pageContext.request.contextPath}/user/flag/<c:out value="${declaration.sender.flag}"/>" alt="flag"/><c:out value="${declaration.sender.nationName}"/></a></td></tr>
				<tr><td><p class="neutral"><c:out value="${declaration.content}"/></p></td></tr>
			</table>
			<br>
		</c:forEach>
	<c:if test="${home != null}">
		<label for="post">Post Declaration</label>
		<textarea style="width: 100%;" id="post"></textarea>
		<button onclick="postDeclaration()">Post - $${home.declarationCost}k</button>
	</c:if>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>