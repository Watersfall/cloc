<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <form method="POST" action="${pageContext.request.contextPath}/policies/createtreaty">
        <input type="text" name="name" maxlength="32">
        <button type="submit">Create</button>
    </form>
</div>
</body>
</html>