<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<c:if test="${sessionScope.user != null}">
	<script>
		window.onload = function(){
			document.addEventListener("click", test);
		};

		function test(event)
		{
			if(event.target.className.indexOf("noClose") === -1)
			{
				toggleTab(null);
			}
		}
	</script>
	<div class="header">
		<ul>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('budget')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/budget.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.budget * 1000}"/></p>
						<div id="budget" class="resourceTabUp">
							<p class="neutral">$<fmt:formatNumber value="${home.economy.budget}"/>k Total<br></p>
							<p class="positive">+<fmt:formatNumber value="${home.budgetChange}" maxFractionDigits="2"/>k from GDP</p>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('food')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/food.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.food}"/></p>
						<div id="food" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.food}"/> Total<br></p>
							<p class="positive">+<fmt:formatNumber value="${home.foodProduction.farming}" maxFractionDigits="2"/> from farming<br></p>
							<p class="negative">-<fmt:formatNumber value="${home.foodProduction.costs}" maxFractionDigits="2"/> from consumption</p>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('coal')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/coal.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.coal}"/></p>
						<div id="coal" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.coal}"/> Total<br></p>
							<c:forEach var="coal" items="${home.totalCoalProduction.entrySet()}">
								<c:if test="${coal.key != 'total' && coal.key != 'net'}">
									<c:if test="${coal.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${coal.value}" maxFractionDigits="2"/> from ${coal.key}</p><br>
									</c:if>
									<c:if test="${coal.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${coal.value}" maxFractionDigits="2"/> from ${coal.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalCoalProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalCoalProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber value="${home.totalCoalProduction.net}" maxFractionDigits="2"/> net gain</p>
							</c:if>
							<c:if test="${home.totalCoalProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber value="${home.totalCoalProduction.net}" maxFractionDigits="2"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('iron')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/iron.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.iron}"/></p>
						<div id="iron" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.iron}"/> Total<br></p>
							<c:forEach var="iron" items="${home.totalIronProduction.entrySet()}">
								<c:if test="${iron.key != 'total' && iron.key != 'net'}">
									<c:if test="${iron.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${iron.value}" maxFractionDigits="2"/> from ${iron.key}</p><br>
									</c:if>
									<c:if test="${iron.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${iron.value}" maxFractionDigits="2"/> from ${iron.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalIronProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalIronProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber value="${home.totalIronProduction.net}" maxFractionDigits="2"/> net gain</p>
							</c:if>
							<c:if test="${home.totalIronProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber value="${home.totalIronProduction.net}" maxFractionDigits="2"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('oil')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/oil.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.oil}"/></p>
						<div id="oil" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.oil}"/> Total<br></p>
							<c:forEach var="oil" items="${home.totalOilProduction.entrySet()}">
								<c:if test="${oil.key != 'total' && oil.key != 'net'}">
									<c:if test="${oil.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${oil.value}" maxFractionDigits="2"/> from ${oil.key}</p><br>
									</c:if>
									<c:if test="${oil.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${oil.value}" maxFractionDigits="2"/> from ${oil.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalOilProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalOilProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber value="${home.totalOilProduction.net}" maxFractionDigits="2"/> net gain</p>
							</c:if>
							<c:if test="${home.totalOilProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber value="${home.totalOilProduction.net}" maxFractionDigits="2"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('steel')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/steel.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.steel}"/></p>
						<div id="steel" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.steel}"/> Total<br></p>
							<c:forEach var="steel" items="${home.totalSteelProduction.entrySet()}">
								<c:if test="${steel.key != 'total' && steel.key != 'net'}">
									<c:if test="${steel.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${steel.value}" maxFractionDigits="2"/> from ${steel.key}</p><br>
									</c:if>
									<c:if test="${steel.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${steel.value}" maxFractionDigits="2"/> from ${steel.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalSteelProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalSteelProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber value="${home.totalSteelProduction.net}" maxFractionDigits="2"/> net gain</p>
							</c:if>
							<c:if test="${home.totalSteelProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber value="${home.totalSteelProduction.net}" maxFractionDigits="2"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('nitrogen')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/nitrogen.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.nitrogen}"/></p>
						<div id="nitrogen" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.nitrogen}"/> Total<br></p>
							<c:forEach var="nitrogen" items="${home.totalNitrogenProduction.entrySet()}">
								<c:if test="${nitrogen.key != 'total' && nitrogen.key != 'net'}">
									<c:if test="${nitrogen.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${nitrogen.value}" maxFractionDigits="2"/> from ${nitrogen.key}</p><br>
									</c:if>
									<c:if test="${nitrogen.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${nitrogen.value}" maxFractionDigits="2"/> from ${nitrogen.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalNitrogenProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalNitrogenProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber maxFractionDigits="2" value="${home.totalNitrogenProduction.net}"/> net gain</p>
							</c:if>
							<c:if test="${home.totalNitrogenProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber maxFractionDigits="2" value="${home.totalNitrogenProduction.net}"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab noClose" onclick="toggleTab('research')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/research.svg" alt="budget"/>
						<p class="noClose"><cloc:uiFormat value="${home.economy.research}"/></p>
						<div id="research" class="resourceTabUp">
							<p class="neutral"><fmt:formatNumber value="${home.economy.research}"/> Total<br></p>
							<c:forEach var="research" items="${home.totalResearchProduction.entrySet()}">
								<c:if test="${research.key != 'total' && research.key != 'net'}">
									<c:if test="${research.value > 0.0}">
										<p class="positive">+<fmt:formatNumber value="${research.value}" maxFractionDigits="2"/> from ${research.key}</p><br>
									</c:if>
									<c:if test="${research.value < 0.0}">
										<p class="negative"><fmt:formatNumber value="${research.value}" maxFractionDigits="2"/> from ${research.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalResearchProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalResearchProduction.net > 0}">
								<p class="positive"><br>+<fmt:formatNumber value="${home.totalResearchProduction.net}" maxFractionDigits="2"/> net gain</p>
							</c:if>
							<c:if test="${home.totalResearchProduction.net < 0}">
								<p class="negative"><br><fmt:formatNumber value="${home.totalResearchProduction.net}" maxFractionDigits="2"/> net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
		</ul>
	</div>
</c:if>
