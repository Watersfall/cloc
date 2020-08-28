<%@ include file="includes/top.jsp" %>
	<div class="title">Settings</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Game Settings</div>
				<div class="subtile">
					<label>
						<div class="title">Update Flag</div>
						<input type="file" accept="image/png"/>
					</label>
					<button class="blue">Update Flag</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Portrait</div>
						<input type="file" accept="image/png"/>
					</label>
					<button class="blue">Update Portrait</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Nation Title</div>
						<input type="text" maxlength="32"/>
					</label>
					<button class="blue">Update Title</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Leader Title</div>
						<input type="text" maxlength="32"/>
					</label>
					<button class="blue">Update Title</button>
				</div>
				<div class="subtile">
					<label>
						<div class="title">Update Nation Description</div>
						<textarea><c:out value="${home.cosmetic.description}"/></textarea>
					</label>
					<button class="blue">Update Description</button>
				</div>
			</div>
			<div class="tile">
				<div class="title">Account Settings</div>
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