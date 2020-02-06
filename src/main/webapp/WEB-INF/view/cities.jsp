<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="id" type="java.lang.Integer"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<%--@elvariable id="city" type="com.watersfall.clocgame.model.nation.City"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:choose>
		<c:when test="${empty id}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${empty city}">
			<p>This city does not exist</p>
		</c:when>
		<c:otherwise>
			<%@include file="includes/city.jsp"%>
		</c:otherwise>
	</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>