<%@ include file="taglibs.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="toggle">
	<c:if test="${home != null}">
		<a href="${pageContext.request.contextPath}/main/">
			<img src="${pageContext.request.contextPath}/user/flag/<c:out value="${home.cosmetic.flag}"/>" alt="flag">
		</a>
	</c:if>
	<div onclick="toggle('header')">
		<p>&#9776;</p>
	</div>
</div>