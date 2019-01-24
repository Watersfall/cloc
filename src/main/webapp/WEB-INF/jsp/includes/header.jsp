<c:set var="user">
    ${param['username']}
</c:set>
<c:set var="nation">
    ${param['nation']}
</c:set>
<c:set var="pass">
    <cloc:password value="${param['password']}"/>
</c:set>
<div class="top">
    <c:choose>
        <c:when test="${result.rowCount == 0}">
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
                <a href="main">
                    <img class="headerFlag" src="https://imgur.com/<c:out value="${result.rows[0].flag}"/>" alt="Flag">
                </a>
                <h1 style="margin-top: 2%; margin-bottom:2%;">
                    <c:out value="${result.rows[0].nation}"/>
                </h1>
                <table style="width: 100%;">
                    <tr style="width: 100%;">
                        <td style="width: 50%;">
                            <a href="index">
                                <div class="world">
                                    <p>Communiques</p>
                                </div>
                            </a>
                        </td>
                        <td style="width: 50%;">
                            <a href="news">
                                <div class="world">
                                    <p>News</p>
                                </div>
                            </a>
                        </td>
                    </tr>
                </table>
                <h3 style="margin-top: 2%; margin-bottom:2%;">
                    Policies
                </h3>
                <a href="policies?policies=Domestic">
                    <div class="policy">
                        Domestic
                    </div>
                </a>
                <a href="policies?policies=Economic">
                    <div class="policy">
                        Economic
                    </div>
                </a>
                <a href="policies?policies=Foreign">
                    <div class="policy">
                        Foreign
                    </div>
                </a>
                <a href="policies?policies=Military">
                    <div class="policy">
                        Military
                    </div>
                </a>
                <table id="resources">
                    <tr>
                        <td>
                            <p class="left">Budget: </p>
                        </td>
                        <td>
                            <p class="right"><i>$<c:out value="${resultMain.rows[0].budget}"/>k</i></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p class="left">RM: </p>
                        </td>
                        <td>
                            <div class="resourceDropDown">
                                <span class="resourceDropDown-span">
                                    <p class="right"><i><c:out value="${resultMain.rows[0].rm}"/> Htons</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultMain.rows[0].mines}"/> Htons from Mines</p>
                                    <p class="negative">-<c:out value="${resultMain.rows[0].industry}"/> Htons from Factories</p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p class="left">Oil: </p>
                        </td>
                        <td>
                            <div class="resourceDropDown">
                                <span class="resourceDropDown-span">
                                    <p class="right"><i><c:out value="${resultMain.rows[0].oil}"/> Mmbls</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultMain.rows[0].wells}"/> Htons from Wells</p>
                                    <p class="negative">-<c:out value="${resultMain.rows[0].industry}"/> Htons from Factories</p>
                                </div>
                            </div>
                        </td>
                    <tr>
                        <td>
                            <p class="left">MG: </p>
                        </td>
                        <td>
                            <div class="resourceDropDown">
                                <span class="resourceDropDown-span">
                                    <p class="right"><i><c:out value="${resultMain.rows[0].mg}"/> Tons</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultMain.rows[0].industry}"/> Tons from Factories</p>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                <h3>The World</h3>
                <h3>The Alliances</h3>
                <a href="index">
                    <div class="world">
                        <p>World Rankings</p>
                    </div>
                </a>
                <a href="index">
                    <div class="world">
                        <p>Your Region</p>
                    </div>
                </a>
                <a href="index">
                    <div class="world">
                        <p>World Map</p>
                    </div>
                </a>
                <a href="index">
                    <div class="world">
                        <p>News and Statistics</p>
                    </div>
                </a>
                <h3>The Alliances</h3>
                <a href="index">
                    <div class="alliance">
                        <p>Alliance Rankings</p>
                    </div>
                </a>
                <a href="index">
                    <div class="alliance">
                        <p>Your Alliance</p>
                    </div>
                </a>
                <a href="index">
                    <div class="alliance">
                        <p>Alliance Chat</p>
                    </div>
                </a>
                <form class="registerForm" action="settings">
                    <input type="submit" value="Settings">
                </form>
                <form class="logout" action="logout" method="GET">
                    <input type="submit" value="Logout">
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>