<%@ include file="includes/defaultTop.jsp" %>

	<form class="registerForm" action="${pageContext.request.contextPath}/register/" method="POST">
		<label>
			Username
			<input class="RegisterText" type="text" name="username" maxlength="32" placeholder="Username">
		</label>
		<br>
		<label>
			Nation Name
			<input class="RegisterText" type="text" name="nation" maxlength="32" placeholder="Nation Name">
		</label>
		<br>
		<label>
			Capital City
			<input class="RegisterText" type="text" name="capital" maxlength="32" placeholder="Capital City">
		</label>
		<br>
		<label>
			Password
			<input class="RegisterText" type="password" name="password" placeholder="Password">
		</label>
		<br>
		<label>
			Region
			<select name="region" class="RegisterText">
				<option value="NORTH_AMERICA">North America</option>
				<option value="SOUTH_AMERICA">South America</option>
				<option value="EUROPE">Europe</option>
				<option value="AFRICA">Africa</option>
				<option value="MIDDLE_EAST">Middle East</option>
				<option value="ASIA">Asia</option>
				<option value="OCEANIA">Oceania</option>
				<option value="SIBERIA">Siberia</option>
			</select>
		</label>
		<br>
		<label>
			Government
			<select name="government" class="RegisterText">
				<option value="10">Absolute Monarch</option>
				<option value="30">Military Dictatorship</option>
				<option value="50">Constitutional Monarchy</option>
				<option value="70">Federal Republic</option>
				<option value="90">Direct Democracy</option>
			</select>
		</label>
		<br>
		<label>
			Economy
			<select name="economy" class="RegisterText">
				<option value="25">Communist</option>
				<option value="50">State Capitalist</option>
				<option value="75">Free Market</option>
			</select>
		</label>
		<br>
		<button type="submit">Register!</button>
	</form>
<%@ include file="includes/defaultBottom.jsp" %>