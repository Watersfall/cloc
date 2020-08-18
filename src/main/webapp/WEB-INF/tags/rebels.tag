<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value > 75}">
		Syrian Civil War
	</c:when>
	<c:when test="${value > 50}">
		Open Rebellion
	</c:when>
	<c:when test="${value > 25}">
		Guerrillas
	</c:when>
	<c:when test="${value > 0}">
		Scattered Terrorists
	</c:when>
	<c:otherwise>
		None
	</c:otherwise>
</c:choose>