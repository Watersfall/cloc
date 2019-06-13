<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <c:set var="user">
        ${param['username']}
    </c:set>
    <c:set var="pass">
        <cloc:password value="${param['password']}"/>
    </c:set>
    <sql:query dataSource="${database}" var="check">
        SELECT * FROM cloc_login WHERE username=? AND password=?
        <sql:param value="${user}"/>
        <sql:param value="${pass}"/>
    </sql:query>
    <c:choose>
        <c:when test="${check.rowCount == 0}">
            <p><c:out value="Incorrect Username or Password!"/></p>
        </c:when>
        <c:otherwise>
            <p><c:out value="Logged in!"/></p>
            <sql:transaction dataSource="${database}">
                <sql:update>
                    UPDATE cloc_login SET sess=? WHERE username=? AND password=?
                    <sql:param value="${sess}"/>
                    <sql:param value="${user}"/>
                    <sql:param value="${pass}"/>
                </sql:update>
            </sql:transaction>
            <c:redirect url="/main"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>