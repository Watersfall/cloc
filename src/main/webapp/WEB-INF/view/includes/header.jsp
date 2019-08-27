<div class="top">
    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <div class="login">
                <form class="loginForm" action="${pageContext.request.contextPath}/login.do" method="POST">
                    <input class="loginText" type="text" name="username" placeholder="Username"><br>
                    <input class="loginText" type="password" name="password" placeholder="Password"><br>
                    <input class="loginText" type="submit" value="Login">
                </form>
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
            <div class="login">
                <div class="headerFlag">
                    <a href="${pageContext.request.contextPath}/main.jsp">
                        <img class="headerFlag" src="https://imgur.com/<c:out value="${home.cosmetic.flag}"/>" alt="Flag">
                    </a>
                    <h1 style="text-align: right;"><c:out value="${home.cosmetic.nationTitle}"/> of<br><c:out value="${home.cosmetic.nationName}"/></h1>
                </div>
                <ul>
                    <li>
                        <a style="cursor: pointer;" onclick="showHidePolicies()">
                            <div class="headerTab">
                                <p>Policies</p>
                            </div>
                        </a>
                        <ul id="policies" style="display: none">
                            <li>
                                <a href="/policies.jsp?policies=Economic">
                                    <div class="headerTab">
                                        <p>Economy</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/policies.jsp?policies=Domestic">
                                    <div class="headerTab">
                                        <p>Domestic</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/policies.jsp?policies=Foreign">
                                    <div class="headerTab">
                                        <p>Foreign</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/policies.jsp?policies=Military">
                                    <div class="headerTab">
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
                                    <div class="headerTab">
                                        <p>World Rankings</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}map.jsp">
                                    <div class="headerTab">
                                        <p>Regions</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/policies.jsp">
                            <div class="headerTab">
                                <p>Treaties</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/settings.jsp">
                            <div class="headerTab">
                                <p>Settings</p>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>
</div>