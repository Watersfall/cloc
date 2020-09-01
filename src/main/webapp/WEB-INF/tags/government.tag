<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value > 80}">
		Direct Democracy
	</c:when>
	<c:when test="${value > 60}">
		Constitutional Monarchy
	</c:when>
	<c:when test="${value > 40}">
		One Party State
	</c:when>
	<c:when test="${value > 20}">
		Military Dictatorship
	</c:when>
	<c:otherwise>
		Absolute Monarchy
	</c:otherwise>
</c:choose>