<c:set var="user">
    ${param['username']}
</c:set>
<c:set var="nation">
    ${param['nation']}
</c:set>
<c:set var="sess">
    ${pageContext.session.id}
</c:set>
<c:set var="pass">
    <cloc:password value="${param['password']}"/>
</c:set>
<div class="top">
    <sql:query dataSource="${database}" var="result">
        SELECT * FROM cloc_main WHERE sess=?
        <sql:param value="${sess}" />
    </sql:query>
    <sql:query dataSource="${database}" var="resultEconomy">
        SELECT * FROM cloc_economy WHERE sess=?
        <sql:param value="${sess}" />
    </sql:query>
    <sql:query dataSource="${database}" var="resultDomestic">
        SELECT * FROM cloc_domestic WHERE sess=?
        <sql:param value="${sess}" />
    </sql:query>
    <sql:query dataSource="${database}" var="resultForeign">
        SELECT * FROM cloc_foreign WHERE sess=?
        <sql:param value="${sess}" />
    </sql:query>
    <sql:query dataSource="${database}" var="resultMilitary">
        SELECT * FROM cloc_military WHERE sess=?
        <sql:param value="${sess}" />
    </sql:query>
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
        </c:when>
        <c:otherwise>
            <div class="login">
                <a href="main">
                    <img class="headerFlag" src="https://imgur.com/<c:out value="${result.rows[0].flag}"/>" alt="Flag">
                </a>
                <h1 style="margin-top: 2%; margin-bottom:2%;">
                    <c:out value="${result.rows[0].nation}"/>
                </h1>
                <h3 style="margin-top: 2%; margin-bottom:2%;">
                    Policies
                </h3>
                <a href="policies?policy=Domestic">
                    <div class="policy">
                        Domestic
                    </div>
                </a>
                <a href="policies?policy=Economic">
                    <div class="policy">
                        Economic
                    </div>
                </a>
                <a href="policies?policy=Foreign">
                    <div class="policy">
                        Foreign
                    </div>
                </a>
                <a href="policies?policy=Military">
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
                            <p class="right"><i>$<c:out value="${resultEconomy.rows[0].budget}"/>k</i></p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p class="left">RM: </p>
                        </td>
                        <td>
                            <div class="resourceDropDown">
                                <span class="resourceDropDown-span">
                                    <p class="right"><i><c:out value="${resultEconomy.rows[0].rm}"/> Htons</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultEconomy.rows[0].mines}"/> Htons from Mines</p>
                                    <p class="negative">-<c:out value="${resultEconomy.rows[0].industry}"/> Htons from Factories</p>
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
                                    <p class="right"><i><c:out value="${resultEconomy.rows[0].oil}"/> Mmbls</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultEconomy.rows[0].wells}"/> Htons from Wells</p>
                                    <p class="negative">-<c:out value="${resultEconomy.rows[0].industry}"/> Htons from Factories</p>
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
                                    <p class="right"><i><c:out value="${resultEconomy.rows[0].mg}"/> Tons</i></p>
                                </span>
                                <div class="resourceDropDown-content">
                                    <p class="positive">+<c:out value="${resultEconomy.rows[0].industry}"/> Tons from Factories</p>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
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