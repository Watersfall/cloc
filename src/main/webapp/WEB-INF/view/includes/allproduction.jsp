<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="production" items="${home.production}">
	<li style="position: relative" class="cityElements">
		<%@include file="production.jsp" %>
	</li>
</c:forEach>
