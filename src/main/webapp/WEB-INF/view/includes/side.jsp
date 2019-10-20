<div class="top">
	<c:choose>
		<c:when test="${sessionScope.user == null}">
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/">
						<div class="headerTab">
							<h1>&ltCLOC</h1>
						</div>
					</a>
				</li>
				<li>
					<a>
						<div class="headerTab">
							<p>Login</p>
							<br>
							<label>
								<input id="username" class="loginText" type="text" name="username" placeholder="Username">
							</label><br>
							<label>
								<input id="password" class="loginText" type="password" name="password" placeholder="Password">
							</label><br>
							<button onclick="login(document.getElementById('username').value, document.getElementById('password').value);">Log in</button>
						</div>
					</a>
				</li>
			</ul>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/">
						<div class="headerTab">
							<p>Home</p>
						</div>
					</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/register.jsp">
						<div class="headerTab">
							<p>Register</p>
						</div>
					</a>
				</li>
				<li>
					<a href="https://discord.gg/x2VwYkS">
						<div class="headerTab">
							<p>Discord Server</p>
						</div>
					</a>
				</li>
			</ul>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/main.jsp">
				<img class="headerFlag" src="https://imgur.com/<c:out value="${home.cosmetic.flag}"/>" alt="Flag">
			</a>
			<ul>
				<li>
					<a style="cursor: pointer;" onclick="toggle('cities')">
						<div class="headerTab">
							<p>Cities</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
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
					<a style="cursor: pointer;" onclick="toggle('decisions')">
						<div class="headerTab">
							<p>Decisions</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="decisions" style="display: none">
						<li>
							<a href="${pageContext.request.contextPath}/decisions.jsp?policies=Economic">
								<div class="headerTabSmall">
									<p>Economy</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/decisions.jsp?policies=Domestic">
								<div class="headerTabSmall">
									<p>Domestic</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/decisions.jsp?policies=Foreign">
								<div class="headerTabSmall">
									<p>Foreign</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/decisions.jsp?policies=Military">
								<div class="headerTabSmall">
									<p>Military</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/policy.jsp">
						<div class="headerTab">
							<p>State Policy</p>
						</div>
					</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/technology.jsp">
						<div class="headerTab">
							<p>Technology</p>
						</div>
					</a>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="toggle('world')">
						<div class="headerTab">
							<p>Realpolitik</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="world" style="display: none">
						<li>
							<a href="${pageContext.request.contextPath}/rankings.jsp">
								<div class="headerTabSmall">
									<p>World Rankings</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/map.jsp">
								<div class="headerTabSmall">
									<p>World Map</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/declarations.jsp">
								<div class="headerTabSmall">
									<p>Global Declarations</p>
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
					<a style="cursor: pointer;" onclick="toggle('user')">
						<div class="headerTab">
							<p>User</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
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