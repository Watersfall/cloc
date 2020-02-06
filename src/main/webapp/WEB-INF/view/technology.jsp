<%@ include file="includes/defaultTop.jsp" %>
	<h1>Technology</h1>
	<div class="categories">
		<a href="${pageContext.request.contextPath}/technology/researched"><div>Researched</div></a>
		<a href="${pageContext.request.contextPath}/technology/"><div>Available</div></a>
		<a href="${pageContext.request.contextPath}/technology/tree/"><div>Tech Tree</div></a>
	</div>
	<br><br>
	<table class="standardTable">
		<%--@elvariable id="type" type="java.lang.String"--%>
		<%--@elvariable id="category" type="java.lang.String"--%>
		<%--@elvariable id="techs" type="java.util.List"--%>
		<%--@elvariable id="categories" type="java.util.List"--%>
		<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
		<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
		<%--@elvariable id="cat" type="com.watersfall.clocgame.model.technology.technologies.Category"--%>
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
			<c:when test="${type == 'tree'}">
				<!-- this is gonna be fun -->
				<div class="categories small">
					<c:forEach items="${categories}" var="cat">
						<a class="small" href="${pageContext.request.contextPath}/technology/tree/${cat.name()}/">
							<div>${cat.name}</div>
						</a>
					</c:forEach>
				</div>
				<br><br>
				<div id="tech">

				</div>
				<div class="wrapper" id="techTree">
					<%@include file="includes/techtree.jsp"%>
				</div>
			</c:when>
		</c:choose>
	</table>
<%@ include file="includes/defaultBottom.jsp" %>