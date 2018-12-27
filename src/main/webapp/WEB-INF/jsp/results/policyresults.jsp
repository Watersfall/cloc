<%@ include file = "../includes/default.jsp" %>
<%@ include file = "../includes/header.jsp" %>
<c:choose>
    <c:when test="${result.rowCount == 0}">
        <p>You must be logged in to view this page!</p>
    </c:when>
    <c:when test="${empty param['policy']}">
        <h4>What did you expect?</h4>
    </c:when>



    <c:when test="${param['policy'] == 'freemoneycapitalist'}">
        <sql:update dataSource="${database}">
            UPDATE cloc_economy SET budget=budget+1000, economic=economic+5 WHERE sess=?;
            <sql:param value="${sess}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc_economy SET economic=100 WHERE economic>100;
        </sql:update>
        <c:redirect url="/policies?policy=Economic&result=You cut the pay and benefits for government employees to fund your newest projects"/>
    </c:when>



    <c:when test="${param['policy'] == 'freemoneycommunist'}">
        <sql:update dataSource="${database}">
            UPDATE cloc_economy SET budget=budget+1000, economic=economic-5 WHERE sess=?;
            <sql:param value="${sess}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc_economy SET economic=0 WHERE economic<0;
        </sql:update>
        <c:redirect url="/policies?policy=Economic&result=You raise taxes by 1%25 to fund your newest projects"/>
    </c:when>



    <c:when test="${param['policy'] == 'drill'}">
        <c:set var="cost" value="${500 + (resultEconomy.rows[0].wells - 1) * 100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_economy SET wells=wells+1, budget=budget-? WHERE sess=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Economic&result=You drill a new oil well!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'mine'}">
        <c:set var="cost" value="${500 + (resultEconomy.rows[0].mines - 1) * 50}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_economy SET mines=mines+1, budget=budget-? WHERE sess=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Economic&result=You drill a new oil well!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'industrialize'}">
        <c:set var="rm" value="${50 + (resultEconomy.rows[0].industry) * 100}"/>
        <c:set var="oil" value="${25 + (resultEconomy.rows[0].industry) * 50}"/>
        <c:set var="mg" value="${5 + (resultEconomy.rows[0].industry) * 5}"/>
        <c:if test="${resultEconomy.rows[0].rm < rm}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough Raw Material!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].oil < oil}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough Oil!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].mg < mg}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough Manufactured Goods!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_economy SET industry=industry+1, budget=budget-? WHERE sess=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Economic&result=You drill a new oil well!"/>
        </c:if>
    </c:when>



    <c:otherwise>
        <p><c:redirect url="/policies?policy=Economic&result=All your money seemingly disappears! How odd..."/></p>
    </c:otherwise>
</c:choose>
