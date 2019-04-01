<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <c:choose>
        <c:when test="${result.rowCount == 0}">
            <p>You must be logged in to view this page</p>
        </c:when>
        <c:otherwise>
            <sql:query dataSource="${database}" var="news">
                SELECT * FROM news WHERE receiver=?
                <sql:param value="${result.rows[0].id}"/>
            </sql:query>
            <c:forEach var="i" items="${news.rows}">
                <tr><cloc:eventText value="${i}"/></tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>