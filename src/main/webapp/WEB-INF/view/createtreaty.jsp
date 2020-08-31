<%@ include file="includes/top.jsp" %>
<c:choose>
	<c:when test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:when>
	<c:otherwise>
		<label>
			<input id="name" type="text" name="name" maxlength="32">
		</label>
		<button onclick="createTreaty(document.getElementById('name').value)">Create</button>
	</c:otherwise>
</c:choose>
<%@ include file="includes/bottom.jsp" %>