<%@ include file="includes/top.jsp" %>
	<div class="tiling">
		<div class="column">
			<c:forEach var="production" items="${home.production.entrySet()}" varStatus="status">
				<c:if test="${status.index % 2 == 0}">
					<c:set value="${production.value}" var="production"/>
					<%@ include file="api/production.jsp"%>
				</c:if>
			</c:forEach>
		</div>
		<div class="column">
			<c:forEach var="production" items="${home.production.entrySet()}" varStatus="status">
				<c:if test="${status.index % 2 == 1}">
					<c:set value="${production.value}" var="production"/>
					<%@ include file="api/production.jsp"%>
				</c:if>
			</c:forEach>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>