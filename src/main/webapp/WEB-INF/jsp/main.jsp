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
                <div class="nation">
                    <h1><c:out value="${result.rows[0].nationTitle}"/><br><c:out value="${result.rows[0].nation}"/></h1>
                    <img class="flag" src="<c:out value="https://imgur.com/${result.rows[0].flag}"/>" alt="flag">
                    <p><c:out value="${result.rows[0].description}"/></p>
                    <img class="leader" src="<c:out value="https://imgur.com/${result.rows[0].leader}"/>" alt="flag">
                    <h1><c:out value="${result.rows[0].leaderTitle}"/>: <c:out value="${result.rows[0].username}"/></h1>
                </div>
                <h1>Government</h1>
                <table id="nation">
                    <tr>
                        <td>Government</td>
                        <td><cloc:government value="${resultMain.rows[0].political}"/></td>
                    </tr>
                    <tr>
                        <td>Approval</td>
                        <td>
                            <div class="dropdown">
                                <span>
                                    <cloc:approval value="${resultMain.rows[0].approval}"/>
                                </span>
                                <div class="dropdown-content">
                                    <i><c:out value="${resultMain.rows[0].approval}"/>%</i>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Stability</td>
                        <td>
                            <div class="dropdown">
                                <span>
                                    <cloc:stability value="${resultMain.rows[0].stability}"/>
                                </span>
                                <div class="dropdown-content">
                                    <i><c:out value="${resultMain.rows[0].stability}"/>%</i>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Land</td>
                        <td><c:out value="${resultMain.rows[0].land}"/> km<sup>2</sup></td>
                    </tr>
                    <tr>
                        <td>Rebel Threat</td>
                        <td>
                            <div class="dropdown">
                                <span>
                                    <cloc:rebels value="${resultMain.rows[0].rebel}"/>
                                </span>
                                <div class="dropdown-content">
                                    <i><c:out value="${resultMain.rows[0].rebel}"/>%</i>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                <h1>Domestic</h1>
                <table id="nation">
                    <tr>
                        <td>Population</td>
                        <td><c:out value="${resultMain.rows[0].population}"/> People</td>
                    </tr>
                    <tr>
                        <td>Quality of Life</td>
                        <td><c:out value="${resultMain.rows[0].qol}"/>%</td>
                    </tr>
                    <tr>
                        <td>Healthcare</td>
                        <td><c:out value="${resultMain.rows[0].healthcare}"/>%</td>
                    </tr>
                    <tr>
                        <td>Literacy</td>
                        <td><c:out value="${resultMain.rows[0].literacy}"/>%</td>
                    </tr>
                    <tr>
                        <td>Universities</td>
                        <td><c:out value="${resultMain.rows[0].universities}"/> Universities</td>
                    </tr>
                </table>
                <h1>Economy</h1>
                <table id="nation">
                    <tr>
                        <td>Economic System</td>
                        <td><cloc:economic value="${resultMain.rows[0].economic}"/></td>
                    </tr>
                    <tr>
                        <td>Gross Domestic Product</td>
                        <td>$<c:out value="${resultMain.rows[0].gdp}"/> Million</td>
                    </tr>
                    <tr>
                        <td>Growth</td>
                        <td>$<c:out value="${resultMain.rows[0].growth}"/> Million per Month</td>
                    </tr>
                    <tr>
                        <td>Industry</td>
                        <td><c:out value="${resultMain.rows[0].industry}"/> Factories</td>
                    </tr>
                    <tr>
                        <td>Manufactured Goods Stockpile</td>
                        <td><c:out value="${resultMain.rows[0].mg}"/> Tons</td>
                    <tr>
                        <td>Discovered Oil Reserves</td>
                        <td><c:out value="${resultMain.rows[0].reserves}"/> Mmbls</td>
                    </tr>
                    <tr>
                        <td>Oil Production</td>
                        <td><c:out value="${resultMain.rows[0].wells}"/> Mmbls per Month</td>
                    </tr>
                    <tr>
                        <td>Oil Stockpile</td>
                        <td><c:out value="${resultMain.rows[0].oil}"/> Mmbls</td>
                    </tr>
                    <tr>
                        <td>Raw Material Production</td>
                        <td><c:out value="${resultMain.rows[0].mines}"/> Hundred Tons per Month</td>
                    </tr>
                    <tr>
                        <td>Raw Material Stockpile</td>
                        <td><c:out value="${resultMain.rows[0].rm}"/> Hundred Tons</td>
                    </tr>
                    <tr>
                        <td>Nitrogen Plants</td>
                        <td><c:out value="${resultMain.rows[0].nitrogenplants}"/> Plants</td>
                    </tr>
                    <tr>
                        <td>Ammonia Stockpile</td>
                        <td><c:out value="${resultMain.rows[0].nitrogen}"/> Tons</td>
                    </tr>
                </table>
                <h1>Foreign</h1>
                <table id="nation">
                    <tr>
                        <td>Official Alignment</td>
                        <td><cloc:alignment value="${resultMain.rows[0].alignment}"/></td>
                    </tr>
                    <tr>
                        <td>Central Powers Relations</td>
                        <td><c:out value="${resultMain.rows[0].central}"/></td>
                    </tr>
                    <tr>
                        <td>Entente Relations</td>
                        <td><c:out value="${resultMain.rows[0].entente}"/></td>
                    </tr>
                    <tr>
                        <td>Region</td>
                        <td><c:out value="${resultMain.rows[0].region}"/></td>
                    </tr>
                    <tr>
                        <td>Alliance</td>
                        <td><c:out value="${resultMain.rows[0].alliance}"/></td>
                    </tr>
                    <tr>
                        <td>Alliance Votes Received</td>
                        <td><c:out value="${resultMain.rows[0].votes}"/></td>
                    </tr>
                    <tr>
                        <td>Voting For</td>
                        <td><c:out value="${resultMain.rows[0].voting}"/></td>
                    </tr>
                    <tr>
                        <td>Reputation</td>
                        <td><c:out value="${resultMain.rows[0].reputation}"/></td>
                    </tr>
                </table>
                <h1>Military</h1>
                <table id="nation">
                    <tr>
                        <td>Army Size</td>
                        <td><c:out value="${resultMain.rows[0].army}"/>k Troops</td>
                    </tr>
                    <tr>
                        <td>Manpower</td>
                        <td><c:out value="${resultMain.rows[0].manpower}"/>k/100k Manpower</td>
                    </tr>
                    <tr>
                        <td>Equipment</td>
                        <td><c:out value="${resultMain.rows[0].tech}"/> Guns</td>
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
                        <td><c:out value="${resultMain.rows[0].training}"/>/100</td>
                    </tr>
                    <tr>
                        <td>Airforce</td>
                        <td><c:out value="${resultMain.rows[0].airforce}"/> Planes</td>
                    </tr>
                    <tr>
                        <td>Navy</td>
                        <td><c:out value="${resultMain.rows[0].navy}"/> Ships</td>
                    </tr>
                </table>
            </c:if>
            <p><br><br><br></p>
        </div>
        <p><br><br><br></p>
    </body>
</html>