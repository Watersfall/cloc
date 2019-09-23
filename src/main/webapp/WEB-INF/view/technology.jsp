<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<table id="nation">
		<tr>
			<th>Technology</th>
			<th>Description</th>
			<th>Cost</th>
			<th>Research</th>
		</tr>
		<%--@elvariable id="techs" type="java.util.List"--%>
		<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
		<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
		<c:forEach var="tech" items="${techs}">
			<c:if test="${tech.technology.isAvailable(home)}">
				<tr>
					<td>${tech.technology.name}</td>
					<td>${tech.technology.desc}</td>
					<td>
						<c:forEach items="${tech.technology.costs}" var="cost" varStatus="index">
							<c:choose>
								<c:when test="${index.first && index.last}">
									${cost.value} ${cost.key}
								</c:when>
								<c:when test="${index.last && !index.first}">
									and ${cost.value} ${cost.key}
								</c:when>
								<c:otherwise>
									${cost.value} ${cost.key},
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</td>
					<td>
						<button onclick="research('${tech.technology.tableName}')" class="button">Research</button>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>
</body>
</html>