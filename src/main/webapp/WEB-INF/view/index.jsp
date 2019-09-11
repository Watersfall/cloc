<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<%--@elvariable id="nations" type="java.util.Collection"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="main">
	<h1 title="Title" id="top">Test</h1>
	<hr>
	<table id="nation">
		<tr>
			<th>Flag</th>
			<th>Nation Name</th>
			<th>Leader Name</th>
		</tr>
		<c:forEach items="${nations}" var="nation">
			<tr>
				<td><img class="indexflag" src="https://imgur.com/<c:out value="${nation.cosmetic.flag}"/>" alt="flag"></td>
				<td><a href="${pageContext.request.contextPath}/nation.jsp?id=<c:out value="${nation.id}"/>"><c:out value="${nation.cosmetic.nationName}"/></a></td>
				<td><c:out value="${nation.cosmetic.username}"/></td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>