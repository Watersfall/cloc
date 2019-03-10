<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <script src="/js/nation.js"></script>
        <c:set var="id" value="${param['id']}"/>
        <sql:query dataSource="${database}" var="nation">
            SELECT * FROM cloc_main WHERE id=?
            <sql:param value="${id}" />
        </sql:query>
        <sql:query dataSource="${database}" var="nation2">
            SELECT * FROM cloc WHERE id=?
            <sql:param value="${id}" />
        </sql:query>
        <sql:query dataSource="${database}" var="nation3">
            SELECT * FROM cloc_population WHERE id=?
            <sql:param value="${id}" />
        </sql:query>
        <div class="main">
            <%@ include file = "includes/results.jsp" %>
            <c:choose>
                <c:when test="${empty id}">
                    <p>You have visited this page incorrectly!</p>
                </c:when>
                <c:when test="${nation.rowCount == 0}">
                    <p>Nation does not exist!</p>
                </c:when>
                <c:when test="${result.rowCount != 0 && nation.rows[0].id == result.rows[0].id}">
                    <c:redirect url="/main"/>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty send}">
                        <p><c:out value="${send}"/></p>
                    </c:if>
                    <div class="nation">
                        <h1><c:out value="${nation.rows[0].nationTitle}"/><br><c:out value="${nation.rows[0].nation}"/></h1>
                        <img class="flag" src="https://imgur.com/<c:out value="${nation.rows[0].flag}"/>" alt="flag">
                        <p><c:out value="${nation.rows[0].description}"/></p>
                        <img class="leader" src="https://imgur.com/<c:out value="${nation.rows[0].leader}"/>" alt="flag">
                        <h1><c:out value="${nation.rows[0].leaderTitle}"/>: <c:out value="${nation.rows[0].username}"/></h1>
                    </div>
                    <h1>Government</h1>
                    <table id="nation">
                        <tr>
                            <td>Approval</td>
                            <td><cloc:approval value="${nation2.rows[0].approval}"/></td>
                        </tr>
                        <tr>
                            <td>Government</td>
                            <td><cloc:government value="${nation2.rows[0].political}"/></td>
                        </tr>
                        <tr>
                            <td>Stability</td>
                            <td><cloc:stability value="${nation2.rows[0].stability}"/></td>
                        </tr>
                        <tr>
                            <td>Land</td>
                            <td><c:out value="${nation2.rows[0].land}"/> km<sup>2</sup></td>
                        </tr>
                        <tr>
                            <td>Rebel Threat</td>
                            <td><cloc:rebels value="${nation2.rows[0].rebel}"/></td>
                        </tr>
                    </table>
                    <h1>Domestic</h1>
                    <table id="nation">
                        <tr>
                            <td>Population</td>
                            <td><c:out value="${nation3.rows[0].asian}"/> People</td>
                        </tr>
                        <tr>
                            <td>Quality of Life</td>
                            <td><c:out value="${nation2.rows[0].qol}"/>%</td>
                        </tr>
                        <tr>
                            <td>Healthcare</td>
                            <td><c:out value="${nation2.rows[0].healthcare}"/>%</td>
                        </tr>
                        <tr>
                            <td>Literacy</td>
                            <td><c:out value="${nation2.rows[0].literacy}"/>%</td>
                        </tr>
                        <tr>
                            <td>Universities</td>
                            <td><c:out value="${nation2.rows[0].universities}"/> Universities</td>
                        </tr>
                    </table>
                    <h1>Economy</h1>
                    <table id="nation">
                        <tr>
                            <td>Economic System</td>
                            <td><cloc:economic value="${nation2.rows[0].economic}"/></td>
                        </tr>
                        <tr>
                            <td>Gross Domestic Product</td>
                            <td>$<c:out value="${nation2.rows[0].gdp}"/> Million</td>
                        </tr>
                        <tr>
                            <td>Growth</td>
                            <td>$<c:out value="${nation2.rows[0].growth}"/> Million per Month</td>
                        </tr>
                        <tr>
                            <td>Industry</td>
                            <td><c:out value="${nation2.rows[0].industry}"/> Factories</td>
                        <tr>
                            <td>Discovered Oil Reserves</td>
                            <td><c:out value="${nation2.rows[0].reserves}"/> Mmbls</td>
                        </tr>
                        <tr>
                            <td>Oil Production</td>
                            <td><c:out value="${nation2.rows[0].wells}"/> Mmbls per Month</td>
                        </tr>
                        <tr>
                            <td>Raw Material Production</td>
                            <td><c:out value="${nation2.rows[0].mines}"/> Hundred Tons per Month</td>
                        </tr>
                        <tr>
                            <td>Nitrogen Plants</td>
                            <td><c:out value="${nation2.rows[0].nitrogenplants}"/> Plants</td>
                        </tr>
                    </table>
                    <h1>Foreign</h1>
                    <table id="nation">
                        <tr>
                            <td>Official Alignment</td>
                            <td><cloc:alignment value="${nation2.rows[0].alignment}"/></td>
                        </tr>
                        <tr>
                            <td>Region</td>
                            <td><c:out value="${nation2.rows[0].region}"/></td>
                        </tr>
                        <tr>
                            <td>Alliance</td>
                            <td><c:out value="${nation2.rows[0].alliance}"/></td>
                        </tr>
                        <tr>
                            <td>Alliance Votes Received</td>
                            <td><c:out value="${nation2.rows[0].votes}"/></td>
                        </tr>
                        <tr>
                            <td>Voting For</td>
                            <td><c:out value="${nation2.rows[0].voting}"/></td>
                        </tr>
                        <tr>
                            <td>Reputation</td>
                            <td><c:out value="${nation2.rows[0].reputation}"/></td>
                        </tr>
                    </table>
                    <h1>Military</h1>
                    <table id="nation">
                        <tr>
                            <td>Army Size</td>
                            <td><c:out value="${nation2.rows[0].army}"/>k Troops</td>
                        </tr>
                        <tr>
                            <td>Manpower</td>
                            <td><c:out value="${nation2.rows[0].manpower}"/>k/100k Manpower</td>
                        </tr>
                        <tr>
                            <td>Equipment</td>
                            <td><c:out value="${nation2.rows[0].tech}"/> Guns</td>
                        </tr>
                        <tr>
                            <td>Progress to the Next Level</td>
                            <c:set var="progress">
                                0%
                            </c:set>
                            <td><c:out value="${progress}"/></td>
                        <tr>
                        <tr>
                            <td>Training</td>
                            <td><c:out value="${nation2.rows[0].training}"/>/100</td>
                        </tr>
                        <tr>
                            <td>Airforce</td>
                            <td><c:out value="${nation2.rows[0].airforce}"/> Planes</td>
                        </tr>
                        <tr>
                            <td>Navy</td>
                            <td><c:out value="${nation2.rows[0].navy}"/> Ships</td>
                        </tr>
                    </table>
                    <h1>Diplomacy</h1>
                    <table id="nation">
                        <tr>
                            <td>Send Money</td>
                            <td>
                                <input type="number" id="amountCash" name="sendcash">
                                <button type="submit" onclick="sendCash(document.getElementById('amountCash').value, <c:out value="${id}"/>);">Send</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Send Raw Material</td>
                            <td>
                                <input type="number" id="amountRm" name="sendrm">
                                <button type="submit" onclick="sendRm(document.getElementById('amountRm').value, <c:out value="${id}"/>);">Send</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Send Oil</td>
                            <td>
                                <input type="number" id="amountOil" name="sendoil">
                                <button type="submit" onclick="sendOil(document.getElementById('amountOil').value, <c:out value="${id}"/>);">Send</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Send Manufactured Goods</td>
                            <td>
                                <input type="number" id="amountMg" name="sendmg">
                                <button type="submit" onclick="sendMg(document.getElementById('amountMg').value, <c:out value="${id}"/>);">Send</button>
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