<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">
	<h1>World News</h1>
	<h2 style="text-align: center">Wars</h2>
	<div class="categories">
		<ul style="display: inline-block">
			<li><a href="${pageContext.request.contextPath}/worldnews/ongoing/1">Ongoing</a></li>
			<li><a href="${pageContext.request.contextPath}/worldnews/ended/1">Ended</a></li>
		</ul>
	</div>
	<table id="nation">
		<tr>
			<th>Attacker</th>
			<th>Treaty</th>
			<th>Army</th>
			<th>Defender</th>
			<th>Treaty</th>
			<th>Army</th>
		</tr>
		<c:forEach items="${wars}" var="war">
			<tr>
				<td>${war.attacker.nationUrl}${(war.attacker == war.winner) ? ' (Winner)' : ''}</td>
				<c:if test="${war.attackerTreaty.name != null}">
					<td>${war.attackerTreaty.treatyUrl}</td>
				</c:if>
				<c:if test="${war.attackerTreaty.name == null}">
					<td></td>
				</c:if>
				<td>${war.attacker.army.size}k</td>
				<td>${war.defender.nationUrl}${(war.defender == war.winner) ? '(Winner)' : ''}</td>
				<c:if test="${war.defenderTreaty.name != null}">
					<td>${war.defenderTreaty.treatyUrl}</td>
				</c:if>
				<c:if test="${war.defenderTreaty.name == null}">
					<td>${war.defenderTreaty.treatyUrl}</td>
				</c:if>
				<td>${war.defender.army.size}k</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="includes/pagination.jsp" %>
	<table id="nation">
		<%--@elvariable id="stats" type="com.watersfall.clocgame.model.Stats"--%>
		<c:forEach items="${stats.map.entrySet()}" var="stat">
			<tr>
				<td>${stat.key}</td>
				<td>${stat.value}</td>
			</tr>
		</c:forEach>
	</table>
</div>
	<%@ include file="includes/header.jsp" %></div>
</body>
</html>