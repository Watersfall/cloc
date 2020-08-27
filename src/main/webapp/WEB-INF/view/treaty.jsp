<%@ include file="includes/top.jsp" %>
<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title"><c:out value="${treaty.name}"/></div><br>
				<img class="large_flag" src="/user/treaty/${treaty.flag}" alt="flag"/><br>
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
											<input type="text" placeholder="${treaty.name}"/><br>
											<button class="blue">Change Name</button><br>
										</label>
										<br>
									</c:if>
									<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.edit || home.treatyPermissions.founder}">
										<label>
											Treaty Flag<br>
											<input type="file" accept="image/png"/><br>
											<button class="blue">Change Flag</button><br>
										</label>
										<br>
									</c:if>
									<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.edit || home.treatyPermissions.founder}">
										<label>
											Treaty Description<br>
											<textarea cols="25" rows="3">${treaty.description}</textarea><br>
											<button class="blue">Update Description</button><br>
										</label>
										<br>
									</c:if>
									<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.invite}">
										<label>
											Invite<br>
											<input type="text"/><br>
											<button class="blue">Send Invite</button><br>
										</label>
										<br>
									</c:if>
									<c:if test="${home.treatyPermissions.manage || home.treatyPermissions.founder || home.treatyPermissions.kick}">
										<button class="blue">Manage Members</button>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:if>
				<h2>Members</h2>
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
								<td><c:out value="${nation.cosmetic.username}"/></td>
								<td>$<fmt:formatNumber value="${nation.economy.gdp}"/></td>
								<td>${nation.foreign.region.name}</td>
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
				<br>
				<button class="red">Resign</button>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>