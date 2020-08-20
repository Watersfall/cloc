<%@ include file="includes/top.jsp" %>
<div class="pagination">
	<a href="./all">All</a>
	<a href="./economy">Economy</a>
	<a href="./domestic">Domestic</a>
	<a href="./foreign">Foreign</a>
	<a href="./military">Military</a>
</div>
<br>
<div class="tiling">
	<div class="column">
		<div class="title">${category.name} Decisions</div>
		<div class="tile">
			<%--@elvariable id="decisions" type="java.util.List"--%>
			<%--@elvariable id="decision" type="com.watersfall.clocgame.model.decisions.Decision"--%>
			<%--@elvariable id="category" type="com.watersfall.clocgame.model.decisions.DecisionCategory"--%>
			<c:forEach var="decision" items="${decisions}">
				<c:if test="${decision.category == category || category == 'ALL'}">
					<div class="subtitle button_right">
						${decision.name}: ${home.getDecisionCostDisplayString(decision)}
						<button class="blue right">${decision.buttonText}</button>
					</div>
					<div class="left_text description">
						${decision.description}<br>
					</div>
					<br>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>
<%@ include file="includes/bottom.jsp" %>