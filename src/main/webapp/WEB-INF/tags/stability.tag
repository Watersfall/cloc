<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value > 90}">
		Peace and Prosperity
	</c:when>
	<c:when test="${value > 80}">
		Unsinkable
	</c:when>
	<c:when test="${value > 70}">
		Very Stable
	</c:when>
	<c:when test="${value > 60}">
		Stable
	</c:when>
	<c:when test="${value > 50}">
		Steady
	</c:when>
	<c:when test="${value > 40}">
		Unsteady
	</c:when>
	<c:when test="${value > 30}">
		Unsafe Driving Conditions
	</c:when>
	<c:when test="${value > 20}">
		Rioting as a National Sport
	</c:when>
	<c:when test="${value > 10}">
		Bring of Collapse
	</c:when>
	<c:otherwise>
		Civil War
	</c:otherwise>
</c:choose>