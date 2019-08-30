<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<div class="main">
    <c:if test="${sessionScope.user == null}">
        <p>You must be logged in to view this page!</p>
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <div class="nation">
            <h1><c:out value="${home.cosmetic.nationTitle}"/><br> of <br><c:out value="${home.cosmetic.nationName}"/></h1>
            <img class="flag" src="https://imgur.com/${home.cosmetic.flag}" alt="flag">
            <p><c:out value="${home.cosmetic.description}"/></p>
            <img class="leader" src="https://imgur.com/${home.cosmetic.portrait}" alt="flag">
            <h1><c:out value="${home.cosmetic.leaderTitle}"/>: <c:out value="${home.cosmetic.username}"/></h1>
        </div>
        <h1>City shit</h1>
        <table id="nation">
            <c:forEach var="i" items="${home.cities.cities}">
                <tr>
                    <td>${i.value.name}</td>
                    <td><a href="${pageContext.request.contextPath}/cities.jsp?id=${i.key}">View</a></td>
                </tr>
            </c:forEach>
        </table>
        <h1>Domestic</h1>
        <table id="nation">
            <tr>
                <td>Government</td>
                <td><cloc:government value="${home.domestic.government}"/></td>
            </tr>
            <tr>
                <td>Approval</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:approval value="${home.domestic.approval}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${home.domestic.approval}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Stability</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:stability value="${home.domestic.stability}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${home.domestic.stability}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Land</td>
                <td><c:out value="${home.domestic.land}"/>km<sup>2</sup></td>
            </tr>
            <tr>
                <td>Rebel Threat</td>
                <td>
                    <div class="dropdown">
                        <span>
                            <cloc:rebels value="${home.domestic.rebels}"/>
                        </span>
                        <div class="dropdown-content">
                            <i><c:out value="${home.domestic.rebels}"/>%</i>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <h1>Economy</h1>
        <table id="nation">
            <tr>
                <td>Economic System</td>
                <td><cloc:economic value="${home.economy.economic}"/></td>
            </tr>
            <tr>
                <td>Gross Domestic Product</td>
                <td>$<c:out value="${home.economy.gdp}"/> Million</td>
            </tr>
            <tr>
                <td>Growth</td>
                <td>
                    <div class="dropdown">
                        <span>
                            ${home.economy.growth}
                        </span>
                        <div class="dropdown-content">
                            ${growth.factories > 0 ? '+' + growth.factories + ' from factories<br>': ''}
                            ${growth.home < 0 ? '-' + growth.home + ' from home army<br>' : ''}
                            ${growth.foreign < 0 ? '-' + growth.foreign + ' from foreign armies' : ''}
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Steel Stockpile</td>
                <td><c:out value="${home.economy.steel}"/> Tons</td>
            <tr>
                <td>Oil Stockpile</td>
                <td><c:out value="${home.economy.oil}"/> Mmbls</td>
            </tr>
            <tr>
                <td>Iron Stockpile</td>
                <td><c:out value="${home.economy.iron}"/> Hundred Tons</td>
            </tr>
            <tr>
                <td>Coal Stockpile</td>
                <td><c:out value="${home.economy.coal}"/> Hundred Tons</td>
            </tr>
            <tr>
                <td>Food</td>
                <td></td>
            </tr>
        </table>
        <h1>Foreign</h1>
        <table id="nation">
            <tr>
                <td>Region</td>
                <td><c:out value="${home.foreign.region.name}"/></td>
            </tr>
            <tr>
                <td>Official Alignment</td>
                <td><cloc:alignment value="${home.foreign.alignment}"/></td>
            </tr>
            <tr>
                <td>Treaty Membership</td>
                <td>None</td>
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
            <c:forEach var="i" items="${home.armies.armies}">
                <tr>
                    <td>${i.value.region.name}</td>
                    <td>${i.value.army}</td>
                    <td>${i.value.training}</td>
                    <td>${i.value.weapons}</td>
                    <td>${i.value.id}</td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <table id="nation">
            <caption>Navy</caption>
            <tr>
                <td>Destroyers</td>
                <td><c:out value="${home.military.destroyers}"/></td>
            </tr>
            <tr>
                <td>Cruisers</td>
                <td><c:out value="${home.military.cruisers}"/></td>
            </tr>
            <tr>
                <td>Battleships</td>
                <td><c:out value="${home.military.battleships}"/></td>
            </tr>
            <tr>
                <td>Submarines</td>
                <td><c:out value="${home.military.submarines}"/></td>
            </tr>
            <tr>
                <td>Troop Transports</td>
                <td><c:out value="${home.military.transports}"/></td>
            </tr>
        </table>
        <br>
        <table id="nation">
            <caption>Airforce</caption>
            <tr>
                <td>Fighters</td>
                <td><c:out value="${home.military.fighters}"/></td>
            </tr>
            <tr>
                <td>Zeppelins</td>
                <td><c:out value="${home.military.zeppelins}"/></td>
            </tr>
        </table>
    </c:if>
    <p><br><br><br></p>
</div>
<p><br><br><br></p>
</body>
</html>