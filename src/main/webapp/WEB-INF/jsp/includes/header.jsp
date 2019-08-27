<div class="top">
    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <div class="login">
                <form class="loginForm" action="login" method="POST">
                    <input class="loginText" type="text" name="username" placeholder="Username"><br>
                    <input class="loginText" type="password" name="password" placeholder="Password"><br>
                    <input class="loginText" type="submit" value="Login">
                </form>
                <form class="loginForm" action="index" method="GET">
                    <input type="submit" value="Home">
                </form>
            </div>
            <div class="login">
                <form class="loginForm" action="register">
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
                    <a href="main">
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
                                <a href="policies?policies=Economic">
                                    <div class="headerTab">
                                        <p>Economy</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="policies?policies=Domestic">
                                    <div class="headerTab">
                                        <p>Domestic</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="policies?policies=Foreign">
                                    <div class="headerTab">
                                        <p>Foreign</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="policies?policies=Military">
                                    <div class="headerTab">
                                        <p>Military</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="decisions">
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
                                <a href="index">
                                    <div class="headerTab">
                                        <p>World Rankings</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="map">
                                    <div class="headerTab">
                                        <p>Regions</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="policies">
                            <div class="headerTab">
                                <p>Treaties</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="settings">
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