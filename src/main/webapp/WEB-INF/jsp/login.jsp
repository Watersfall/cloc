<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <div class="main">
            <sql:query dataSource="${database}" var="result">
                SELECT * FROM cloc_main WHERE username=? AND password=?
                <sql:param value="${user}" />
                <sql:param value="${pass}" />
            </sql:query>
            <c:choose>
                <c:when test="${result.rowCount == 0}">
                    <p><c:out value="Incorrect Username or Password!"/></p>
                </c:when>
                <c:otherwise>
                    <p><c:out value="Logged in!"/></p>
                    <sql:transaction dataSource="${database}">
                        <sql:update>
                            UPDATE cloc_main SET sess=? WHERE username=? AND password=?
                            <sql:param value="${sess}" />
                            <sql:param value="${user}" />
                            <sql:param value="${pass}" />
                        </sql:update>
                        <sql:update>
                            UPDATE cloc SET sess=? WHERE id=?
                            <sql:param value="${sess}" />
                            <sql:param value="${result.rows[0].id}" />
                        </sql:update>
                        <sql:update>
                            UPDATE cloc_population SET sess=? WHERE id=?
                            <sql:param value="${sess}" />
                            <sql:param value="${result.rows[0].id}" />
                        </sql:update>
                    </sql:transaction>
                    <c:redirect url="/main"/>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>