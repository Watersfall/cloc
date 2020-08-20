<%@ include file="includes/top.jsp" %>
	<div class="title">Technology Tree</div>
	<br>
	<div class="pagination">
		<c:forEach items="${categories}" var="category">
			<a href="${pageContext.request.contextPath}/technology/tree/${category.name()}">
				${category.name}
			</a>
		</c:forEach>
	</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="tech_tree">
					<%--@elvariable id="techs" type="java.util.List"--%>
					<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
					<c:forEach var="tech" items="${techs}">
						<c:if test="${tech.category == category}">
							<div class="tech_tile" style="grid-column: ${tech.x}; grid-row: ${tech.y}">
								<div class="tech_wrapper">
									<c:choose>
										<c:when test="${home.hasTech(tech)}">
											<div class="tech green">
												${tech.technology.name}
											</div>
										</c:when>
										<c:when test="${tech.technology.isAvailable(home)}">
											<div class="tech gold">
												${tech.technology.name}
											</div>
										</c:when>
										<c:otherwise>
											<div class="tech">
												${tech.technology.name}
											</div>
										</c:otherwise>
									</c:choose>
									<c:forEach items="${tech.cssClass}" var="cssClass">
										<c:choose>
											<c:when test="${home.hasTech(tech)}">
												<div class="green ${cssClass}"></div>
											</c:when>
											<c:when test="${tech.technology.isAvailable(home)}">
												<div class="gold ${cssClass}"></div>
											</c:when>
											<c:otherwise>
												<div class="${cssClass}"></div>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>