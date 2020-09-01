<%@ include file="includes/top.jsp" %>
<%@ page import="com.watersfall.clocgame.model.Region" %>
<div class="title">Register</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="subtile registration">
					<form action="${pageContext.request.contextPath}/register/" method="POST">
						<label>
							<div class="title">Username</div>
							<input type="text" maxlength="32" name="username" placeholder="Username"/>
						</label><br>
						<label>
							<div class="title">Password</div>
							<input type="password" name="password" placeholder="Password"/>
						</label><br>
						<label>
							<div class="title">Nation Name</div>
							<input type="text" maxlength="32" name="nation" placeholder="Nation Name"/>
						</label><br>
						<label>
							<div class="title">Capital City</div>
							<input type="text" maxlength="32" name="capital" placeholder="Capital City"/>
						</label><br>
						<label>
							<div class="title">Region</div>
							<select name="region">
								<c:forEach items="${Region.values()}" var="region">
									<option value="${region.name()}">${region.name}</option>
								</c:forEach>
							</select>
						</label><br>
						<label>
							<div class="title">Government</div>
							<select name="government">
								<option value="10">Absolute Monarch</option>
								<option value="30">Military Dictatorship</option>
								<option value="50">Constitutional Monarchy</option>
								<option value="70">Federal Republic</option>
								<option value="90">Direct Democracy</option>
							</select>
						</label><br>
						<label>
							<div class="title">Economy</div>
							<select name="economy">
								<option value="25">Communist</option>
								<option value="50">State Capitalist</option>
								<option value="75">Free Market</option>
							</select>
						</label><br>
						<button class="blue" type="submit">Register</button>
					</form>
				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>