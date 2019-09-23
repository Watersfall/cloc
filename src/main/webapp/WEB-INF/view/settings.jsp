<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:if test="${empty user}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<div class="settings">
			<label>
				Flag
				<input type="text" maxlength="128" name="flag" id="flag" value="${home.cosmetic.flag}" placeholder="Flag"/>
				<button onclick="settings('flag', document.getElementById('flag').value)">Update Flag</button>
			</label>
			<br>
			<label>
				Portrait
				<input type="text" maxlength="128" name="portrait" id="portrait" value="${home.cosmetic.portrait}" placeholder="Portrait"/>
				<button onclick="settings('portrait', document.getElementById('portrait').value)">Update Portrait</button>
			</label>
			<br>
			<label>
				Nation Title
				<input type="text" maxlength="128" name="nationTitle" id="nationTitle" value="${home.cosmetic.nationTitle}" placeholder="Nation Title"/>
				<button onclick="settings('nationTitle', document.getElementById('nationTitle').value)">Update Nation Title</button>
			</label>
			<br>
			<label>
				Leader Title
				<input type="text" maxlength="128" name="leaderTitle" id="leaderTitle" value="${home.cosmetic.leaderTitle}" placeholder="Leader Title"/>
				<button onclick="settings('leaderTitle', document.getElementById('leaderTitle').value)">Update Leader Title</button>
			</label>
			<br>
			<label>
				Description
				<textarea type="t" maxlength="65536" name="description" id="description" placeholder="Description">${home.cosmetic.description}</textarea>
				<button onclick="settings('description', document.getElementById('description').value)">Update Description</button>
			</label>
			<br>
			<button onclick="settingsAll(document.getElementById('flag').value, document.getElementById('portrait').value, document.getElementById('nationTitle').value, document.getElementById('leaderTitle').value, document.getElementById('description').value)">Update All</button>
		</div>
	</c:if>
</div>
</body>
</html>
