<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="decisions" type="java.util.List"--%>
<%@ include file="includes/defaultTop.jsp" %>
<c:choose>
	<c:when test="${empty home}">
		<p>You must be logged in to view this page</p>
	</c:when>
	<c:otherwise>
		<br>
		<div class="categories four">
			<a href="${pageContext.request.contextPath}/decisions/economy/">Economy</a>
			<a href="${pageContext.request.contextPath}/decisions/domestic/">Domestic</a>
			<a href="${pageContext.request.contextPath}/decisions/foreign/">Foreign</a>
			<a href="${pageContext.request.contextPath}/decisions/military/">Military</a>
		</div>
		<h1><c:out value="${category.name}"/> Decisions</h1>
		<div class="specialTable">
			<div class="header">
				<div class="name">
					<p class="halfPad">Policy</p>
				</div>
				<div class="description">
					<p class="halfPad">Description</p>
				</div>
				<div class="cost">
					<p class="halfPad">Cost</p>
				</div>
				<div class="button"></div>
			</div>
			<c:forEach items="${decisions}" var="current">
				<c:if test="${current.category == category || category == 'ALL'}">
					<div class="policy">
						<div class="name">
							<p class="fullPad">${current.name}</p>
						</div>
						<div class="description">
							<p class="fullPad">${current.description}</p>
						</div>
						<div class="cost">
								<p class="fullPad">${home.getDecisionCostDisplayString(current)}</p>
						</div>
						<div class="button">
							<button onclick="decision('${current}');">${current.buttonText}</button>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>