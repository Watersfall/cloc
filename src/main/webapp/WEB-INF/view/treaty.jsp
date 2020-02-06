<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.treaty.TreatyMember"--%>
<%--@elvariable id="stats" type="com.watersfall.clocgame.model.Stats"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:choose>
		<c:when test="${empty id}">
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
			<h1><c:out value="${treaty.name}"/></h1>
			<img class="veryLarge" src="${pageContext.request.contextPath}/user/treaty/<c:out value="${treaty.flag}"/>" alt="flag"/>
			<br>
			<p><c:out value="${treaty.description}"/></p>
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
			<c:if test="${(home.treaty != null && home.treaty.id == treaty.id) && (home.kick || home.manage || home.founder || home.invite || home.edit)}">
				<button id="adminButton" onclick="display()">Show Admin Actions</button>
				<div id="admin" class="toggleable">
					<c:if test="${home.manage || home.founder || home.edit}">
						<label for="name">Alliance Name</label>
						<input type="text" id="name" value="${treaty.name}"/>
						<button onclick="updateTreaty('name', document.getElementById('name').value)">Set Name</button><br>
						<label for="flag">Alliance Flag</label>
						<form id="flagForm" action="${pageContext.request.contextPath}/treaty/${treaty.id}" method="POST" enctype="multipart/form-data">
							<input type="file" id="flag" name="flag" accept="image/png"/>
						</form>
						<button onclick="document.getElementById('flagForm').submit();">Set Flag</button><br>
						<label for="description">Description<br></label>
						<textarea style="width: 75%;" id="description">${treaty.description}</textarea><br>
						<button onclick="updateTreaty('description', document.getElementById('description').value)">Set Description</button><br>
					</c:if>
					<c:if test="${home.manage || home.founder || home.invite}">
						<label for="invite">Invite</label>
						<input type="text" id="invite"/>
						<button onclick="updateTreaty('invite', document.getElementById('invite').value)">Invite</button>
					</c:if>
				</div>
			</c:if>
			<h3>Stats</h3>
			<button id="statsButton" onclick="stats();">Show Stats</button>
			<div id="stats" class="toggleable">
				<table class="standardTable">
					<c:forEach items="${stats.getTreatyStats(treaty.id).map}" var="stat">
						<tr>
							<td>${stat.key}</td>
							<td>${stat.value}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<h3>Members</h3>
			<table class="standardTable">
				<tr>
					<th>Name</th>
					<th>Region</th>
					<th>Roles</th>
					<c:if test="${(home.treaty != null && home.treaty.id == treaty.id) && (home.kick || home.manage || home.founder)}">
						<th>Manage</th>
					</c:if>
				</tr>
				<c:forEach items="${treaty.members}" var="member">
					<tr>
						<td><b><a href="${pageContext.request.contextPath}/nation/${member.id}"><c:out value="${member.cosmetic.nationName}"/></a></b></td>
						<td><b><a href="${pageContext.request.contextPath}/map/region/${member.foreign.region.name}">${member.foreign.region.name}</a></b></td>
						<td>${member.roles}</td>
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
<%@ include file="includes/defaultBottom.jsp" %>