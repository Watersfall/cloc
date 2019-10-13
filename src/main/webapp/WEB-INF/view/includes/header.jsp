<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<c:if test="${sessionScope.user != null}">
	<script>
		window.onload = function(){
			document.getElementsByClassName("main")[0].addEventListener("click", test);
		};

		function test(event)
		{
			if(event.target.className !== "noClose")
			{
				showHideTab(null);
			}
		}
	</script>
	<div class="header">
		<ul>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('budget')">
						<img src="${pageContext.request.contextPath}/images/ui/budget.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.budget * 1000}"/></p>
						<div id="budget" class="resourceTab">
							<p class="neutral">$<fmt:formatNumber value="${home.economy.budget}"/>k Total<br></p>
							<p class="positive">+0k</p>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('food')">
						<img src="${pageContext.request.contextPath}/images/ui/food.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.food}"/></p>
						<div id="food" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.food}"/> Total<br></p>
							<p class="positive">+${home.foodProduction.farming} from farming<br></p>
							<p class="negative">-${home.foodProduction.costs} from consumption</p>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('coal')">
						<img src="${pageContext.request.contextPath}/images/ui/coal.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.coal}"/></p>
						<div id="coal" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.coal}"/> Total<br></p>
							<c:forEach var="coal" items="${home.totalCoalProduction.entrySet()}">
								<c:if test="${coal.key != 'total' && coal.key != 'net'}">
									<c:if test="${coal.value > 0.0}">
										<p class="positive">+${coal.value} from ${coal.key}</p><br>
									</c:if>
									<c:if test="${coal.value < 0.0}">
										<p class="negative">${coal.value} from ${coal.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalCoalProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalCoalProduction.net > 0}">
								<p class="positive"><br>+${home.totalCoalProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalCoalProduction.net < 0}">
								<p class="negative"><br>${home.totalCoalProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('iron')">
						<img src="${pageContext.request.contextPath}/images/ui/iron.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.iron}"/></p>
						<div id="iron" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.iron}"/> Total<br></p>
							<c:forEach var="iron" items="${home.totalIronProduction.entrySet()}">
								<c:if test="${iron.key != 'total' && iron.key != 'net'}">
									<c:if test="${iron.value > 0.0}">
										<p class="positive">+${iron.value} from ${iron.key}</p><br>
									</c:if>
									<c:if test="${iron.value < 0.0}">
										<p class="negative">${iron.value} from ${iron.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalIronProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalIronProduction.net > 0}">
								<p class="positive"><br>+${home.totalIronProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalIronProduction.net < 0}">
								<p class="negative"><br>${home.totalIronProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('oil')">
						<img src="${pageContext.request.contextPath}/images/ui/oil.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.oil}"/></p>
						<div id="oil" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.oil}"/> Total<br></p>
							<c:forEach var="oil" items="${home.totalOilProduction.entrySet()}">
								<c:if test="${oil.key != 'total' && oil.key != 'net'}">
									<c:if test="${oil.value > 0.0}">
										<p class="positive">+${oil.value} from ${oil.key}</p><br>
									</c:if>
									<c:if test="${oil.value < 0.0}">
										<p class="negative">${oil.value} from ${oil.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalOilProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalOilProduction.net > 0}">
								<p class="positive"><br>+${home.totalOilProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalOilProduction.net < 0}">
								<p class="negative"><br>${home.totalOilProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('steel')">
						<img src="${pageContext.request.contextPath}/images/ui/steel.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.steel}"/></p>
						<div id="steel" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.steel}"/> Total<br></p>
							<c:forEach var="steel" items="${home.totalSteelProduction.entrySet()}">
								<c:if test="${steel.key != 'total' && steel.key != 'net'}">
									<c:if test="${steel.value > 0.0}">
										<p class="positive">+${steel.value} from ${steel.key}</p><br>
									</c:if>
									<c:if test="${steel.value < 0.0}">
										<p class="negative">${steel.value} from ${steel.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalSteelProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalSteelProduction.net > 0}">
								<p class="positive"><br>+${home.totalSteelProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalSteelProduction.net < 0}">
								<p class="negative"><br>${home.totalSteelProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('nitrogen')">
						<img src="${pageContext.request.contextPath}/images/ui/nitrogen.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.nitrogen}"/></p>
						<div id="nitrogen" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.nitrogen}"/> Total<br></p>
							<c:forEach var="nitrogen" items="${home.totalNitrogenProduction.entrySet()}">
								<c:if test="${nitrogen.key != 'total' && nitrogen.key != 'net'}">
									<c:if test="${nitrogen.value > 0.0}">
										<p class="positive">+${nitrogen.value} from ${nitrogen.key}</p><br>
									</c:if>
									<c:if test="${nitrogen.value < 0.0}">
										<p class="negative">${nitrogen.value} from ${nitrogen.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalNitrogenProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalNitrogenProduction.net > 0}">
								<p class="positive"><br>+${home.totalNitrogenProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalNitrogenProduction.net < 0}">
								<p class="negative"><br>${home.totalNitrogenProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a>
					<div class="topTab" onclick="showHideTab('research')">
						<img src="${pageContext.request.contextPath}/images/ui/research.svg" alt="budget"/>
						<p><cloc:uiFormat value="${home.economy.research}"/></p>
						<div id="research" class="resourceTab">
							<p class="neutral"><fmt:formatNumber value="${home.economy.research}"/> Total<br></p>
							<c:forEach var="research" items="${home.totalResearchProduction.entrySet()}">
								<c:if test="${research.key != 'total' && research.key != 'net'}">
									<c:if test="${research.value > 0.0}">
										<p class="positive">+${research.value} from ${research.key}</p><br>
									</c:if>
									<c:if test="${research.value < 0.0}">
										<p class="negative">${research.value} from ${research.key}</p><br>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${home.totalResearchProduction.net == 0}">
								<p class="neutral"><br>No net change...</p>
							</c:if>
							<c:if test="${home.totalResearchProduction.net > 0}">
								<p class="positive"><br>+${home.totalResearchProduction.net} net gain</p>
							</c:if>
							<c:if test="${home.totalResearchProduction.net < 0}">
								<p class="negative"><br>${home.totalResearchProduction.net} net loss</p>
							</c:if>
						</div>
					</div>
				</a>
			</li>
		</ul>
	</div>
</c:if>
