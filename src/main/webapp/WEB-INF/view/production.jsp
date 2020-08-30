<%@ include file="includes/top.jsp" %>
	<script>
		window.onload = function() {
			document.addEventListener("click", test);
			setFreeFactories(${home.freeFactories});
		}
	</script>
	<div class="title"><span id="free_factories">${home.freeFactories}</span> Free Factories</div>
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
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<img onclick="createNewProduction();" src="${pageContext.request.contextPath}/images/production/new.svg" alt="new"/>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>