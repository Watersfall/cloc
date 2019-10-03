<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="nations" type="java.util.Collection"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<h1 title="Title" id="top">Test</h1>
	<hr>
	<table id="policy">
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
						<img class="indexflag" src="https://imgur.com/<c:out value="${nation.cosmetic.flag}"/>" alt="flag">
					</a>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/nation.jsp?id=${nation.id}">
						${nation.cosmetic.nationName}
					</a>
				</td>
				<td><c:out value="${nation.cosmetic.username}"/></td>
				<td>${nation.foreign.region.name}</td>
				<td>${nation.economy.gdp}</td>
				<td>${nation.treaty.name}</td>
			</tr>
		</c:forEach>
	</table>
</div>
</div>
</body>
</html>