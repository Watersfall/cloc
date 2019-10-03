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
							<p class="positive">+${home.cities.totalCoalProduction.mines} from mines<br></p>
							<p class="positive">+${home.cities.totalCoalProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.cities.totalCoalProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.cities.totalIronProduction.mines} from mines<br></p>
							<p class="positive">+${home.cities.totalIronProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.cities.totalIronProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.cities.totalOilProduction.wells} from wells<br></p>
							<p class="positive">+${home.cities.totalOilProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.cities.totalOilProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.cities.totalSteelProduction.factories} from factories<br></p>
							<p class="positive">+${home.cities.totalSteelProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.cities.totalSteelProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.cities.totalSteelProduction.factories} from factories<br></p>
							<p class="positive">+${home.cities.totalSteelProduction.bonus} from railroads<br></p>
							<p class="negative">-${home.cities.totalSteelProduction.costs} from upkeep<br></p>
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
							<p class="positive">+${home.cities.totalResearchProduction.universities} from universities<br></p>
						</div>
					</div>
				</a>
			</li>
		</ul>
	</div>
</c:if>
