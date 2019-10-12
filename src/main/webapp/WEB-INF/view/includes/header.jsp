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
							<p class="positive">+${home.totalCoalProduction.mines} from mines<br></p>
							<p class="positive">+${home.totalCoalProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.totalCoalProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.totalIronProduction.mines} from mines<br></p>
							<p class="positive">+${home.totalIronProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.totalIronProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.totalOilProduction.wells} from wells<br></p>
							<p class="positive">+${home.totalOilProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.totalOilProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.totalSteelProduction.factories} from factories<br></p>
							<p class="positive">+${home.totalSteelProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.totalSteelProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.totalSteelProduction.factories} from factories<br></p>
							<p class="positive">+${home.totalSteelProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.totalSteelProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.totalResearchProduction.universities} from universities<br></p>
						</div>
					</div>
				</a>
			</li>
		</ul>
	</div>
</c:if>
