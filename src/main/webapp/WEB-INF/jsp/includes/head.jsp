<head>
    <title>&ltCLOC - Online Nation Sim</title>
    <c:choose>
        <c:when test="${mobile == true}">
            <link rel="stylesheet" type="text/css" href="css/home_m.css">
            <link rel="stylesheet" type="text/css" href="css/nation_m.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="css/home.css">
            <link rel="stylesheet" type="text/css" href="css/nation.css">
        </c:otherwise>
    </c:choose>
    <meta name="description" content="Bad web game">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>