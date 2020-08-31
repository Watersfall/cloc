<%@ include file="includes/top.jsp" %>
	<div class="title">Settings</div>
	<div class="tiling">
		<div class="column">
			<div class="tile settings">
				<div class="title">Game Settings</div>
				<div class="subtile">
					<form enctype="multipart/form-data" action="${pageContext.request.contextPath}/settings/" method="POST">
						<label>
							<div class="title">Update Flag</div>
							<input name="flag" type="file" accept="image/png"/>
						</label>
						<button type="submit" class="blue">Update Flag</button>
					</form>
				</div>
				<div class="subtile">
					<form enctype="multipart/form-data" action="${pageContext.request.contextPath}/settings/" method="POST">
						<label>
							<div class="title">Update Portrait</div>
							<input name="portrait" type="file" accept="image/png"/>
						</label>
						<button type="submit" class="blue">Update Portrait</button>
					</form>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Nation Title</div>
						<input id="nation_title" type="text" maxlength="32"/>
					</label>
					<button onclick="settings('nationTitle', document.getElementById('nation_title').value);" class="blue">Update Title</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Leader Title</div>
						<input id="leader_title" type="text" maxlength="32"/>
					</label>
					<button onclick="settings('leaderTitle', document.getElementById('leader_title').value);" class="blue">Update Title</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Nation Description</div>
						<textarea id="description"><c:out value="${home.cosmetic.description}"/></textarea>
					</label>
					<button onclick="settings('description', document.getElementById('description').value);" class="blue">Update Description</button>
				</div>
			</div>
			<div class="tile">
				<div class="title">Account Settings (Currently non-functional)</div>
				<div class="subtile">
					<div class="title">Update Email Address</div>
					<label>
						Current Password:
						<input type="password"/>
					</label><br>
					<label>
						New Email:
						<input type="email"/>
					</label><br>
					<button class="blue">Update Email</button>
				</div>
				<div class="subtile">
					<div class="title">Update Password</div>
					<label>
						Current Password:
						<input type="password"/>
					</label><br>
					<label>
						New Password:
						<input type="password"/>
					</label><br>
					<button class="blue">Update Password</button>
				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>