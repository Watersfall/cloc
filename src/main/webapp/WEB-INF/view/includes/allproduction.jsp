<%@ include file="taglibs.jsp" %>
<c:forEach var="production" items="${home.production}">
	<div class="element">
		<%@include file="production.jsp" %>
	</div>
</c:forEach>
