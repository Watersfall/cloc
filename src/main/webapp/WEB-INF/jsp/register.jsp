<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
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
            <option value="Siberia">Siberia</option>
        </select>
        <br>
        <h3 class="RegisterText">Government</h3>
        <select name="government" class="RegisterText">
            <option value="10">Absolute Monarch</option>
            <option value="30">Military Dictatorship</option>
            <option value="50">Constitutional Monarchy</option>
            <option value="70">Federal Republic</option>
            <option value="90">Direct Democracy</option>
        </select>
        <br>
        <h3 class="RegisterText">Economy</h3>
        <select name="economy" class="RegisterText">
            <option value="25">Communist</option>
            <option value="50">State Capitalist</option>
            <option value="75">Free Market</option>
        </select>
        <br>
        <input type="submit" value="Register!" class="policyButton">
    </form>
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
            <c:set var="user">
                ${param['username']}
            </c:set>
            <c:set var="nation">
                ${param['nation']}
            </c:set>
            <c:set var="pass">
                <cloc:password value="${param['password']}"/>
            </c:set>
        </c:catch>
        <sql:query dataSource="${database}" var="usernameResult">
            SELECT * FROM cloc_login WHERE username=?
            <sql:param value="${user}"/>
        </sql:query>
        <sql:query dataSource="${database}" var="nationResult">
            SELECT * FROM cloc_cosmetic WHERE nation_name=?
            <sql:param value="${nation}"/>
        </sql:query>
        <c:choose>
            <c:when test="${empty user || empty pass || empty nation}">
                <p>Please enter a Username, Password, and Nation Name</p>
            </c:when>
            <c:when test="${fn:length(user) > 32}">
                <p>Username must be 32 characters or less!</p>
            </c:when>
            <c:when test="${fn:length(nation) > 32}">
                <p>Nation name must be 32 characters or less!</p>
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
                        INSERT INTO cloc_login (username, password, sess) VALUES (?,?,?);
                        <sql:param value="${user}"/>
                        <sql:param value="${pass}"/>
                        <sql:param value="${sess}"/>
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_cosmetic (nation_name, username, description) VALUES (?,?,?);
                        <sql:param value="${nation}"/>
                        <sql:param value="${user}"/>
                        <sql:param value="Welcome to CLOC! Please change me in the Settings!"/>
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_economy (economic) VALUES (?);
                        <sql:param value="${economy}"/>
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_domestic (government) VALUES (?);
                        <sql:param value="${government}"/>
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_military () VALUES ();
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_foreign (region) VALUES (?);
                        <sql:param value="${region}"/>
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_tech () VALUES ();
                    </sql:update>
                    <sql:update>
                        INSERT INTO cloc_policy () VALUES ();
                    </sql:update>
                </sql:transaction>
                <c:redirect url="main"/>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
</body>
</html>