<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">

	<label>
		<input id="name" type="text" name="name" maxlength="32">
	</label>
	<button onclick="createTreaty(document.getElementById('name').value)">Create</button>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>