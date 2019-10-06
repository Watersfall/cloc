<%--@elvariable id="treaties" type="java.util.ArrayList"--%>
<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<h1>Treaties</h1>
	<table id="policy">
		<tr>
			<th style="width: 10em">Alliance</th>
			<th></th>
			<th>Description</th>
			<th>Members</th>
		</tr>
		<c:forEach var="treaty" items="${treaties}">
			<tr>
				<td><img class="indexflag" src="https://i.imgur.com/${treaty.flag}" alt="flag"/></td>
				<td><a href="${pageContext.request.contextPath}/treaty.jsp?id=${treaty.id}">${treaty.name}</a></td>
				<td>${treaty.description}</td>
				<td>${treaty.memberCount}</td>
			</tr>
		</c:forEach>
	</table>
</div>
</div>
</body>
</html>