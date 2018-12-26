<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cloc" uri="/WEB-INF/custom.tld" %>
<sql:setDataSource var = "database" driver = "com.mysql.jdbc.Driver" url = "jdbc:mysql://localhost/cloc" user = "root"  password = "***REMOVED***"/>
<% session.invalidate(); %>
<!DOCTYPE html>
<html>
    <head>
        <title>&ltCLOC - Online Nation Sim</title>
        <link rel="stylesheet" type="text/css" href="css/home.css">
        <meta name="description" content="Bad web game">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <%@ include file = "header.jsp" %>
        <div class="main">
            <p>Logged out!</p>
        </div>
        <c:redirect url="/index"/>
    </body>
</html>