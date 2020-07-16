<%@ include file="includes/defaultTop.jsp" %>
	<h1>World News</h1>
	<h2 style="text-align: center">Wars</h2>
	<div class="categories">
		<a class="small" href="${pageContext.request.contextPath}/worldnews/ongoing/1">Ongoing</a>
		<a class="small" href="${pageContext.request.contextPath}/worldnews/ended/1">Ended</a>
	</div>
	<table class="standardTable">
		<tr>
			<th>Attacker</th>
			<th>Treaty</th>
			<th>Army</th>
			<th>Defender</th>
			<th>Treaty</th>
			<th>Army</th>
		</tr>
		<%--@elvariable id="war" type="com.watersfall.clocgame.model.war.War"--%>
		<c:forEach items="${wars}" var="war">
			<tr>
				<td>${war.attacker.nationUrl}${(war.attacker == war.winner) ? ' (Winner)' : ''}</td>
				<td><c:if test="${war.attacker.treaty != null}">
					${war.attacker.treaty.treatyUrl}
				</c:if></td>
				<td>${war.attacker.army.size}k</td>
				<td>${war.defender.nationUrl}${(war.defender == war.winner) ? '(Winner)' : ''}</td>
				<td><c:if test="${war.defender.treaty != null}">
					${war.defender.treaty.treatyUrl}
				</c:if></td>
				<td>${war.defender.army.size}k</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="includes/pagination.jsp" %>
	<br><br>
	<table class="standardTable">
		<%--@elvariable id="stats" type="com.watersfall.clocgame.model.Stats"--%>
		<c:forEach items="${stats.map.entrySet()}" var="stat">
			<tr onclick="generateGraph('${stat.key}');">
				<td><p>${stats.getStringFromKey(stat.key)}</p></td>
				<td>
					<p>${stat.value}<img class="tiny floatRight" src="${pageContext.request.contextPath}/images/ui/graph.svg" alt="graph"></p>
				</td>
			</tr>
			<tr style="padding: 0;">
				<td colspan="2" style="padding: 0;">
					<div class="toggleable" id="${stat.key}_div">
						<%@include file="includes/graph.jsp"%>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>

<%@ include file="includes/defaultBottom.jsp" %>