<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="policy" type="com.watersfall.clocgame.action.PolicyActions"--%>
<%@ include file="includes/defaultTop.jsp" %>
<c:choose>
	<c:when test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:when>
	<c:otherwise>
		<h1>The Countryside</h1>
		<div>
			<div class="element farm">
				<h3>Farms</h3>
				<img src="${pageContext.request.contextPath}/images/city/farm.png" alt="farm"/>
				<br><br>
				<p>
					<fmt:formatNumber value="${home.freeLand}"/>km<sup>2</sup> of farmland producing
					<fmt:formatNumber value="${home.foodProduction.get('resource.total')}" maxFractionDigits="2"/> food per week
				</p>
				<p>
					Production per 250km<sup>2</sup> of farmland: <fmt:formatNumber value="${home.baseFoodProduction.get('farming.net')}" maxFractionDigits="2"/>
				</p>
				<div style="max-width: 15em; margin: auto">
					<ul class="bulletList">
						<c:forEach items="${home.baseFoodProduction.entrySet()}" var="production">
							<c:if test="${production.key != 'farming.net' && production.key != 'farming.total'}">
								<li><p class="textLeft"><fmt:formatNumber value="${production.value}" maxFractionDigits="2"/> ${home.getDisplayString(production.key)}</p></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>