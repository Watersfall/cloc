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
                    <c:when test="${empty user && empty pass}">
                        <p>Please enter a Username and Password</p>
                    </c:when>
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
                            INSERT INTO cloc_main (username, nation, password, leader, flag, leaderTitle, nationTitle, description, sess) VALUES (?,?,?,?,?,?,?,?,?)
                            <sql:param value="${user}" />
                            <sql:param value="${nation}" />
                            <sql:param value="${pass}" />
                            <sql:param value="phcFTw1.png" />
                            <sql:param value="zb2vDeE.jpg" />
                            <sql:param value="President" />
                            <sql:param value="Republic of" />
                            <sql:param value=" " />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc_domestic (approval, political, stability, land, rebel, population, qol, literacy, healthcare, universities, sess) VALUES (?,?,?,?,?,?,?,?,?,?,?)
                            <sql:param value="50" />
                            <sql:param value="50" />
                            <sql:param value="50" />
                            <sql:param value="20000" />
                            <sql:param value="0" />
                            <sql:param value="50" />
                            <sql:param value="50" />
                            <sql:param value="50" />
                            <sql:param value="50" />
                            <sql:param value="0" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc_economy (economic, budget, gdp, growth, fi, mg, rm, oil, reserves, industry, mines, wells, food, uranium, umines, sess) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                            <sql:param value="50" />
                            <sql:param value="1000" />
                            <sql:param value="300" />
                            <sql:param value="5" />
                            <sql:param value="0" />
                            <sql:param value="1" />
                            <sql:param value="25" />
                            <sql:param value="20" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="2" />
                            <sql:param value="0" />
                            <sql:param value="100" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc_foreign(alignment, soviet, us, region, alliance, votes, voting, repuation, sess) VALUES (?,?,?,?,?,?,?,?,?) 
                            <sql:param value="50" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="50" />
                            <sql:param value="${sess}" />
                        </sql:update>
                        <sql:update dataSource="${database}">
                            INSERT INTO cloc_military (army, manpower, training, tech, airforce, navy, chems, nukes, godrods, sess) VALUES (?,?,?,?,?,?,?,?,?,?);
                            <sql:param value="20" />
                            <sql:param value="100" />
                            <sql:param value="50" />
                            <sql:param value="10" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="0" />
                            <sql:param value="${sess}" />
                        </sql:update>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </body>
</html>