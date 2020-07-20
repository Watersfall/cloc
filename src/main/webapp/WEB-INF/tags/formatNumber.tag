<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="value" required="true" type="java.lang.Long" %>
<c:set var="formatted" value="${value.toString()}"/>
<c:choose>
	<c:when test="${fn:length(formatted) > 15}">
		<c:set var="formatted" value=">999T"/>
	</c:when>
	<c:when test="${fn:length(formatted) > 12}">
		<c:set var="formatted">
			<fmt:formatNumber value="${value / 1e12}" maxFractionDigits="2"/>T
		</c:set>
	</c:when>
	<c:when test="${fn:length(formatted) > 9}">
		<c:set var="formatted">
			<fmt:formatNumber value="${value / 1e9}" maxFractionDigits="2"/>B
		</c:set>
	</c:when>
	<c:when test="${fn:length(formatted) > 6}">
		<c:set var="formatted">
			<fmt:formatNumber value="${value / 1e6}" maxFractionDigits="2"/>M
		</c:set>
	</c:when>
	<c:when test="${fn:length(formatted) > 3}">
		<c:set var="formatted">
			<fmt:formatNumber value="${value / 1e3}" maxFractionDigits="2"/>K
		</c:set>
	</c:when>
</c:choose>
${formatted}