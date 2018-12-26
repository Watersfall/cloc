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
        <c:choose>
            <c:when test="${result.rowCount == 0}">
                <div class="login">
                    <form class="loginForm" action="login" method="POST">
                        <input class="loginText" type="text" name="username" placeholder="Username">
                        <input class="loginText" type="password" name="password" placeholder="Password">
                        <input type="submit" value="Login">
                    </form>
                    <form class="loginForm" action="index" method="GET">
                        <input type="submit" value="Home">
                    </form>
                </div>
                <div class="register">
                    <form class="registerForm" action="register">
                        <input type="submit" value="Register">
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="login">
                    <form class="loginForm" action="logout" method="GET">
                        <input type="submit" value="Logout">
                    </form>
                    <form class="registerForm" action="settings">
                        <input type="submit" value="Settings">
                    </form>
                    <form class="loginForm" action="main" method="GET">
                        <input type="submit" value="Home">
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>