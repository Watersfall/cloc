<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div class="top" id="top">
	<c:choose>
		<c:when test="${sessionScope.user == null}">
			<div class="magic"></div>
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
					<a href="${pageContext.request.contextPath}/register/">
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
			<a href="${pageContext.request.contextPath}/main/">
				<img src="${pageContext.request.contextPath}/user/flag/<c:out value="${home.cosmetic.flag}"/>" alt="Flag">
			</a>
			<div class="magic"></div>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/news/">
						<div class="headerTab">
							<p>News</p>
							<div style="position: absolute; left: 0; top: 0; width: 100%;">
								<p style="visibility: hidden">NewsNews</p>
								<p class="newsBubble ${home.news.anyUnread ? 'unread' : 'read'}"><fmt:formatNumber value="${home.news.news.size()}"/></p>
							</div>
						</div>
					</a>
				</li>
			</ul>
			<ul>
				<li>
					<a style="cursor: pointer;" onclick="toggle('nation2')">
						<div class="headerTab">
							<p>Nation</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="nation2">
						<li>
							<a href="${pageContext.request.contextPath}/decisions/">
								<div class="headerTabSmall">
									<p>Decisions</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/policy/">
								<div class="headerTabSmall">
									<p>State Policy</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/technology/">
								<div class="headerTabSmall">
									<p>Technology</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/production/">
								<div class="headerTabSmall">
									<p>Production</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="toggle('cities')">
						<div class="headerTab">
							<p>Cities</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="cities">
						<c:forEach var="city" items="${home.cities.cities}">
							<li>
								<a href="${pageContext.request.contextPath}/cities/${city.value.id}/">
									<div class="headerTabSmall">
										<p><c:out value="${city.value.name}"/></p>
									</div>
								</a>
							</li>
						</c:forEach>
					</ul>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="toggle('world')">
						<div class="headerTab">
							<p>Realpolitik</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="world">
						<li>
							<a href="${pageContext.request.contextPath}/rankings/">
								<div class="headerTabSmall">
									<p>World Rankings</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/map/">
								<div class="headerTabSmall">
									<p>World Map</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/declarations/">
								<div class="headerTabSmall">
									<p>Global Declarations</p>
								</div>
							</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/worldnews/ongoing">
								<div class="headerTabSmall">
									<p>World News</p>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li>
					<c:if test="${home.treaty != null}">
						<a style="cursor: pointer;" onclick="toggle('treaties')">
							<div class="headerTab">
								<p>Treaties</p>
								<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
							</div>
						</a>
						<ul id="treaties">
							<li>
								<a href="${pageContext.request.contextPath}/treaty/${home.treaty.id}">
									<div class="headerTabSmall">
										<p>Your Treaty</p>
									</div>
								</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/treaties/">
									<div class="headerTabSmall">
										<p>Treaty Rankings</p>
									</div>
								</a>
							</li>
						</ul>
					</c:if>
					<c:if test="${home.treaty == null}">
						<a href="${pageContext.request.contextPath}/treaties/">
							<div class="headerTab">
								<p>Treaties</p>
							</div>
						</a>
					</c:if>
				</li>
				<li>
					<a style="cursor: pointer;" onclick="toggle('user')">
						<div class="headerTab">
							<p>User</p>
							<img src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						</div>
					</a>
					<ul id="user">
						<li class="headerTabSmall">
							<a href="${pageContext.request.contextPath}/settings/">
								<div class="headerTab">
									<p>Settings</p>
								</div>
							</a>
						</li>
						<li class="headerTabSmall">
							<a href="${pageContext.request.contextPath}/logout/">
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