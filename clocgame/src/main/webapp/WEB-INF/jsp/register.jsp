<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <div class="main">
            <form class="registerForm" action="register" method="POST">
                <h3 class="RegisterText">Username</h3>
                <input class="RegisterText" type="text" name="username" placeholder="Username">
                <br>
                <h3 class="RegisterText">Nation Name</h3>
                <input class="RegisterText" type="text" name="nation" placeholder="Nation Name">
                <br>
                <h3 class="RegisterText">Password</h3>
                <input class="RegisterText" type="password" name="password" placeholder="Password">
                <br>
                <h3 class="RegisterText">Region</h3>
                <select name="region" class="RegisterText">
                    <option value="North America">North America</option>
                    <option value="South America">South America</option>
                    <option value="Europe">Europe</option>
                    <option value="Africa">Africa</option>
                    <option value="Middle East">Middle East</option>
                    <option value="Asia">Asia</option>
                    <option value="Oceania">Oceania</option>
                </select>
                <br>
                <h3 class="RegisterText">Government</h3>
                <select name="government" class="RegisterText">
                    <option value="0">Absolute Monarch</option>
                    <option value="25">Military Dictatorship</option>
                    <option value="50">Constitutional Monarchy</option>
                    <option value="75">Federal Republic</option>
                    <option value="100">Direct Democracy</option>
                </select>
                <br>
                <h3 class="RegisterText">Economy</h3>
                <select name="economy" class="RegisterText">
                    <option value="0">Communist</option>
                    <option value="50">State Capitalist</option>
                    <option value="100">Free Market</option>
                </select>
                <br>
                <input type="submit" value="Register!" class="policyButton">
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
                <c:catch var="error">
                    <c:set var="region" value="${param['region']}"/>
                    <c:if test="${empty region || (region != 'North America' && region != 'South America' && region != 'Africa' && region != 'Middle East' && region != 'Europe' && region != 'Asia' && region != 'Oceania')}">
                        <c:set var="region" value="Siberia"/>
                    </c:if>
                    <c:set var="government" value="${param['government']}"/>
                    <c:if test="${empty government || government > 100 || government < 0}">
                        <c:set var="government" value="50"/>
                    </c:if>
                    <c:set var="economy" value="${param['economy']}"/>
                    <c:if test="${empty economy || economy > 100 || economy < 0}">
                        <c:set var="economy" value="50"/>
                    </c:if>
                </c:catch>
                <c:choose>
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
                    <c:when test="${error != null}">
                        <p>Don't do that please</p>
                    </c:when>
                    <c:otherwise>
                        <p>Registered!</p>
                        <sql:transaction dataSource="${database}">
                            <sql:update>
                                INSERT INTO cloc_main (username, nation, password, sess) VALUES (?,?,?,?)
                                <sql:param value="${user}" />
                                <sql:param value="${nation}" />
                                <sql:param value="${pass}" />
                                <sql:param value="${sess}" />
                            </sql:update>
                            <sql:update>
                                INSERT INTO cloc (sess, political, economic, region) VALUES (?,?,?,?);
                                <sql:param value="${sess}" />
                                <sql:param value="${government}" />
                                <sql:param value="${economy}" />
                                <sql:param value="${region}" />
                            </sql:update>
                            <sql:update>
                                INSERT INTO cloc_population (sess, asian) VALUES (?,?);
                                <sql:param value="${sess}" />
                                <sql:param value="100000" />
                            </sql:update>
                        </sql:transaction>
                        <c:redirect url="main"/>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </body>
</html>