<c:choose>
    <c:when test="${result.rowCount == 0}">
        <p>You must be logged in to view this page!</p>
    </c:when>
    <c:when test="${empty param['policy']}">
    </c:when>



    <%-- Economic Policies --%>



    <c:when test="${param['policy'] == 'freemoneycapitalist'}">
        <sql:update dataSource="${database}">
            UPDATE cloc SET budget=budget+1000, economic=economic+5
            WHERE sess=?;
            <sql:param value="${sess}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc SET economic=100
            WHERE economic>100;
        </sql:update>
        <c:set var="policyResult" value="You cut the pay and benefits for government employees to fund your newest projects"/>
    </c:when>



    <c:when test="${param['policy'] == 'freemoneycommunist'}">
        <sql:update dataSource="${database}">
            UPDATE cloc SET budget=budget+1000, economic=economic-5
            WHERE sess=?;
            <sql:param value="${sess}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc SET economic=0
            WHERE economic<0;
        </sql:update>
        <c:set var="policyResult" value="You raise taxes by 1% to fund your newest projects"/>
    </c:when>



    <c:when test="${param['policy'] == 'drill'}">
        <c:set var="cost" value="${500 + (resultMain.rows[0].wells - 1) * 100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET wells=wells+1, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="You drill a new oil well!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'mine'}">
        <c:set var="cost" value="${500 + (resultMain.rows[0].mines - 1) * 50}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET mines=mines+1, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="You dig a new mine!" scope="page"/>
            <c:set var="image" value="mine.jpg" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'industrialize'}">
        <c:set var="rm" value="${50 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 100)}"/>
        <c:set var="oil" value="${25 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 50)}"/>
        <c:set var="mg" value="${0 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 5)}"/>
        <c:if test="${resultMain.rows[0].rm < rm}">
            <c:set var="policyResult" value="You do not have enough Raw Material!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].oil < oil}">
            <c:set var="policyResult" value="You do not have enough Oil!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].mg < mg}">
            <c:set var="policyResult" value="You do not have enough Manufactured Goods!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET industry=industry+1, rm=rm-?, oil=oil-?, mg=mg-?
                WHERE sess=? && rm>=? && oil>=? && mg>=?;
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
            </sql:update>
            <c:set var="policyResult" value="Your farmers flock to your cities for a new life!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'nitrogenplant'}">
        <c:set var="rm" value="${50 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 100)}"/>
        <c:set var="oil" value="${25 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 50)}"/>
        <c:set var="mg" value="${0 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 5)}"/>
        <c:if test="${resultMain.rows[0].rm < rm}">
            <c:set var="policyResult" value="You do not have enough Raw Material!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].oil < oil}">
            <c:set var="policyResult" value="You do not have enough Oil!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].mg < mg}">
            <c:set var="policyResult" value="You do not have enough Manufactured Goods!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET nitrogenplant=nitrogenplant+1, rm=rm-?, oil=oil-?, mg=mg-?
                WHERE sess=? && rm>=? && oil>=? && mg>=?;
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
            </sql:update>
            <c:set var="policyResult" value="You build a new Ammonia plant!" scope="page"/>
        </c:if>
    </c:when>



    <%-- Domestic Policies --%>



    <c:when test="${param['policy'] == 'crackdown'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].political >= 100}">
            <c:set var="policyResult" value="There are no more lollygaggers to arrest!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].approval < 3}">
            <c:set var="policyResult" value="You are not popular enough to do this!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET stability=stability+5, approval=approval-3, political=political+5, budget=budget-?
                WHERE sess=? && approval>=3 && political<100 && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <sql:update dataSource="${database}">
                UPDATE cloc SET stability=100
                WHERE stability>100;
            </sql:update>
            <c:set var="policyResult" value="Your police force arrests every petty criminal they could find!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'free'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].stability < 5}">
            <c:set var="policyResult" value="You are not stable enough!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].political < 5}">
            <c:set var="policyResult" value="You have no more prisoners to free!" scope="page"/>
        </c:if>
        <sql:update dataSource="${database}">
            UPDATE cloc SET stability=stability-5, approval=approval+3, political=political-5, budget=budget-?
            WHERE sess=? && political<5 && stability>=5 && budget>=?;
            <sql:param value="${cost}"/>
            <sql:param value="${sess}"/>
            <sql:param value="${cost}"/>
        </sql:update>
        <sql:update dataSource="${database}">
            UPDATE cloc SET approval=100
            WHERE approval>100;
        </sql:update>
        <c:set var="policyResult" value="Your people enjoy their freedoms!" scope="page"/>
    </c:when>



    <c:when test="${param['policy'] == 'university'}">
        <c:set var="rm" value="${50 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 100)}"/>
        <c:set var="oil" value="${25 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 50)}"/>
        <c:set var="mg" value="${0 + ((resultMain.rows[0].industry + resultMain.rows[0].nitrogenplant + resultMain.rows[0].universities) * 5)}"/>
        <c:if test="${resultMain.rows[0].rm < rm}">
            <c:set var="policyResult" value="You do not have enough Raw Material!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].oil < oil}">
            <c:set var="policyResult" value="You do not have enough Oil!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].mg < mg}">
            <c:set var="policyResult" value="You do not have enough Manufactured Goods!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET universities=universities+1, rm=rm-?, oil=oil-?, mg=mg-?
                WHERE sess=? && rm>=? && oil>=? && mg>=?;
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${rm}"/>
                <sql:param value="${oil}"/>
                <sql:param value="${mg}"/>
            </sql:update>
            <c:set var="policyResult" value="You build a new University!" scope="page"/>
        </c:if>
    </c:when>



    <%-- Foreign Policies --%>



    <c:when test="${param['policy'] == 'aligncentral'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET alignment=1, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="You align yourself with the Central Powers!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignentente'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET alignment=-1, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="You align yourself with the Entente!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignneutral'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET alignment=0, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="Your people cheer as you declare your neutrality!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'alignsoviet'}">
        <c:set var="cost" value="${100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET alignment=2, budget=budget-?
                WHERE sess=? && budget>=?;
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <c:set var="policyResult" value="You align yourself with the newly formed Soviet Union!" scope="page"/>
        </c:if>
    </c:when>



    <%-- Military Policies --%>



    <c:when test="${param['policy'] == 'conscript'}">
        <c:set var="cost" value="${2}"/>
        <c:if test="${resultMain.rows[0].manpower < cost}">
            <c:set var="policyResult" value="You do not have enough manpower!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].manpower >= cost}">
            <c:set var="training">
                ${(resultMain.rows[0].army + 2 - resultMain.rows[0].army)
                / resultMain.rows[0].army * 100
                * (resultMain.rows[0].training / 100)}
            </c:set>
            <sql:update dataSource="${database}">
                UPDATE cloc SET manpower=manpower-2, army=army+2, training=training-?
                WHERE sess=? && manpower>=2
                <sql:param value="${training}"/>
                <sql:param value="${sess}"/>
            </sql:update>
            <c:set var="policyResult" value="You conscript thousands of men into your army!" scope="page"/>
        </c:if>
    </c:when>



    <c:when test="${param['policy'] == 'train'}">
        <c:set var="cost" value="${resultMain.rows[0].army *
                    (resultMain.rows[0].training * resultMain.rows[0].training) / 100}"/>
        <c:if test="${resultMain.rows[0].budget < cost}">
            <c:set var="policyResult" value="You do not have enough money!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].training > 100}">
            <c:set var="policyResult" value="Your men are already fully trained!" scope="page"/>
        </c:if>
        <c:if test="${resultMain.rows[0].budget >= cost}">
            <sql:update dataSource="${database}">
                UPDATE cloc SET training=training+5, budget=budget-?
                WHERE sess=? && training<100 && budget>=?
                <sql:param value="${cost}"/>
                <sql:param value="${sess}"/>
                <sql:param value="${cost}"/>
            </sql:update>
            <sql:update dataSource="${database}">
                UPDATE cloc SET training=100
                WHERE sess=? && training>100;
                <sql:param value="${sess}"/>
            </sql:update>
            <c:set var="policyResult" value="You train your men into a fine killing machine!" scope="page"/>
        </c:if>
    </c:when>



    <c:otherwise>
        <c:set var="policyResult" value="All your money seemingly disappears! How odd..."/>
    </c:otherwise>
</c:choose>