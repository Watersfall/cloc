<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">
	<h1>Technology</h1>
	<div class="categories">
		<ul>
			<li style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp?type=researched"><div>Researched</div></a></li>
			<li style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp"><div>Available</div></a></li>
			<li style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp?type=tree"><div>Tech Tree</div></a></li>
		</ul>
	</div>
	<table id="nation">
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
				<div class="categories">
					<ul>
						<c:forEach items="${categories}" var="cat">
							<li>
								<a href="${pageContext.request.contextPath}/technology.jsp?type=tree&category=${cat.name()}">
									<div>${cat.name}</div>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<style>
					.wrapper{
						display: grid;
						grid-template-columns: repeat(3, 1fr);
						grid-auto-rows: minmax(100px, auto);
						grid-gap: 0;
					}

					.vertical{
						position:absolute;
						bottom: 50%;
						left: calc(50% - 0.5em);
						width: 1em;
						height: 100%;
						border-left: gray solid 1em;
					}

					.horizontalLeft{
						position:absolute;
						top: calc(50% - 0.5em);
						left: -50%;
						width: 100%;
						height: 1em;
						border-top: gray solid 1em;
					}

					.horizontalRight{
						position:absolute;
						top: calc(50% - 0.5em);
						right: -50%;
						width: 100%;
						height: 1em;
						border-top: gray solid 1em;
					}

					.cornerBottomRight{
						position:absolute;
						right: calc(-50% - 0.5em);
						width: 100%;
						top: -50%;
						height: 100%;
						border-right: gray solid 1em;
						border-bottom: gray solid 1em;
						border-left: transparent 0;
						border-top: transparent 0;
					}

					.cornerBottomLeft{
						position:absolute;
						left: calc(-50% - 0.5em);
						width: 100%;
						top: -50%;
						height: 100%;
						border-style: solid;
						border-right: transparent 0;
						border-bottom: gray solid 1em;
						border-left: gray solid 1em;
						border-top: transparent 0;
					}
					.tech {
						position: relative;
						text-align: center;
						display: inline-block;
						border: #424242 0.25em solid;
						padding: 1em;
						border-radius: 0.1em;
						margin: 1em;
						z-index: 1;
					}

					.researched {
						background-color: rgb(95,183,97);
					}

					.researched p{
						color: black;
					}

					.available {
						background-color: rgb(250,189,41);
						cursor: pointer;
					}

					.available p {
						-webkit-user-select: none;
						-moz-user-select: none;
						-ms-user-select: none;
						user-select: none;
						color: black;
					}

					.unavailable {
						background-color: #6d6d6d;
					}

					.unavailable p{
						color: black;
					}
				</style>
				<script>
					function tech(tech, next)
					{
						document.getElementById('resultsContainer').style.visibility = "visible";
						document.getElementById("result").innerHTML = "<p>Loading...</p>";
						let attempt = document.getElementById(tech);
						if(attempt == null)
						{
							document.getElementById("result").innerHTML = "<p>Don't do that!</p>";
						}
						else if(attempt.classList.contains("unavailable"))
						{
							document.getElementById("result").innerHTML = "<p>You can not research this technology!</p>";
						}
						else if(attempt.classList.contains("researched"))
						{
							document.getElementById("result").innerHTML = "<p>You already have this technology!</p>";
						}
						else
						{
							research(tech);
						}
					}
				</script>
				<div class="wrapper" id="techTree">
					<%@include file="includes/techtree.jsp"%>
				</div>
			</c:when>
		</c:choose>
	</table>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>