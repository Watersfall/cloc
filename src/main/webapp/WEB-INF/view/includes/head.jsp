<!DOCTYPE html>
<head>
    <title>&ltCLOC - Online Nation Sim</title>
    <c:choose>
        <c:when test="${mobile == true}">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/nation.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/nation.css">
        </c:otherwise>
    </c:choose>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <meta name="description" content="Bad web game">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>