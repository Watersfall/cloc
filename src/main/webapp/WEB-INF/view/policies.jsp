<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="${pageContext.request.contextPath}/js/policies.js"></script>
<%-- POLICIES --%>
<div class="main">
    <%@ include file="includes/results.jsp" %>
    <h1><c:out value="${param['policies']}"/> Policy</h1>
    <c:if test="${not empty policyResult}">
        <div class="result">
            <c:if test="${not empty image}">
                <img class="policyImage" src="${pageContext.request.contextPath}/images/policies/<c:out value="${image}"/>" alt="Policy">
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
            <c:when test="${check.rowCount == 0}">
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
                        <button class="policyButton" type="submit" onclick="policy('freemoneycapitalist')">Get Rich
                        </button>
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
                        <button class="policyButton" type="submit" onclick="policy('freemoneycommunist')">Get Rich
                        </button>
                    </td>
                </tr>
            </c:when>
            <c:when test="${param['policies'] == 'Domestic'}">
                <tr>
                    <td>
                        Increase Arrest Quotas
                    </td>
                    <td>
                        Tell your police force they need to arrest more criminals! Increases stability, but lowers
                        approval and moves your government to the right.
                    </td>
                    <td>
                        $<c:out value="${policyConstants.CRACKDOWN}"/>k
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
                        Release the jaywalkers from their cells! Decreases stability, but increases approval and moves
                        your government to the left.
                    </td>
                    <td>
                        $<c:out value="${policyConstants.FREE}"/>k
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
                        Build a university to further your research! Consumes 2 STEEL. Same cost as factories.
                    </td>
                    <td>
                        nada
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
                        $<c:out value="${policyConstants.ALIGN_ENTENTE}"/>k
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
                        $<c:out value="${policyConstants.ALIGN_CENTRAL_POWERS}"/>k
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
                        $<c:out value="${policyConstants.ALIGN_NEUTRAL}"/>k
                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('alignneutral')">Celebrate</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Form Treaty
                    </td>
                    <td>
                        Create an international alliance to keep yourself safe
                    </td>
                    <td>
                        $0k
                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="window.location.href = '/createtreaty.jsp'">Create</button>
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
                        $k
                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('train')">Train</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Build Submarine
                    </td>
                    <td>
                        Build a submarine to augment your glorious navy!
                    </td>
                    <td>

                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('buildship?ship=ss')">Celebrate</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Build Destroyer
                    </td>
                    <td>
                        Build a destroyer to augment your glorious navy!
                    </td>
                    <td>

                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('buildship?ship=dd')">Celebrate</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Build Cruiser
                    </td>
                    <td>
                        Build a cruiser to augment your glorious navy!
                    </td>
                    <td>

                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('buildship?ship=cl')">Celebrate</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        Build Battleship
                    </td>
                    <td>
                        Build a battleship to augment your glorious navy!
                    </td>
                    <td>

                    </td>
                    <td>
                        <button class="policyButton" type="submit" onclick="policy('buildship?ship=bb')">Celebrate</button>
                    </td>
                </tr>
            </c:when>
        </c:choose>
    </table>
</div>
</body>
</html>