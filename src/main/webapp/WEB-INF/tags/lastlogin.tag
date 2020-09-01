<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="time" required="true" type="java.lang.Long"%>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="lastLogin" value="${(now.time - time) / (1000 * 60 * 60)}"/>
<c:choose>
	<c:when test="${lastLogin < 1}">
		<p>Online Now</p>
	</c:when>
	<c:when test="${lastLogin < 2}">
		<p>Last Seen: 1 hour ago</p>
	</c:when>
	<c:otherwise>
		<p>Last Seen: <fmt:formatNumber value="${lastLogin}" maxFractionDigits="0"/> hours ago</p>
	</c:otherwise>
</c:choose>
