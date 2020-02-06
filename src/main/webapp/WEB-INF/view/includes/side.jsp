<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="header">
	<c:choose>
		<c:when test="${sessionScope.user == null}">
			<a href="${pageContext.request.contextPath}/">
				<div>
					<h1>&ltCLOC</h1>
				</div>
			</a>
			<a>
				<div>
					<p>Login</p>
					<label>
						<input id="username" class="loginText" type="text" name="username" placeholder="Username">
					</label><br>
					<label>
						<input id="password" class="loginText" type="password" name="password" placeholder="Password">
					</label><br>
					<button onclick="login(document.getElementById('username').value, document.getElementById('password').value);">Log in</button>
				</div>
			</a>
			<a href="${pageContext.request.contextPath}/register/">
				<div>
					<p>Register</p>
				</div>
			</a>
			<a href="https://discord.gg/x2VwYkS">
				<div>
					<p>Discord</p>
				</div>
			</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/main/">
				<img src="${pageContext.request.contextPath}/user/flag/${home.cosmetic.flag}" alt="flag">
			</a>
			<a href="${pageContext.request.contextPath}/news/">
				<div>
					<p>News</p>
					<p class="newsBubble ${home.news.anyUnread ? 'unread' : 'read'}">
						<fmt:formatNumber value="${home.news.news.size()}"/>
					</p>
				</div>
			</a>
			<a onclick="toggle('nation');">
				<div>
					<p>Nation</p>
					<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
				</div>
				<div id="nation" class="sub toggleable">
					<a href="${pageContext.request.contextPath}/policy/">
						<div>
							<p>Policies</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/decisions/">
						<div>
							<p>Decisions</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/production/">
						<div>
							<p>Production</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/technology/">
						<div>
							<p>Technology</p>
						</div>
					</a>
				</div>
			</a>
			<a onclick="toggle('cities');">
				<div>
					<p>Cities</p>
					<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
				</div>
				<div id="cities" class="sub toggleable">
					<c:forEach items="${home.cities.cities.values()}" var="city">
						<a href="${pageContext.request.contextPath}/cities/${city.id}">
							<div>
								<p><c:out value="${city.name}"/></p>
							</div>
						</a>
					</c:forEach>
				</div>
			</a>
			<a onclick="toggle('realpolitik');">
				<div>
					<p>Realpolitik</p>
					<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
				</div>
				<div id="realpolitik" class="sub toggleable">
					<a href="${pageContext.request.contextPath}/rankings/">
						<div>
							<p>World Rankings</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/map/">
						<div>
							<p>World Map</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/declarations/">
						<div>
							<p>Global Declarations</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/worldnews//">
						<div>
							<p>World News</p>
						</div>
					</a>
				</div>
			</a>
			<a onclick="toggle('treaties')">
				<div>
					<p>Treaties</p>
					<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
				</div>
				<div id="treaties" class="sub toggleable">
					<c:if test="${home.treaty != null}">
						<a href="${pageContext.request.contextPath}/treaty/${home.treaty.id}">
							<div>
								<p>Your Treaty</p>
							</div>
						</a>
					</c:if>
					<a href="${pageContext.request.contextPath}/treaties/">
						<div>
							<p>Treaty Rankings</p>
						</div>
					</a>
				</div>
			</a>
			<a onclick="toggle('user');">
				<div>
					<p>User</p>
					<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
				</div>
				<div id="user" class="sub toggleable">
					<a href="${pageContext.request.contextPath}/settings/">
						<div>
							<p>Settings</p>
						</div>
					</a>
					<a href="${pageContext.request.contextPath}/logout/">
						<div>
							<p>Logout</p>
						</div>
					</a>
				</div>
			</a>
		</c:otherwise>
	</c:choose>
</div>