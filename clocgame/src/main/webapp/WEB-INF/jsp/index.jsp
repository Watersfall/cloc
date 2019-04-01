<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <h1 title="Title" id="top">Test</h1>
    <hr>
    <sql:query dataSource="${database}" var="results">
        SELECT * FROM cloc_main
    </sql:query>
    <table id="nation">
        <tr>
            <th>Flag</th>
            <th>Nation Name</th>
            <th>Leader Name</th>
        </tr>
        <c:forEach items="${results.rows}" var="row">
            <tr>
                <td><img class="indexflag" src="https://imgur.com/<c:out value="${row.flag}"/>" alt="flag"></td>
                <td><a href="nation?id=<c:out value="${row.id}"/>"><c:out value="${row.nation}"/></a></td>
                <td><c:out value="${row.username}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>