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
			<th style="width: 33%"><a href="${pageContext.request.contextPath}/technology.jsp?type=tree"><div>Tech Tree</div></a></th>
		</tr>
	</table>
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
				<style>
					.categories{
						width: 100%;
						margin-left: auto;
						margin-right: auto;
						display: block;
					}

					.categories ul {
						list-style-type: none;
						padding: 0;
						overflow: hidden;
						background-color: #333333;
						margin: 0 0 1em auto;
					}

					.categories li {
						float: left;
					}

					.categories li a {
						display: block;
						color: white;
						text-align: center;
						padding: 16px;
						text-decoration: none;
					}

					.categories li a:hover {
						background-color: #111111;
					}
				</style>
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
				</style>
				<div class="wrapper">
					<style>
						.vertical{
							position:absolute;
							bottom: 50%;
							left: calc(50% - 0.5em);
							width: 1em;
							height: 100%;
							border-left: gray solid 1em;
							z-index: -1;
						}

						.horizontalLeft{
							position:absolute;
							top: calc(50% - 0.5em);
							left: -50%;
							width: 100%;
							height: 1em;
							border-top: gray solid 1em;
							z-index: -1;
						}

						.horizontalRight{
							position:absolute;
							top: calc(50% - 0.5em);
							right: -50%;
							width: 100%;
							height: 1em;
							border-top: gray solid 1em;
							z-index: -1;
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
							z-index: -1;
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
							z-index: -1;
						}
						.tech {
							text-align: center;
							display: inline-block;
							border: rgb(35,35,35) 0.25em solid;
							padding: 1em;
							border-radius: 0.1em;
							background: rgb(55, 55, 55);
						}
					</style>
					<c:forEach items="${techs}" var="tech">
						<c:if test="${tech.category == category}">
							<div style="grid-column: ${tech.x};grid-row: ${tech.y};">
								<div style="position:relative;">
									<div class="tech" style="margin: 1em; ${home.tech.researchedTechs.contains(tech)? 'background-color: darkgreen': tech.technology.isAvailable(home)? 'background-color: goldenrod;' : 'background-color: dark-gray'}">
										<p>${tech.technology.name}</p>
									</div>
									<c:if test="${tech.cssClass.contains(',')}" var="multiple">
										<c:set var="array" value="${fn:split(tech.cssClass, ',')}"/>
										<c:forEach var="divClass" items="${array}">
											<div class="${divClass}" style="${tech.technology.isAvailable(home) ? 'border-color: gold' : home.tech.researchedTechs.contains(tech) ? 'border-color: green' : 'border-color: gray'}"></div>
										</c:forEach>
									</c:if>
									<c:if test="${multiple == false}">
										<div class="${tech.cssClass}" style="${tech.technology.isAvailable(home) ? 'border-color: gold' : home.tech.researchedTechs.contains(tech) ? 'border-color: green' : 'border-color: gray'}"></div>
									</c:if>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:when>
		</c:choose>
	</table>
</div>
</body>
</html>