<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <script src="/js/policies.js"></script>
        <%-- POLICIES --%>
        <div class="main">
            <%@ include file = "includes/results.jsp" %>
            <h1><c:out value="${param['policies']}"/> Policy</h1>
            <c:if test="${not empty policyResult}">
                <div class="result">
                    <c:if test="${not empty image}">
                        <img class="policyImage" src="/images/policies/<c:out value="${image}"/>" alt="Policy">
                    </c:if>
                    <p><c:out value="${policyResult}"/></p>
                </div>
            </c:if>
            <table id="policy">
                <tr>
                    <th style="width: 15%">
                        Policy
                    </th>
                    <th style="width: 50%">
                        Description
                    </th>
                    <th style="width: 20%;">
                        Cost
                    </th>
                    <th style="width: 20%"></th>
                </tr>
                <c:choose>
                    <c:when test="${result.rowCount == 0}">
                        <p>You must be logged in to view this page!</p>
                    </c:when>
                    <c:when test="${param['policies'] == 'Economic'}">
                        <tr>
                            <td>
                                Free Money
                            </td>
                            <td>
                                Acquire some free money, the Free Market way.
                            </td>
                            <td>
                                Free!
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('freemoneycapitalist')">Get Rich</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Free Money
                            </td>
                            <td>
                                Acquire some free money, the Communist way.
                            </td>
                            <td>
                                Free!
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('freemoneycommunist')">Get Rich</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Dig Mine
                            </td>
                            <td>
                                Dig a new mine, increasing raw material output by 1 Hton per turn
                            </td>
                            <td>
                                $<c:out value="${500 + (resultMain.rows[0].mines - 1) * 50}"/>k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('mine')">Mine</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Drill Well
                            </td>
                            <td>
                                Drill a new oil well, increasing oil output by 1 Mmbl per turn
                            </td>
                            <td>
                                $<c:out value="${500 + (resultMain.rows[0].wells - 1) * 100}"/>k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('drill')">Drill</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Build Factory
                            </td>
                            <td>
                                Build a new factory for your growing population, Consumes 1 Oil and Raw Material to produce 1 Manufactured good
                            </td>
                            <td>
                                <c:out  value="${50 + ((resultMain.rows[0].industry + resultMain.rows[0].milindustry) * 50)}"/> Htons of Raw Material,
                                <c:out  value="${25 + ((resultMain.rows[0].industry + resultMain.rows[0].milindustry) * 25)}"/> Mmbls of Oil,
                                <c:out  value="${0 + ((resultMain.rows[0].industry + resultMain.rows[0].milindustry) * 5)}"/> Tons of Manufactures Goods
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('industrialize')">Industrialize</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Build Nitrogen Plant
                            </td>
                            <td>
                                Build a new Nitrogen Fixation plant to blow up other people's populations, consumes 2 Oil and 1 MG to produce 1 Nitrogen. Same cost as factories
                            </td>
                            <td>
                                <c:out  value="${100 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 100)}"/> Htons of Raw Material,
                                <c:out  value="${50 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 50)}"/> Mmbls of Oil,
                                <c:out  value="${10 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 10)}"/> Tons of Manufactures Goods
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('nitrogenplant')">Progress</button>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${param['policies'] == 'Domestic'}">
                        <tr>
                            <td>
                                Increase Arrest Quotas
                            </td>
                            <td>
                                Tell your police force they need to arrest more criminals! Increases stability, but lowers approval and moves your government to the right.
                            </td>
                            <td>
                                $100k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('crackdown')">Crackdown</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Parden Petty Criminals
                            </td>
                            <td>
                                Release the jaywalkers from their cells! Decreases stability, but increases approval and moves your government to the left.
                            </td>
                            <td>
                                $100k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('free')">Free</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Build University
                            </td>
                            <td>
                                Build a university to further your research! Consumes 2 MG. Same cost as factories.
                            </td>
                            <td>
                                <c:out  value="${100 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 100)}"/> Htons of Raw Material,
                                <c:out  value="${50 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 50)}"/> Mmbls of Oil,
                                <c:out  value="${10 + ((resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 10)}"/> Tons of Manufactures Goods
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('university')">Advance</button>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${param['policies'] == 'Foreign'}">
                        <tr>
                            <td>
                                Align With The Entente
                            </td>
                            <td>
                                Praise France's Democracy, hoping to make them like you.
                            </td>
                            <td>
                                $100k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('alignentente')">Praise</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Align With The Central Powers
                            </td>
                            <td>
                                Admire the German <i>Stahlhelm</i>, hoping to protect yourself from shrapnel.
                            </td>
                            <td>
                                $100k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('aligncentral')">Admire</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Declare Neutrality
                            </td>
                            <td>
                                Go out on stage and celebrate your people's strength!
                            </td>
                            <td>
                                $100k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('alignneutral')">Celebrate</button>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${param['policies'] == 'Military'}">
                        <tr>
                            <td>
                                Conscript
                            </td>
                            <td>
                                Throw more men into your army at the cost of manpower and overall training.
                            </td>
                            <td>
                                Reduction in Manpower, Training
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('conscript')">Conscript</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Train
                            </td>
                            <td>
                                Train your army
                            </td>
                            <td>
                                <c:out value="${resultMain.rows[0].army * (resultMain.rows[0].training * resultMain.rows[0].training) / 100}"/>
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('train')">Train</button>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </table>
        </div>
    </body>
</html>