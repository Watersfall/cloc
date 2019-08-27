<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <c:set var="username">
        ${param['username']}
    </c:set>
    <c:set var="pass">
        <cloc:password value="${param['password']}"/>
    </c:set>
    <sql:query dataSource="${database}" var="check">
        SELECT id FROM cloc_login WHERE username=? AND password=?
        <sql:param value="${username}"/>
        <sql:param value="${pass}"/>
    </sql:query>
    <c:choose>
        <c:when test="${check.rowCount == 0}">
            <p>Incorrect Username or Password!</p>
        </c:when>
        <c:otherwise>
            <p>Logged in!</p>
            <c:set var="user" value="${check.rows[0].id}" scope="session"/>
            <c:redirect url="/main"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>