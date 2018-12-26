<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <c:if test="${result.rowCount == 0}">
                <p>You must be logged in to view this page!</p>
            </c:if>
            <c:if test="${result.rowCount > 0}">
                <c:choose>
                    <c:when test="${not empty param['flag']}">
                        <sql:update dataSource="${database}">
                            UPDATE cloc_main SET flag=? WHERE sess=?
                            <sql:param value="${param['flag']}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <p>Updated flag</p>
                    </c:when>
                    <c:when test="${not empty param['leader']}">
                        <sql:update dataSource="${database}">
                            UPDATE cloc_main SET leader=? WHERE sess=?
                            <sql:param value="${param['leader']}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <p>Updated portrait</p>
                    </c:when>
                    <c:when test="${not empty param['leadertitle']}">
                        <sql:update dataSource="${database}">
                            UPDATE cloc_main SET leaderTitle=? WHERE sess=?
                            <sql:param value="${param['leadertitle']}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <p>Updated leader title</p>
                    </c:when>
                    <c:when test="${not empty param['nationtitle']}">
                        <sql:update dataSource="${database}">
                            UPDATE cloc_main SET nationTitle=? WHERE sess=?
                            <sql:param value="${param['nationtitle']}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <p>Updated nation title</p>
                    </c:when>
                    <c:when test="${not empty param['description']}">
                        <sql:update dataSource="${database}">
                            UPDATE cloc_main SET description=? WHERE sess=?
                            <sql:param value="${param['description']}" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <p>Updated description</p>
                    </c:when>
                </c:choose>
                <div class="settings">
                    <p>Update flag</p>
                    <form action="settings" method="POST">
                        <input class="loginText" type="text" name="flag" placeholder="Imgur image code">
                        <input type="submit" value="Update">
                    </form>
                    <p>Update leader portrait</p>
                    <form action="settings" method="POST">
                        <input class="loginText" type="text" name="leader" placeholder="Imgur image code">
                        <input type="submit" value="Update">
                    </form>
                    <p>Leader title</p>
                    <form action="settings" method="POST">
                        <input class="loginText" type="text" name="leadertitle" placeholder="Title">
                        <input type="submit" value="Update">
                    </form>
                    <p>Nation title</p>
                    <form action="settings" method="POST">
                        <input class="loginText" type="text" name="nationtitle" placeholder="Title">
                        <input type="submit" value="Update">
                    </form>
                    <p>Nation description</p>
                    <form action="settings" method="POST">
                        <input class="loginText" type="text" name="description" placeholder="Description">
                        <input type="submit" value="Update">
                    </form>
                </div>
            </c:if>
        </div>
    </body>
</html>
