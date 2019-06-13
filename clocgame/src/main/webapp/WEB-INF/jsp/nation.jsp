<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="js/nation.js"></script>
<c:set var="nation" value="${param['id']}"/>
<sql:query dataSource="${database}" var="nation_cosmetic" scope="page">
    SELECT * FROM cloc_cosmetic WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_economy" scope="page">
    SELECT * FROM cloc_economy WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_domestic" scope="page">
    SELECT * FROM cloc_domestic WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_military" scope="page">
    SELECT * FROM cloc_military WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_armies" scope="page">
    SELECT * FROM cloc_armies WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_foreign" scope="page">
    SELECT * FROM cloc_foreign WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_tech" scope="page">
    SELECT * FROM cloc_tech WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<sql:query dataSource="${database}" var="nation_policy" scope="page">
    SELECT * FROM cloc_policy WHERE id=?
    <sql:param value="${nation}"/>
</sql:query>
<c:set var="nation_map" value="${nation_economy.rows[0]}"/>
${nation_map.putAll(nation_domestic.rows[0])}
${nation_map.putAll(nation_military.rows[0])}
<div class="main">
    <%@ include file="includes/results.jsp" %>
    <c:choose>
        <c:when test="${empty nation}">
            <p>You have visited this page incorrectly!</p>
        </c:when>
        <c:when test="${nation_cosmetic.rowCount == 0}">
            <p>Nation does not exist!</p>
        </c:when>
        <c:when test="${id != -1 && nation_cosmetic.rows[0].id == check.rows[0].id}">
            <c:redirect url="/main"/>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty send}">
                <p><c:out value="${send}"/></p>
            </c:if>
            <div class="nation">
                <h1><c:out value="${nation_cosmetic.rows[0].nationTitle}"/><br><c:out value="${nation_cosmetic.rows[0].nation}"/></h1>
                <img class="flag" src="https://imgur.com/<c:out value="${nation_cosmetic.rows[0].flag}"/>" alt="flag">
                <p><c:out value="${nation_cosmetic.rows[0].description}"/></p>
                <img class="leader" src="https://imgur.com/<c:out value="${nation_cosmetic.rows[0].leader}"/>" alt="flag">
                <h1><c:out value="${nation_cosmetic.rows[0].leaderTitle}"/>: <c:out value="${nation_cosmetic.rows[0].username}"/></h1>
            </div>
            <h1>Government</h1>
            <table id="nation">
                <tr>
                    <td>Approval</td>
                    <td><cloc:approval value="${nation_domestic.rows[0].approval}"/></td>
                </tr>
                <tr>
                    <td>Government</td>
                    <td><cloc:government value="${nation_domestic.rows[0].government}"/></td>
                </tr>
                <tr>
                    <td>Stability</td>
                    <td><cloc:stability value="${nation_domestic.rows[0].stability}"/></td>
                </tr>
                <tr>
                    <td>Land</td>
                    <td><c:out value="${nation_domestic.rows[0].land}"/> km<sup>2</sup></td>
                </tr>
                <tr>
                    <td>Rebel Threat</td>
                    <td><cloc:rebels value="${nation_domestic.rows[0].rebels}"/></td>
                </tr>
            </table>
            <h1>Domestic</h1>
            <table id="nation">
                <tr>
                    <td>Population</td>
                    <td><c:out value="${nation_domestic.rows[0].asian}"/> People</td>
                </tr>
                <tr>
                    <td>Quality of Life</td>
                    <td><c:out value="${nation_domestic.rows[0].qol}"/>%</td>
                </tr>
                <tr>
                    <td>Healthcare</td>
                    <td><c:out value="${nation_domestic.rows[0].healthcare}"/>%</td>
                </tr>
                <tr>
                    <td>Literacy</td>
                    <td><c:out value="${nation_domestic.rows[0].literacy}"/>%</td>
                </tr>
                <tr>
                    <td>Universities</td>
                    <td><c:out value="${nation_economy.rows[0].universities}"/> Universities</td>
                </tr>
            </table>
            <h1>Economy</h1>
            <table id="nation">
                <tr>
                    <td>Economic System</td>
                    <td><cloc:economic value="${nation_economy.rows[0].economic}"/></td>
                </tr>
                <tr>
                    <td>Gross Domestic Product</td>
                    <td>$<c:out value="${nation_economy.rows[0].gdp}"/> Million</td>
                </tr>
                <tr>
                    <td>Growth</td>
                    <td>$<c:out value="${nation_economy.rows[0].growth}"/> Million per Month</td>
                </tr>
                <tr>
                    <td>Industry</td>
                    <td><c:out value="${nation_economy.rows[0].industry}"/> Factories</td>
                <tr>
                    <td>Discovered Oil Reserves</td>
                    <td><c:out value="${nation_economy.rows[0].reserves}"/> Mmbls</td>
                </tr>
                <tr>
                    <td>Oil Production</td>
                    <td><c:out value="${nation_economy.rows[0].wells}"/> Mmbls per Month</td>
                </tr>
                <tr>
                    <td>Raw Material Production</td>
                    <td><c:out value="${nation_economy.rows[0].mines}"/> Hundred Tons per Month</td>
                </tr>
            </table>
            <h1>Foreign</h1>
            <table id="nation">
                <tr>
                    <td>Region</td>
                    <td><c:out value="${nation_foreign.rows[0].region}"/></td>
                </tr>
                <tr>
                    <td>Alliance</td>
                    <td><c:out value="${nation_foreign.rows[0].alliance}"/></td>
                </tr>
            </table>
            <h1>Military</h1>
            <table id="nation">
                <tr>
                    <td>Army Size</td>
                    <td><c:out value="${nation_military.rows[0].army}"/>k Troops</td>
                </tr>
                <tr>
                    <td>Manpower</td>
                    <td><cloc:manpower main="${nation_map}"/></td>
                </tr>
                <tr>
                    <td>Equipment</td>
                    <td><c:out value="${nation_military.rows[0].tech}"/> Guns</td>
                </tr>
                <tr>
                    <td>Training</td>
                    <td><c:out value="${nation_military.rows[0].training}"/>/100</td>
                </tr>
                <tr>
                    <td>Airforce</td>
                    <td><c:out value="${nation_military.rows[0].airforce}"/> Planes</td>
                </tr>
                <tr>
                    <td>Navy</td>
                    <td><c:out value="${nation_military.rows[0].navy}"/> Ships</td>
                </tr>
            </table>
            <h1>Diplomacy</h1>
            <table id="nation">
                <tr>
                    <td>Send Money</td>
                    <td>
                        <input type="number" id="amountCash" name="sendcash">
                        <button type="submit" onclick="sendCash(document.getElementById('amountCash').value, <c:out
                                value="${nation}"/>);">Send
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>Send Raw Material</td>
                    <td>
                        <input type="number" id="amountRm" name="sendrm">
                        <button type="submit"
                                onclick="sendRm(document.getElementById('amountRm').value, <c:out value="${nation}"/>);">
                            Send
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>Send Oil</td>
                    <td>
                        <input type="number" id="amountOil" name="sendoil">
                        <button type="submit"
                                onclick="sendOil(document.getElementById('amountOil').value, <c:out value="${nation}"/>);">
                            Send
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>Send Manufactured Goods</td>
                    <td>
                        <input type="number" id="amountMg" name="sendmg">
                        <button type="submit"
                                onclick="sendMg(document.getElementById('amountMg').value, <c:out value="${nation}"/>);">
                            Send
                        </button>
                    </td>
                </tr>
            </table>
        </c:otherwise>
    </c:choose>
    <p><br><br><br></p>
</div>
<p><br><br><br></p>
</body>
</html>