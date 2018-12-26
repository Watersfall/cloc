<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloc" uri="/WEB-INF/custom.tld" %>
<sql:setDataSource var = "database" driver = "com.mysql.jdbc.Driver" url = "jdbc:mysql://localhost/cloc" user = "root"  password = "***REMOVED***"/>
<!DOCTYPE html>
<html>
    <head>
        <title>&ltCLOC - Online Nation Sim</title>
        <link rel="stylesheet" type="text/css" href="css/home.css">
        <link rel="stylesheet" type="text/css" href="css/nation.css">
        <meta name="description" content="Bad web game">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <%@ include file = "header.jsp" %>
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
                        <td><a href="/nation?id=<c:out value="${row.id}"/>"><c:out value="${row.nation}"/></a></td>
                        <td><c:out value="${row.username}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>