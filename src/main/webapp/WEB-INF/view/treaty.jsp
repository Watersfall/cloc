<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
	<c:choose>
		<c:when test="${empty param['id']}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${treaty == null}">
			<p>No treaty with that ID!</p>
		</c:when>
		<c:otherwise>
			<h1>${treaty.name}</h1>
			<br>
			<h3>Members</h3>
			<table id="policy">
				<tr>
					<th>Name</th>
					<th>Region</th>
				</tr>
				<c:forEach items="${treaty.members}" var="member">
					<tr>
						<td>${member.cosmetic.nationName}</td>
						<td>${member.foreign.region.name}</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>