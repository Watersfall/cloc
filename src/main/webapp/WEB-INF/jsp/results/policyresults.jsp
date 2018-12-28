<%@ include file = "../includes/default.jsp" %>
<%@ include file = "../includes/header.jsp" %>
<c:choose>
    <c:when test="${result.rowCount == 0}">
        <p>You must be logged in to view this page!</p>
    </c:when>
    <c:when test="${empty param['policy']}">
        <h4>What did you expect?</h4>
    </c:when>



    <%-- Economic Policies --%>



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



    <%-- Domestic Policies --%>



    <c:when test="${param['policy'] == 'crackdown'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Domestic&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultDomestic.rows[0].political > 100}">
            <c:redirect url="/policies?policy=Domestic&result=There are no more lollygaggers to arrest!"/>
        </c:if>
        <c:if test="${resultDomestic.rows[0].approval < 3}">
            <c:redirect url="/policies?policy=Domestic&result=You are not popular enough to do this!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_domestic SET stability=stability+5, approval=approval-3, political=political+5 WHERE sess=?;
                <sql:param value="${sess}"/>
            </sql:update>
            <sql:update dataSource="${database}">
                UPDATE cloc_domestic SET stability=100 WHERE stability>100;
            </sql:update>
            <sql:update dataSource="${database}">
                UPDATE cloc_economy SET budget=budget-? WHERE sess=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Domestic&result=Your police force arrests every petty criminal they could find!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'free'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Domestic&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultDomestic.rows[0].stability < 5}">
            <c:redirect url="/policies?policy=Domestic&result=You are not stable enough!"/>
        </c:if>
        <c:if test="${resultDomestic.rows[0].political < 5}">
            <c:redirect url="/policies?policy=Domestic&result=You have no more prisoners to free!"/>
        </c:if>
        <sql:update dataSource="${database}">
            UPDATE cloc_domestic SET stability=stability-5, approval=approval+3, political=political-5 WHERE sess=?;
            <sql:param value="${sess}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc_domestic SET approval=100 WHERE approval>100;
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc_economy SET budget=budget-? WHERE sess=?;
            <sql:param value="${cost}"/>
            <sql:param value="${sess}"/>
        </sql:update>
        <c:redirect url="/policies?policy=Domestic&result=Your people enjoy their freedoms!"/>
    </c:when>



    <%-- Foreign Policies --%>



    <c:when test="${param['policy'] == 'aligncentral'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Foreign&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_foreign SET alignment=1 WHERE sess=?;
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Foreign&result=You align yourself with the Central Powers!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignentente'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Foreign&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_foreign SET alignment=-1 WHERE sess=?;
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Foreign&result=You align yourself with the Entente!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignneutral'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Foreign&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_foreign SET alignment=0 WHERE sess=?;
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Foreign&result=Your people cheer as you declare your neutrality!"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignsoviet'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultEconomy.rows[0].budget < cost}">
            <c:redirect url="/policies?policy=Economic&result=You do not have enough money!"/>
        </c:if>
        <c:if test="${resultEconomy.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc_foreign SET alignment=2 WHERE sess=?;
                <sql:param value="${sess}"/>
            </sql:update>
            <c:redirect url="/policies?policy=Foreign&result=You align yourself with the newly formed Soviet Union!"/>
        </c:if>
    </c:when>



    <%-- Military Policies --%>



    <c:otherwise>
        <p><c:redirect url="/policies?policy=Economic&result=All your money seemingly disappears! How odd..."/></p>
    </c:otherwise>
</c:choose>
