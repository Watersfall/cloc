<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <div class="main">
            <form class="registerForm" action="register" method="POST">
                <input class="loginText" type="text" name="username" placeholder="Username">
                <input class="loginText" type="text" name="nation" placeholder="Nation Name">
                <input class="loginText" type="password" name="password" placeholder="Password">
                <input type="submit" value="register">
            </form>
            <sql:query dataSource="${database}" var="usernameResult">
                SELECT * FROM cloc_main WHERE username=?
                <sql:param value="${user}" />
            </sql:query>
            <sql:query dataSource="${database}" var="nationResult">
                SELECT * FROM cloc_main WHERE nation=?
                <sql:param value="${nation}" />
            </sql:query>
            <c:if test="${pageContext.request.method== 'POST'}">
                <c:choose>
                    <c:when test="${empty user || empty pass}">
                        <p>Please enter a Username and Password</p>
                    </c:when>
                    <c:when test="${fn:length(user) > 16}">
                        <p>Username must be 16 characters or less!</p>
                    </c:when>
                    <c:when test="${fn:length(nation) > 24}">
                        <p>Nation name must be 24 characters or less!</p>
                    </c:when>
                    <c:when test="${usernameResult.rowCount != 0}">
                        <p>Duplicate Username!</p>
                    </c:when>
                    <c:when test="${nationResult.rowCount != 0}">
                        <p>Duplicate Nation Name!</p>
                    </c:when>
                    <c:otherwise>
                        <p>Registered!</p>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc_main (username, nation, password, sess) VALUES (?,?,?,?)
                            <sql:param value="${user}" />
                            <sql:param value="${nation}" />
                            <sql:param value="${pass}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc (sess) VALUES (?);
                            <sql:param value="${sess}" />
                        </sql:update>
                        <c:redirect url="main"/>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </body>
</html>