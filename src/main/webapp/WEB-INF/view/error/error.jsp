<%--@elvariable id="error" type="java.lang.String"--%>
<%@ include file="../includes/top.jsp" %>
<c:choose>
	<c:when test="${empty error}">
		<p>You have visited this page incorrectly!</p>
	</c:when>
	<c:otherwise>
		<c:out escapeXml="false" value="${error.message}"/>
	</c:otherwise>
</c:choose>
<%@ include file="../includes/bottom.jsp" %>