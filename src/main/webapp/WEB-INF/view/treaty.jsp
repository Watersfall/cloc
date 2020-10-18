<%@ include file="includes/top.jsp" %>
<%--@elvariable id="treaty" type="net.watersfall.clocgame.model.treaty.Treaty"--%>
	<c:if test="${manage && (!(home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.kick) || (home.treaty.id != treaty.id))}">
		You do not have permission to do this!
	</c:if>
	<c:if test="${!manage || ((home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.kick) && home.treaty.id == treaty.id)}">
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="title"><c:out escapeXml="false" value="${treaty.name}"/></div><br>
					<img class="large_flag" src="/user/treaty/${treaty.flag}" alt="flag"/><br>
					<div class="description"><c:out escapeXml="false" value="${treaty.description}"/></div>
					<c:if test="${home.treaty.id == treaty.id}">
						Your Permissions: <br>${home.treatyPermissions.roles}<br>
						<c:if test="${not empty home.treatyPermissions.roles}">
							<button class="blue" onclick="toggle('admin_actions')">Toggle Admin Actions</button><br>
							<div class="toggleable-default-off" id="admin_actions">
								<div class="subtile">
									<div class="centered">
										<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.edit || home.treatyPermissions.founder}">
											<label>
												Treaty Name<br>
												<input type="text" id="name" placeholder="${treaty.name}"/><br>
												<button onclick="updateTreaty('name', document.getElementById('name').value);" class="blue">Change Name</button><br>
											</label>
											<br>
										</c:if>
										<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.edit || home.treatyPermissions.founder}">
											<form method="POST" action="${pageContext.request.contextPath}/treaty/${treaty.id}" enctype="multipart/form-data">
												<label>
													Treaty Flag<br>
													<input name="flag" id="flag" type="file" accept="image/png"/><br>
													<button class="blue" type="submit">Change Flag</button><br>
												</label>
												<br>
											</form>
										</c:if>
										<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.edit || home.treatyPermissions.founder}">
											<label>
												Treaty Description<br>
												<textarea id="description" cols="25" rows="3">${treaty.description}</textarea><br>
												<button onclick="updateTreaty('description', document.getElementById('description').value);" class="blue">Update Description</button><br>
											</label>
											<br>
										</c:if>
										<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.invite}">
											<label>
												Invite<br>
												<input id="invite" type="text"/><br>
												<button onclick="updateTreaty('invite', document.getElementById('invite').value);" class="blue">Send Invite</button><br>
											</label>
											<br>
										</c:if>
										<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.kick}">
											<a href="${treaty.id}/manage" class="button blue">Manage Members</a>
										</c:if>
									</div>
								</div>
							</div>
						</c:if>
					</c:if>
					<h2>Members</h2>
					<c:if test="${!manage}">
						<c:forEach var="nation" items="${treaty.members}">
							<div class="rankings">
								<div class="subtitle">
									<img src="/user/flag/${nation.cosmetic.flag}" alt="flag"/>
										${nation.nationUrl}
								</div>
								<table>
									<tr>
										<td>Leader</td>
										<td>GDP</td>
										<td>Region</td>
										<td>Roles</td>
									</tr>
									<tr>
										<td><c:out escapeXml="false" value="${nation.cosmetic.username}"/></td>
										<td>$<fmt:formatNumber value="${nation.stats.gdp}"/></td>
										<td>${nation.stats.region.name}</td>
										<td>
											<c:if test="${empty nation.treatyPermissions.roles}">
												Member
											</c:if>
											<c:if test="${not empty nation.treatyPermissions.roles}">
												${nation.treatyPermissions.roles}
											</c:if>
										</td>
									</tr>
								</table>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${manage}">
						<c:forEach var="nation" items="${treaty.members}">
							<div class="rankings">
								<div class="subtitle">
									<img src="/user/flag/${nation.cosmetic.flag}" alt="flag"/>
										${nation.nationUrl}
								</div>
								<div>
									Current Roles: <c:out value="${nation.treatyPermissions.roles}"/><br>
									<button onclick="updateTreaty('kick', ${nation.id});" class="red">Kick</button>
									<c:if test="${home.treatyPermissions.founder}">
										<button onclick="updateTreaty('toggle_edit', ${nation.id});" class="blue">Toggle Edit</button>
										<button onclick="updateTreaty('toggle_invite', ${nation.id});" class="blue">Toggle Invite</button>
										<button onclick="updateTreaty('toggle_kick', ${nation.id});" class="blue">Toggle Kick</button>
										<button onclick="updateTreaty('toggle_manage', ${nation.id});" class="blue">Toggle Manage</button>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<br>
					<c:if test="${home.treaty.id == treaty.id}">
						<button onclick="updateTreaty('resign', 1);" class="red">Resign</button>
					</c:if>
				</div>
			</div>
		</div>
	</c:if>
<%@ include file="includes/bottom.jsp" %>