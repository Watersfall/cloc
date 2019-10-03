<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:if test="${empty user}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<div class="settings">
			<h1>Settings</h1>
			<table id="policy">
				<tr>
					<td>
						<label for="flag">
							Flag
						</label>
					</td>
					<td>
						<input type="text" maxlength="128" name="flag" id="flag" value="${home.cosmetic.flag}" placeholder="Flag"/>
					</td>
					<td>
						<button onclick="settings('flag', document.getElementById('flag').value)">Update Flag</button>
					</td>
				</tr>
				<tr>
					<td>
						<label for="portrait">
							Portrait
						</label>
					</td>
					<td>
						<input type="text" maxlength="128" name="portrait" id="portrait" value="${home.cosmetic.portrait}" placeholder="Portrait"/>
					</td>
					<td>
						<button onclick="settings('portrait', document.getElementById('portrait').value)">Update Portrait</button>
					</td>
				</tr>
				<tr>
					<td>
						<label for="nationTitle">
							Nation Title
						</label>
					</td>
					<td>
						<input type="text" maxlength="128" name="nationTitle" id="nationTitle" value="${home.cosmetic.nationTitle}" placeholder="Nation Title"/>
					</td>
					<td>
						<button onclick="settings('nationTitle', document.getElementById('nationTitle').value)">Update Nation Title</button>
					</td>
				</tr>
				<tr>
					<td>
						<label for="leaderTitle">
							Leader Title
						</label>
					</td>
					<td>
						<input type="text" maxlength="128" name="leaderTitle" id="leaderTitle" value="${home.cosmetic.leaderTitle}" placeholder="Leader Title"/>
					</td>
					<td>
						<button onclick="settings('leaderTitle', document.getElementById('leaderTitle').value)">Update Leader Title</button>
					</td>
				</tr>
				<tr>
					<td>
						<label for="description">
							Description
						</label>
					</td>
					<td>
						<textarea maxlength="65536" name="description" id="description" placeholder="Description">${home.cosmetic.description}</textarea>
					</td>
					<td>
						<button onclick="settings('description', document.getElementById('description').value)">Update Description</button>
					</td>
				</tr>
			</table>
			<button onclick="settingsAll(document.getElementById('flag').value, document.getElementById('portrait').value, document.getElementById('nationTitle').value, document.getElementById('leaderTitle').value, document.getElementById('description').value)">Update All</button>
		</div>
	</c:if>
</div>
</div>
</body>
</html>
