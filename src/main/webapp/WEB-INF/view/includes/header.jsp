<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<c:if test="${home != null}">
	<div class="header">
		<ul>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/budget.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.budget * 1000}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/food.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.food}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/coal.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.coal}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/iron.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.iron}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/oil.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.oil}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/steel.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.steel}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/nitrogen.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.nitrogen}"/></p>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/ui/research.svg" alt="budget"/>
				<p><cloc:uiFormat value="${home.economy.research}"/></p>
			</li>
		</ul>
	</div>
</c:if>
