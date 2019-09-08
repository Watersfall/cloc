<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
	<c:if test="${empty user}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<div class="settings">
			<form action="${pageContext.request.contextPath}/settings.do" method="POST">
				<label>
					Update Flag
					<input class="loginText" type="text" name="flag" placeholder="Imgur image code" value="${home.cosmetic.flag}">
				</label>
				<br>
				<label>
					Update leader portrait
					<input class="loginText" type="text" name="leader" placeholder="Imgur image code" value="${home.cosmetic.portrait}">
				</label>
				<br>
				<label>
					Leader Title
					<input class="loginText" type="text" name="leadertitle" placeholder="Title" value="${home.cosmetic.leaderTitle}">
				</label>
				<br>
				<label>
					Nation Title
					<input class="loginText" type="text" name="nationtitle" placeholder="Title" value="${home.cosmetic.nationTitle}">
				</label>
				<br>
				<label>
					Nation Description
					<input class="loginText" type="text" name="description" placeholder="Description" value="${home.cosmetic.description}">
				</label>
				<br>
				<button type="submit">Update!</button>
				<br>
			</form>
		</div>
	</c:if>
</div>
</body>
</html>
