<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="declarations" type="java.util.ArrayList"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<h1>Global Declarations</h1>
		<c:forEach var="declaration" items="${declarations}">
			<table id="policy">
				<tr><td><a href="${pageContext.request.contextPath}/nation.jsp?id=${declaration.sender.id}"><img class="imgSmall" src="https://i.imgur.com/${declaration.sender.flag}" alt="flag"/>${declaration.sender.nationName}</a></td></tr>
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
</div>
</body>
</html>