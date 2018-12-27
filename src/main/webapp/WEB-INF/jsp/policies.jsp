<%@ include file = "includes/default.jsp" %>
<html>
    <%@ include file = "includes/head.jsp" %>
    <body>
        <%@ include file = "includes/header.jsp" %>
        <div class="main">
            <h1><c:out value="${param['policy']}"/> Policy</h1>
            <c:if test="${not empty param['result']}">
                <div class="result">
                    <p><c:out value="${param['result']}"/></p>
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
                    <c:when test="${param['policy'] == 'Economic'}">
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
                                <form action="policyresults" method="post">
                                    <input type="hidden" name="policy" value="freemoneycapitalist">
                                    <button class="policyButton" type="submit">Get Rich</button>
                                </form>
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
                                <form action="policyresults" method="post">
                                    <input type="hidden" name="policy" value="freemoneycommunist">
                                    <button class="policyButton" type="submit">Get Rich</button>
                                </form>
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
                                $<c:out value="${500 + (resultEconomy.rows[0].mines - 1) * 50}"/>k
                            </td>
                            <td>
                                <form action="policyresults" method="post">
                                    <input type="hidden" name="policy" value="mine">
                                    <button class="policyButton" type="submit">Mine</button>
                                </form>
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
                                $<c:out value="${500 + (resultEconomy.rows[0].wells - 1) * 100}"/>k
                            </td>
                            <td>
                                <form action="policyresults" method="post">
                                    <input type="hidden" name="policy" value="drill">
                                    <button class="policyButton" type="submit">Drill</button>
                                </form>
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
                                <c:out  value="${50 + (resultEconomy.rows[0].industry) * 100}"/> Htons of Raw Material,
                                <c:out  value="${25 + (resultEconomy.rows[0].industry) * 50}"/> Mmbls of Oil,
                                <c:out  value="${5 + (resultEconomy.rows[0].industry) * 5}"/> Tons of Manufactures Goods
                            </td>
                            <td>
                                <form action="policyresults" method="post">
                                    <input type="hidden" name="policy" value="industrialize">
                                    <button class="policyButton" type="submit">Industrialize</button>
                                </form>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </table>
        </div>
    </body>
</html>