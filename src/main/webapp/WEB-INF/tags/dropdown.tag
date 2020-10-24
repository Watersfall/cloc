<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="value" required="true" type="java.util.HashMap" %>
<ul>
	<c:if test="${value.size() <= 0}">
		<li>No change</li>
	</c:if>
	<c:if test="${value.size() > 0}">
		<c:forEach var="item" items="${value.entrySet()}">
			<c:choose>
				<c:when test="${item.value > 0}">
					<li><span class="positive">+<fmt:formatNumber value="${item.value}" maxFractionDigits="2"/>${item.key.text}</span></li>
				</c:when>
				<c:when test="${item.value < 0}">
					<li><span class="negative"><fmt:formatNumber value="${item.value}" maxFractionDigits="2"/>${item.key.text}</span></li>
				</c:when>
			</c:choose>
		</c:forEach>
	</c:if>
</ul>