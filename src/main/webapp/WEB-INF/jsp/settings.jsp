<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <div class="main">
            <c:if test="${result.rowCount == 0}">
                <p>You must be logged in to view this page!</p>
            </c:if>
            <c:if test="${result.rowCount > 0}">
                <c:choose>
                    <c:when test="${not empty param['flag']}">
                        <c:if test="${fn:length(param['flag']) <= 12}">
                            <sql:transaction dataSource="${database}">
                                <sql:update>
                                    UPDATE cloc_main SET flag=? WHERE sess=?
                                    <sql:param value="${param['flag']}" />
                                    <sql:param value="${sess}" />
                                </sql:update>
                            </sql:transaction>
                            <p>Updated flag</p>
                        </c:if>
                        <c:if test="${fn:length(param['flag']) > 12}">
                            <p>Must be less than 12 characters!</p>
                        </c:if>
                    </c:when>
                    <c:when test="${not empty param['leader']}">
                        <c:if test="${fn:length(param['leader']) <= 12}">
                            <sql:transaction dataSource="${database}">
                                             <sql:update>
                                    UPDATE cloc_main SET leader=? WHERE sess=?
                                    <sql:param value="${param['leader']}" />
                                    <sql:param value="${sess}" />
                                </sql:update>
                            </sql:transaction>
                            <p>Updated leader portrait</p>
                        </c:if>
                        <c:if test="${fn:length(param['leader']) > 12}">
                            <p>Must be less than 12 characters!</p>
                        </c:if>
                    </c:when>
                    <c:when test="${not empty param['leadertitle']}">
                        <sql:transaction dataSource="${database}">
                            <sql:update>
                                UPDATE cloc_main SET leaderTitle=? WHERE sess=?
                                <sql:param value="${param['leadertitle']}" />
                                <sql:param value="${sess}" />
                            </sql:update>
                        </sql:transaction>
                        <p>Updated leader title</p>
                    </c:when>
                    <c:when test="${not empty param['nationtitle']}">
                        <sql:transaction dataSource="${database}">
                            <sql:update>
                                UPDATE cloc_main SET nationTitle=? WHERE sess=?
                                <sql:param value="${param['nationtitle']}" />
                                <sql:param value="${sess}" />
                            </sql:update>
                        </sql:transaction>
                        <p>Updated nation title</p>
                    </c:when>
                    <c:when test="${not empty param['description']}">
                        <sql:transaction dataSource="${database}">
                            <sql:update>
                                UPDATE cloc_main SET description=? WHERE sess=?
                                <sql:param value="${param['description']}" />
                                <sql:param value="${sess}" />
                            </sql:update>
                        </sql:transaction>
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
