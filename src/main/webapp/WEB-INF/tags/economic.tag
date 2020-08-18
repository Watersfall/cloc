<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value > 66}">
		Free Market Economy
	</c:when>
	<c:when test="${value > 33}">
		Mixed Market Economy
	</c:when>
	<c:otherwise>
		Communist Economy
	</c:otherwise>
</c:choose>