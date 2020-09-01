<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value == -1}">
		Central Powers
	</c:when>
	<c:when test="${value == 0}">
		Neutral
	</c:when>
	<c:when test="${value == 1}">
		Entente
	</c:when>
</c:choose>