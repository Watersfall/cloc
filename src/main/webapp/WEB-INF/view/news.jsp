<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">
	<c:if test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<h1>National News</h1>
		<c:forEach items="${home.news.news.entrySet()}" var="news">
			<jsp:useBean id="newsDate" class="java.util.Date" />
			<jsp:setProperty name="newsDate" property="time" value="${news.value.time}" />
			${news.value.read()}
			<table id="nation" style="margin-bottom: 2em;">
				<tr>
					<td class="left">${news.value.content}</td>
					<td><button class="right" onclick="newsDelete(${news.value.id})">Delete</button></td>
				</tr>
				<tr>
					<td><fmt:formatDate value="${newsDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td></td>
				</tr>
			</table>
		</c:forEach>
		${home.connection.commit()}
		${home.connection.close()}
		<button onclick="newsDelete('all')">Delete All</button>
	</c:if>
</table>
</div>
<%@ include file="includes/header.jsp" %>
</div>
</body>
</html>