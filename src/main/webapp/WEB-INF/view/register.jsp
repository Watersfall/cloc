<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
	<form class="registerForm" action="${pageContext.request.contextPath}/register.do" method="POST">
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
			<input class="RegisterText" type="text" name="capital" maxlength="32" placeholder="Nation Name">
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
				<option value="North America">North America</option>
				<option value="South America">South America</option>
				<option value="Europe">Europe</option>
				<option value="Africa">Africa</option>
				<option value="Middle East">Middle East</option>
				<option value="Asia">Asia</option>
				<option value="Oceania">Oceania</option>
				<option value="Siberia">Siberia</option>
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
</div>
</body>
</html>