<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="production" items="${home.production}">
	<li style="position: relative" onmouseover="resetProduction(event, ${production.value.id}, ${production.value.factories})" class="cityElements">
		<p class="neutral" style="position: absolute; left: 0; top: 0;">${production.value.productionAsTechnology.technology.name}</p>
		<%@include file="production.jsp" %>
	</li>
</c:forEach>
