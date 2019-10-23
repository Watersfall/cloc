<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<%--@elvariable id="nations" type="java.util.Collection"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">

	<h1 title="Title" id="top">The World's Finest</h1>
	<hr>
	<table id="nation">
		<tr>
			<th style="width: 10em">Nation</th>
			<th></th>
			<th>Leader</th>
			<th>Region</th>
			<th>GDP</th>
			<th>Treaty</th>
		</tr>
		<c:forEach items="${nations}" var="nation">
			<tr>
				<td style="width: 10em">
					<a href="${pageContext.request.contextPath}/nation.jsp?id=${nation.id}">
						<img class="large" src="${pageContext.request.contextPath}/user/flag/<c:out value="${nation.cosmetic.flag}"/>" alt="flag">
					</a>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/nation.jsp?id=${nation.id}">
							${nation.cosmetic.nationName}
					</a>
				</td>
				<td><c:out value="${nation.cosmetic.username}"/></td>
				<td><c:out value="${nation.foreign.region.name}"/></td>
				<td><fmt:formatNumber value="${nation.economy.gdp}"/></td>
				<c:if test="${nation.treaty != null}">
					<td><a href="${pageContext.request.contextPath}/treaty.jsp?id=${nation.treaty.id}"><c:out value="${nation.treaty.name}"/></a></td>
				</c:if>
				<c:if test="${nation.treaty == null}">
					<td>None</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>