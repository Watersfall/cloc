<%--@elvariable id="treaties" type="java.util.ArrayList"--%>
<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
	<table id="policy">
		<tr>
			<th>Flag</th>
			<th>Name</th>
			<th>Description</th>
			<th>Members</th>
		</tr>
		<c:forEach var="treaty" items="${treaties}">
			<tr>
				<td>${treaty.flag}</td>
				<td><a href="${pageContext.request.contextPath}/treaty.jsp?id=${treaty.id}">${treaty.name}</a></td>
				<td>${treaty.description}</td>
				<td>${treaty.memberCount}</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>