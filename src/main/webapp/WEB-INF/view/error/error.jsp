<%--@elvariable id="error" type="java.lang.String"--%>
<%@ include file="../includes/defaultTop.jsp" %>
<c:choose>
	<c:when test="${empty error}">
		<p>You have visited this page incorrectly!</p>
	</c:when>
	<c:otherwise>
		<c:out value="${error}" escapeXml="false"/>
	</c:otherwise>
</c:choose>
<%@ include file="../includes/defaultBottom.jsp" %>