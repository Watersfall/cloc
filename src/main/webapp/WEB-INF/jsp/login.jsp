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
        <meta name="description" content="Bad web game">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <%@ include file = "header.jsp" %>
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
                    <sql:update dataSource="${database}">
                        UPDATE cloc_main SET sess=? WHERE username=? AND password=?
                        <sql:param value="${sess}" />
                        <sql:param value="${user}" />
                        <sql:param value="${pass}" />
                    </sql:update>
                    <sql:update dataSource="${database}">
                        UPDATE cloc_domestic SET sess=? WHERE id=?
                        <sql:param value="${sess}" />
                        <sql:param value="${result.rows[0].id}" />
                    </sql:update>
                    <sql:update dataSource="${database}">
                        UPDATE cloc_economy SET sess=? WHERE id=?
                        <sql:param value="${sess}" />
                        <sql:param value="${result.rows[0].id}" />
                    </sql:update>
                    <sql:update dataSource="${database}">
                        UPDATE cloc_foreign SET sess=? WHERE id=?
                        <sql:param value="${sess}" />
                        <sql:param value="${result.rows[0].id}" />
                    </sql:update>
                    <sql:update dataSource="${database}">
                        UPDATE cloc_military SET sess=? WHERE id=?
                        <sql:param value="${sess}" />
                        <sql:param value="${result.rows[0].id}" />
                    </sql:update>
                    <c:redirect url="/main"/>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>