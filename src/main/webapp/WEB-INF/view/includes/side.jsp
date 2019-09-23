<div class="top">
	<c:choose>
		<c:when test="${sessionScope.user == null}">
			<div class="login">
				<label>
					<input id="username" class="loginText" type="text" name="username" placeholder="Username">
				</label><br>
				<label>
					<input id="password" class="loginText" type="password" name="password" placeholder="Password">
				</label><br>
				<input class="loginText" type="submit" onclick="login(document.getElementById('username').value, document.getElementById('password').value);" value="Login">
				<form class="loginForm" action="${pageContext.request.contextPath}/index.jsp" method="GET">
					<input type="submit" value="Home">
				</form>
			</div>
			<div class="login">
				<form class="loginForm" action="${pageContext.request.contextPath}/register.jsp">
					<input class="loginText" type="submit" value="Register">
				</form>
			</div>
			<div class="login">
				<a href="https://discord.gg/x2VwYkS">Discord Server</a>
			</div>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/main.jsp">
				<img class="headerFlag" src="https://imgur.com/<c:out value="${home.cosmetic.flag}"/>" alt="Flag">
			</a>
			<ul>
				<li>
					<a style="cursor: pointer;" onclick="showHideCities()">
						<div class="headerTab">
							<p>Cities</p>
						</div>
					</a>
					<ul id="cities" style="display: none">
						<c:forEach var="city" items="${home.cities.cities}">
							<li>
								<a href="${pageContext.request.contextPath}/cities.jsp?id=${city.value.id}">
									<div class="headerTabSmall">
										<p>${city.value.name}</p>
									</div>
								</a>
							</li>
						</c:forEach>
					</ul>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="showHidePolicies()">
						<div class="headerTab">
							<p>Policies</p>
						</div>
					</a>
					<ul id="policies" style="display: none">
						<li>
							<a href="${pageContext.request.contextPath}/policies.jsp?policies=Economic">
								<div class="headerTabSmall">
									<p>Economy</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/policies.jsp?policies=Domestic">
								<div class="headerTabSmall">
									<p>Domestic</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/policies.jsp?policies=Foreign">
								<div class="headerTabSmall">
									<p>Foreign</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/policies.jsp?policies=Military">
								<div class="headerTabSmall">
									<p>Military</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/decisions.jsp">
						<div class="headerTab">
							<p>Decisions</p>
						</div>
					</a>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="showHideWorld()">
						<div class="headerTab">
							<p>Realpolitik</p>
						</div>
					</a>
					<ul id="world" style="display: none">
						<li>
							<a href="${pageContext.request.contextPath}/index.jsp">
								<div class="headerTabSmall">
									<p>World Rankings</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/map.jsp">
								<div class="headerTabSmall">
									<p>Regions</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/treaties.jsp">
						<div class="headerTab">
							<p>Treaties</p>
						</div>
					</a>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="showHideUser()">
						<div class="headerTab">
							<p>User</p>
						</div>
					</a>
					<ul id="user" style="display: none">
						<li class="headerTabSmall">
							<a href="${pageContext.request.contextPath}/settings.jsp">
								<div class="headerTab">
									<p>Settings</p>
								</div>
							</a>
						</li>
						<li class="headerTabSmall">
							<a href="${pageContext.request.contextPath}/logout.jsp">
								<div class="headerTab">
									<p>Log Out</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</c:otherwise>
	</c:choose>
</div>
<%@ include file="header.jsp" %>