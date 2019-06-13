<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <c:if test="${check.rowCount == 0}">
        <p>You must be logged in to view this page!</p>
    </c:if>
    <c:if test="${check.rowCount > 0}">
        <div class="nation">
            <h1><c:out value="${result_cosmetic.rows[0].nation_title}"/><br> of <br><c:out value="${result_cosmetic.rows[0].nation_name}"/></h1>
            <img class="flag" src="<c:out value="https://imgur.com/${result_cosmetic.rows[0].flag}"/>" alt="flag">
            <p><c:out value="${result_cosmetic.rows[0].description}"/></p>
            <img class="leader" src="<c:out value="https://imgur.com/${result_cosmetic.rows[0].portrait}"/>" alt="flag">
            <h1><c:out value="${result_cosmetic.rows[0].leader_title}"/>: <c:out value="${result_cosmetic.rows[0].username}"/></h1>
        </div>
        <h1>Government</h1>
        <table id="nation">
            <tr>
                <td>Government</td>
                <td><cloc:government value="${result_domestic.rows[0].government}"/></td>
            </tr>
            <tr>
                <td>Approval</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:approval value="${result_domestic.rows[0].approval}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${result_domestic.rows[0].approval}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Stability</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:stability value="${result_domestic.rows[0].stability}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${result_domestic.rows[0].stability}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Land</td>
                <td><c:out value="${result_domestic.rows[0].land}"/> km<sup>2</sup></td>
            </tr>
            <tr>
                <td>Rebel Threat</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:rebels value="${result_domestic.rows[0].rebels}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${result_domestic.rows[0].rebels}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <h1>Domestic</h1>
        <table id="nation">
            <tr>
                <td>Population</td>
                <td><cloc:popGrowth data="${result_map}"/></td>
            </tr>
            <tr>
                <td>Healthcare</td>
                <td><c:out value="${result_domestic.rows[0].healthcare}"/>%</td>
            </tr>
            <tr>
                <td>Literacy</td>
                <td><c:out value="${result_domestic.rows[0].literacy}"/>%</td>
            </tr>
            <tr>
                <td>Universities</td>
                <td><c:out value="${result_economy.rows[0].universities}"/> Universities</td>
            </tr>
        </table>
        <h1>Economy</h1>
        <table id="nation">
            <tr>
                <td>Economic System</td>
                <td><cloc:economic value="${result_economy.rows[0].economic}"/></td>
            </tr>
            <tr>
                <td>Gross Domestic Product</td>
                <td>$<c:out value="${result_economy.rows[0].gdp}"/> Million</td>
            </tr>
            <tr>
                <td>Growth</td>
                <td>$<c:out value="${result_economy.rows[0].growth}"/> Million per Month</td>
            </tr>
            <tr>
                <td>Industry</td>
                <td><c:out value="${result_economy.rows[0].industry}"/> Factories</td>
            </tr>
            <tr>
                <td>Manufactured Goods Stockpile</td>
                <td><c:out value="${result_economy.rows[0].mg}"/> Tons</td>
            <tr>
                <td>Discovered Oil Reserves</td>
                <td><c:out value="${result_economy.rows[0].reserves}"/> Mmbls</td>
            </tr>
            <tr>
                <td>Oil Production</td>
                <td><c:out value="${result_economy.rows[0].wells}"/> Mmbls per Month</td>
            </tr>
            <tr>
                <td>Oil Stockpile</td>
                <td><c:out value="${result_economy.rows[0].oil}"/> Mmbls</td>
            </tr>
            <tr>
                <td>Raw Material Production</td>
                <td><c:out value="${result_economy.rows[0].mines}"/> Hundred Tons per Month</td>
            </tr>
            <tr>
                <td>Raw Material Stockpile</td>
                <td><c:out value="${result_economy.rows[0].rm}"/> Hundred Tons</td>
            </tr>
            <tr>
                <td>Nitrogen Plants</td>
                <td><c:out value="${result_economy.rows[0].nitrogenplant}"/> Plants</td>
            </tr>
            <tr>
                <td>Ammonia Stockpile</td>
                <td><c:out value="${result_economy.rows[0].nitrogen}"/> Tons</td>
            </tr>
            <tr>
                <td>Food</td>
                <td><cloc:food main="${result_map}"/></td>
            </tr>
        </table>
        <h1>Foreign</h1>
        <table id="nation">
            <tr>
                <td>Region</td>
                <td><c:out value="${result_foreign.rows[0].region}"/></td>
            </tr>
            <tr>
                <td>Alliance</td>
                <td><c:out value="${result_foreign.rows[0].alliance}"/></td>
            </tr>
        </table>
        <h1>Military</h1>
        <table id="nation">
            <caption>Army</caption>
            <tr>
                <th>Region</th>
                <th>Size</th>
                <th>Training</th>
                <th>Equipment</th>
                <th>Reinforce</th>
            </tr>
            <tr>
                <td>Home</td>
                <td><c:out value="${result_military.rows[0].army_home}"/>k Active Personnel</td>
                <td><c:out value="${result_military.rows[0].training_home}"/></td>
                <td><c:out value="${result_military.rows[0].weapons_home}"/></td>
                <td><c:out value="${result_military.rows[0].war_protection}"/></td>
            </tr>
            <c:forEach var="i" items="${result_armies.rows}">
                <tr>
                    <td>${i.region}</td>
                    <td>${i.army}</td>
                    <td>${i.training}</td>
                    <td>${i.weapons}</td>
                    <td>${i.id}</td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <table id="nation">
            <caption>Navy</caption>
            <tr>
                <td>Destroyers</td>
                <td><c:out value="${result_military.rows[0].destroyers}"/></td>
            </tr>
            <tr>
                <td>Cruisers</td>
                <td><c:out value="${result_military.rows[0].cruisers}"/></td>
            </tr>
            <tr>
                <td>Battleships</td>
                <td><c:out value="${result_military.rows[0].battleships}"/></td>
            </tr>
            <tr>
                <td>Submarines</td>
                <td><c:out value="${result_military.rows[0].submarines}"/></td>
            </tr>
            <tr>
                <td>Troop Transports</td>
                <td><c:out value="${result_military.rows[0].transports}"/></td>
            </tr>
        </table>
        <br>
        <table id="nation">
            <caption>Airforce</caption>
            <tr>
                <td>Fighters</td>
                <td><c:out value="${result_military.rows[0].fighters}"/></td>
            </tr>
            <tr>
                <td>Zeppelins</td>
                <td><c:out value="${result_military.rows[0].zeppelins}"/></td>
            </tr>
        </table>
    </c:if>
    <p><br><br><br></p>
</div>
<p><br><br><br></p>
</body>
</html>