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
                                $<cloc:mine main="${resultMain}"/>k
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
                                $<cloc:well main="${resultMain}"/>k
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
                                <cloc:factory main="${resultMain}"/>
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
                                <cloc:nitrogen main="${resultMain}"/>
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('nitrogenplant')">Progress</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Close Mine
                            </td>
                            <td>
                                Close down a mine. Reduces max employment, don't know why you'd wanna do that.
                            </td>
                            <td>
                                Some Families' Income
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('unmine')">Close</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Close Well
                            </td>
                            <td>
                                Close down an oil well. Reduces max employment, don't know why you'd wanna do that.
                            </td>
                            <td>
                                Some Families' Income
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('undrill')">Burn</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Shutter Factory
                            </td>
                            <td>
                                Close down a factory. Significantly reduces max employment, don't know why you'd wanna do that.
                            </td>
                            <td>
                                A Lot of Families' Income
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('unindustrialize')">Shutter</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Shutter Nitrogen Plant
                            </td>
                            <td>
                                Shut down a plant. Massively reduces max employment, don't know why you'd wanna do that.
                            </td>
                            <td>
                                A Lot More Families' Income
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('unnitrogenplant')">Delete</button>
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
                                $<c:out value="${policyConstants.COST_CRACKDOWN}"/>k
                            </td>
                            <td>
                                <button class="policyButton" type="submit" onclick="policy('crackdown')">Crackdown</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Pardon Petty Criminals
                            </td>
                            <td>
                                Release the jaywalkers from their cells! Decreases stability, but increases approval and moves your government to the left.
                            </td>
                            <td>
                                $<c:out value="${policyConstants.COST_FREE}"/>k
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
                                <cloc:nitrogen main="${resultMain}"/>
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
                                $<c:out value="${policyConstants.COST_ALIGN_ENTENTE}"/>k
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
                                $<c:out value="${policyConstants.COST_ALIGN_CENTRAL_POWERS}"/>k
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
                                $<c:out value="${policyConstants.COST_ALIGN_NEUTRAL}"/>k
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
                                $<cloc:training main="${resultMain}"/>k
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