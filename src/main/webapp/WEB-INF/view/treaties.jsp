<%--@elvariable id="treaties" type="java.util.ArrayList"--%>
<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">

	<h1>Treaties</h1>
	<table id="nation">
		<tr>
			<th style="width: 10em">Alliance</th>
			<th></th>
			<th>Description</th>
			<th>Members</th>
		</tr>
		<c:forEach var="treaty" items="${treaties}">
			<tr>
				<td><img class="large" src="${pageContext.request.contextPath}/images/flag/<c:out value="${treaty.flag}"/>" alt="flag"/></td>
				<td><a href="${pageContext.request.contextPath}/treaty.jsp?id=${treaty.id}"><c:out value="${treaty.name}"/></a></td>
				<td><c:out value="${treaty.description}"/></td>
				<td>${treaty.memberCount}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>