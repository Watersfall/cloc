<%@ include file="default.jsp" %>
<div id="city">
    <c:choose>
        <c:when test="${empty param['id']}">
            <p>You have visited this page incorrectly!</p>
        </c:when>
        <c:otherwise>
            <sql:query var="city" dataSource="${database}">
                SELECT * FROM cloc_cities WHERE id=?
                <sql:param value="${param['id']}"/>
            </sql:query>
                <c:choose>
                    <c:when test="${city.rowCount == 0}">
                        <p>This city does not exist!</p>
                    </c:when>
                    <c:otherwise>
                        <h1>${city.rows[0].name}</h1>
                        <ul>
                            <li class="cityElements">
                                <h3>Coal Mines</h3>
                                <img src="/images/city/coalmine.png" alt="coal mine"/>
                                <p>
                                    ${city.rows[0].coal_mines} coal mines producing ${city.rows[0].coal_mines / 4 } tons of coal per week
                                    <br>
                                    (${1 / 4} tons per week per mine)
                                </p>
                                <button onclick="cityPolicy('coalmine', '${city.rows[0].id}')">Build - $k</button>
                                <button onclick="cityPolicy('uncoalmine', '${city.rows[0].id}')">Close</button>
                            </li>
                            <li class="cityElements">
                                <h3>Iron Mines</h3>
                                <img src="/images/city/ironmine.png" alt="iron mine"/>
                                <p>
                                    ${city.rows[0].iron_mines} iron mines producing ${city.rows[0].iron_mines / 4 } tons of coal per week
                                    <br>
                                    (${1 / 4} tons per week per mine)
                                </p>
                                <button onclick="cityPolicy('ironmine', '${city.rows[0].id}')">Build - $k</button>
                                <button onclick="cityPolicy('unironmine', '${city.rows[0].id}')">Close</button>
                            </li>
                            <li class="cityElements">
                                <h3>Oil Wells</h3>
                                <img src="/images/city/oilwell.png" alt="iron mine"/>
                                <p>
                                        ${city.rows[0].oil_wells} oil wells producing ${city.rows[0].oil_wells / 4 } tons of oil per week
                                    <br>
                                    (${1 / 4} tons per week per well)
                                </p>
                                <button onclick="cityPolicy('drill', '${city.rows[0].id}')">Build - $k</button>
                                <button onclick="cityPolicy('undrill', '${city.rows[0].id}')">Close</button>
                            </li>
                        </ul>
                        <ul>
                            <li class="cityElements">
                                <h3>Civilian Factories</h3>
                                <img src="/images/city/civ.png" alt="civilian factory"/>
                                <p>
                                    ${city.rows[0].civilian_industry} civilian factories producing ${city.rows[0].civilian_industry / 4 } tons of steel per week
                                    <br>
                                    (${1 / 4} tons per week per factory)
                                </p>
                                <button onclick="cityPolicy('industrialize', '${city.rows[0].id}')">Build - </button>
                                <button onclick="cityPolicy('unindustrialize', '${city.rows[0].id}')">Close</button>
                            </li>
                            <li class="cityElements">
                                <h3>Military Factories</h3>
                                <img src="/images/city/mil.png" alt="military factory"/>
                                <p>
                                    ${city.rows[0].military_industry} military factories producing ${city.rows[0].military_industry * 10 } weapons per week
                                    <br>
                                    (${1 * 10} weapons per week per factory)
                                </p>
                                <button onclick="cityPolicy('militarize', '${city.rows[0].id}')">Build - </button>
                                <button onclick="cityPolicy('unmilitarize', '${city.rows[0].id}')">Close</button>
                            </li>
                            <li class="cityElements">
                                <h3>Nitrogen Plants</h3>
                                <img src="/images/city/civ.png" alt="nitrogen factory"/>
                                <p>
                                        ${city.rows[0].military_industry} military factories producing ${city.rows[0].military_industry * 10 } weapons per week
                                    <br>
                                    (${1 * 10} weapons per week per factory)
                                </p>
                                <button onclick="cityPolicy('nitrogenplant', '${city.rows[0].id}')">Build - </button>
                                <button onclick="cityPolicy('unnitrogenplant', '${city.rows[0].id}')">Close</button>
                            </li>
                        </ul>
                        <ul>
                            <li class="cityElements">
                                <h3>Universities</h3>
                                <img src="/images/city/uni.png" alt="university"/>
                                <p>
                                        ${city.rows[0].military_industry} universities producing ${city.rows[0].military_industry * 10 } research per week
                                    <br>
                                    (${1 * 10} research per week per university)
                                </p>
                                <button onclick="cityPolicy('university', '${city.rows[0].id}')">Build - </button>
                                <button onclick="cityPolicy('ununiversity', '${city.rows[0].id}')">Close</button>
                            </li>
                        </ul>
                        <ul>
                            <li class="cityElements">
                                <h3>Railroads</h3>
                                <img src="/images/city/railroad.png" alt="railroad"/>
                                <p>test</p>
                            </li>
                            <li class="cityElements">
                                <h3>Military Barracks</h3>
                                <img src="/images/city/barrack.png" alt="barrack"/>
                                <p>test</p>
                            </li>
                            <li class="cityElements">
                                <h3>Ports</h3>
                                <img src="/images/city/port.png" alt="port"/>
                                <p>test</p>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>
        </c:otherwise>
    </c:choose>
</div>