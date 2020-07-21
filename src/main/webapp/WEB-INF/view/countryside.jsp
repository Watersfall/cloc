<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="policy" type="com.watersfall.clocgame.action.DecisionActions"--%>
<%@ include file="includes/defaultTop.jsp" %>
<c:choose>
	<c:when test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:when>
	<c:otherwise>
		<h1>The Countryside</h1>
		<div>
			<div class="element farm">
				<h3>Farms</h3>
				<img src="${pageContext.request.contextPath}/images/city/farm.png" alt="farm"/>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>