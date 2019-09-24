<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<table id="nation">
		<tr>
			<th style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp?type=researched"><div>Researched</div></a></th>
			<th style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp"><div>Available</div></a></th>
			<th style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp?type=all"><div>All</div></a></th>
		</tr>
	</table>
	<br><br>
	<table id="nation">
		<%--@elvariable id="type" type="java.lang.String"--%>
		<%--@elvariable id="techs" type="java.util.List"--%>
		<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
		<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
		<c:choose>
			<c:when test="${type == 'standard'}">
				<tr>
					<th>Technology</th>
					<th>Description</th>
					<th>Cost</th>
					<th>Research</th>
				</tr>
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
								<button onclick="research('${tech.name()}')" class="button">Research</button>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>
			<c:when test="${type == 'all'}">
				<tr>
					<th>Technology</th>
					<th>Description</th>
					<th>Prerequisites</th>
				</tr>
				<c:forEach var="tech" items="${techs}">
					<tr>
						<td>${tech.technology.name}</td>
						<td>${tech.technology.desc}</td>
						<td>
							<c:forEach var="pre" items="${tech.technology.prerequisites}" varStatus="index">
								<c:choose>
									<c:when test="${index.first && index.last}">
										${pre.technology.name}
									</c:when>
									<c:when test="${index.last && !index.first}">
										and ${pre.technology.name}
									</c:when>
									<c:otherwise>
										${pre.technology.name},
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:when test="${type == 'researched'}">
				<tr>
					<th>Technology</th>
					<th>Description</th>
				</tr>
				<c:forEach var="tech" items="${home.tech.researchedTechs}">
					<tr>
						<td>${tech.technology.name}</td>
						<td>${tech.technology.desc}</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
</div>
</body>
</html>