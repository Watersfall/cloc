<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="value" required="true" type="java.util.HashMap" %>
<c:if test="${value.size() <= 0}">
	<p>No change</p>
</c:if>
<c:if test="${value.size() > 0}">
	<ul>
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
	</ul>
</c:if>