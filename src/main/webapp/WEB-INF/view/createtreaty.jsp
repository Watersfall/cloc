<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<label>
		<input id="name" type="text" name="name" maxlength="32">
	</label>
	<button onclick="createTreaty(document.getElementById('name').value)">Create</button>
</div>
</div>
</body>
</html>