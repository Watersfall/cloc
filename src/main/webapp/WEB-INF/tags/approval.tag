<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="value" required="true" %>
<c:choose>
	<c:when test="${value > 90}">
		Worshipped as the Color Purple
	</c:when>
	<c:when test="${value > 80}">
		Worshipped as Hexy's Disciple
	</c:when>
	<c:when test="${value > 70}">
		Loved
	</c:when>
	<c:when test="${value > 60}">
		Liked
	</c:when>
	<c:when test="${value > 50}">
		Accepted
	</c:when>
	<c:when test="${value > 40}">
		Neutral
	</c:when>
	<c:when test="${value > 30}">
		Disliked
	</c:when>
	<c:when test="${value > 20}">
		Hated
	</c:when>
	<c:when test="${value > 10}">
		Enemy of Hexy
	</c:when>
	<c:otherwise>
		Enemy of the Color Purple
	</c:otherwise>
</c:choose>