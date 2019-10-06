<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.treaty.TreatyMember"--%>
<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:choose>
		<c:when test="${empty param['id']}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${treaty == null}">
			<p>No treaty with that ID!</p>
		</c:when>
		<c:otherwise>
			<c:if test="${home != null && home.invites.invites.contains(treaty.id)}">
				<p>You have been invited to this alliance!</p>
				<button onclick="updateTreaty('accept', '${treaty.id}')">Accept</button><button onclick="updateTreaty('decline', '${treaty.id}')">Reject</button>
			</c:if>
			<h1>${treaty.name}</h1>
			<br>
			<img style="width: 40%; height: auto;" src="https://i.imgur.com/${treaty.flag}" alt="flag"/>
			<br>
			<p>${treaty.description}</p>
			<c:if test="${home.treaty != null && home.treaty.id == treaty.id}">
				<br>
				<p>Your Permissions</p>
				<c:if test="${home.founder}">
					<p>Founder</p>
				</c:if>
				<c:if test="${home.manage}">
					<p>Manage</p>
				</c:if>
				<c:if test="${home.kick}">
					<p>Kick</p>
				</c:if>
				<c:if test="${home.invite}">
					<p>Invite</p>
				</c:if>
				<c:if test="${home.edit}">
					<p>Edit</p>
				</c:if>
			</c:if>
			<br>
			<c:if test="${(home.treaty != null && home.treaty.id == treaty.id) && (home.kick || home.manage || home.founder)}">
				<script>
					function display()
					{
						let panel = document.getElementById("admin");
						let button = document.getElementById("adminButton");
						if(panel.style.display === "")
						{
							panel.style.display = "block";
							button.innerHTML = "Hide Admin Actions";
						}
						else
						{
							panel.style.display = "";
							button.innerHTML = "Show Admin Actions";
						}
					}
				</script>
				<style>
					#admin{
						display: none;
					}
				</style>
				<button id="adminButton" onclick="display()">Show Admin Actions</button>
				<div id="admin">
					<label for="name">Alliance Name</label>
					<input type="text" id="name" value="${treaty.name}"/>
					<button onclick="updateTreaty('name', document.getElementById('name').value)">Set Name</button><br>
					<label for="flag">Alliance Flag</label>
					<input type="text" id="flag" value="${treaty.flag}"/>
					<button onclick="updateTreaty('flag', document.getElementById('flag').value)">Set Flag</button><br>
					<label for="description">Description<br></label>
					<textarea style="width: 75%;" id="description">${treaty.description}</textarea><br>
					<button onclick="updateTreaty('description', document.getElementById('description').value)">Set Description</button><br>
					<label for="invite">Invite</label>
					<input type="text" id="invite"/>
					<button onclick="updateTreaty('invite', document.getElementById('invite').value)">>Invite</button>
				</div>
			</c:if>
			<h3>Members</h3>
			<table id="policy">
				<tr>
					<th>Name</th>
					<th>Region</th>
					<c:if test="${(home.treaty != null && home.treaty.id == treaty.id) && (home.kick || home.manage || home.founder)}">
						<th>Manage</th>
					</c:if>
				</tr>
				<c:forEach items="${treaty.members}" var="member">
					<tr>
						<td>${member.cosmetic.nationName}</td>
						<td>${member.foreign.region.name}</td>
						<c:if test="${(home.treaty != null && home.treaty.id == treaty.id) && (home.kick || home.manage || home.founder)}">
							<td><button>Kick</button></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<br>
			<c:if test="${home.treaty != null && home.treaty.id == treaty.id}">
				<button onclick="updateTreaty('resign', 'anything really')">Resign</button>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>
</div>
</body>
</html>