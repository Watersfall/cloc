<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="offensive" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="defensive" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="production" type="java.util.HashMap"--%>
<%--@elvariable id="food" type="java.util.HashMap"--%>
<%--@elvariable id="population" type="java.util.HashMap"--%>
<div class="main">
	<c:if test="${sessionScope.user == null}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${sessionScope.user != null}">
		<c:if test="${home.offensive != 0 || home.defensive != 0}">
			<p>You are at war!</p>
		</c:if>
		<div class="nation">
			<h1><c:out value="${home.cosmetic.nationTitle}"/><br> of <br><c:out value="${home.cosmetic.nationName}"/></h1>
			<img class="flag" src="https://imgur.com/${home.cosmetic.flag}" alt="flag">
			<p><c:out value="${home.cosmetic.description}"/></p>
			<img class="leader" src="https://imgur.com/${home.cosmetic.portrait}" alt="flag">
			<h1><c:out value="${home.cosmetic.leaderTitle}"/>: <c:out value="${home.cosmetic.username}"/></h1>
		</div>
		<h1>City shit</h1>
		<table id="nation">
			<c:forEach var="i" items="${home.cities.cities}">
				<tr>
					<td>${i.value.name}</td>
					<td><a href="${pageContext.request.contextPath}/cities.jsp?id=${i.key}">View</a></td>
				</tr>
			</c:forEach>
		</table>
		<h1>Domestic</h1>
		<table id="nation">
			<tr>
				<td>Government</td>
				<td><cloc:government value="${home.domestic.government}"/></td>
			</tr>
			<tr>
				<td>Approval</td>
				<td>
					<div class="dropdown">
                        <span>
                            <cloc:approval value="${home.domestic.approval}"/>
                        </span>
						<div class="dropdown-content">
							<i><c:out value="${home.domestic.approval}"/>%</i>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>Stability</td>
				<td>
					<div class="dropdown">
                        <span>
                            <cloc:stability value="${home.domestic.stability}"/>
                        </span>
						<div class="dropdown-content">
							<i><c:out value="${home.domestic.stability}"/>%</i>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>Land</td>
				<td><fmt:formatNumber value="${home.freeLand}"/> km<sup>2</sup> / <fmt:formatNumber value="${home.domestic.land}"/> km<sup>2</sup></td>
			</tr>
			<tr>
				<td>Population</td>
				<td>
					<div class="dropdown">
                        <span>
								${home.domestic.population}
						</span>
						<div class="dropdown-content">
							<p class="positive">+<fmt:formatNumber value="${population.base}" maxFractionDigits="2"/>% from base growth<br></p>
							<p class="${population.policies > 1 ? 'positive' : 'negative'}">Modified by <fmt:formatNumber value="${population.policies * 100}" maxFractionDigits="2"/>% from policies<br></p>
							<p class="${population.unemployment > 1 ? 'positive' : 'negative'}">Modified by <fmt:formatNumber value="${population.unemployment * 100}" maxFractionDigits="2"/>% from ${population.unemployment > 0 ? 'unemployment' : 'over population'}<br></p>
							<p class="${population.total > 0 ? 'positive' : 'negative'}">${population.total >= 0 ? '+' : ''}<fmt:formatNumber value="${population.total}" maxFractionDigits="2"/>% change total</p>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>Rebel Threat</td>
				<td>
					<div class="dropdown">
                        <span>
                            <cloc:rebels value="${home.domestic.rebels}"/>
                        </span>
						<div class="dropdown-content">
							<i><c:out value="${home.domestic.rebels}"/>%</i>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<h1>Economy</h1>
		<table id="nation">
			<tr>
				<td>Economic System</td>
				<td><cloc:economic value="${home.economy.economic}"/></td>
			</tr>
			<tr>
				<td>Gross Domestic Product</td>
				<td>$<c:out value="${home.economy.gdp}"/> Million</td>
			</tr>
			<tr>
				<td>Growth</td>
				<td>
					<div class="dropdown">
                        <span>
								${home.economy.growth}
						</span>
						<div class="dropdown-content">

						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>Coal Production</td>
				<td>
					<div class="dropdown">
                        <span>
								${production.coal.net}
						</span>
						<div class="dropdown-content">
							+${production.coal.mines} from coal mines<br>
							+${production.coal.bonus} from railways<br>
							-${production.coal.costs} from factories
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>Coal Stockpile</td>
				<td><c:out value="${home.economy.coal}"/> Hundred Tons</td>
			</tr>
			<tr>
				<td>Iron Stockpile</td>
				<td><c:out value="${home.economy.iron}"/> Hundred Tons</td>
			</tr>
			<tr>
				<td>Oil Stockpile</td>
				<td><c:out value="${home.economy.oil}"/> Mmbls</td>
			</tr>
			<tr>
				<td>Steel Stockpile</td>
				<td><c:out value="${home.economy.steel}"/> Tons</td>
			<tr>
				<td>Food</td>
				<td>
					<div class="dropdown">
						<span>
								${home.economy.food}
						</span>
						<div class="dropdown-content">
							<p class="positive">+${food.farming} from farming</p>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<h1>Foreign</h1>
		<table id="nation">
			<tr>
				<td>Region</td>
				<td><c:out value="${home.foreign.region.name}"/></td>
			</tr>
			<tr>
				<td>Official Alignment</td>
				<td><cloc:alignment value="${home.foreign.alignment}"/></td>
			</tr>
			<tr>
				<td>Treaty Membership</td>
				<td>None</td>
			</tr>
		</table>
		<h1>Military</h1>
		<table id="nation">
			<caption>Army</caption>
			<tr>
				<th>Region</th>
				<th>Size</th>
				<th>Training</th>
				<th>Equipment</th>
				<th>Reinforce</th>
			</tr>
			<c:forEach var="i" items="${home.armies.armies}">
				<tr>
					<td>${i.value.region.name}</td>
					<td>${i.value.army}</td>
					<td>${i.value.training}</td>
					<td>${i.value.weapons}</td>
					<td>${i.value.id}</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<table id="nation">
			<caption>Navy</caption>
			<tr>
				<td>Destroyers</td>
				<td><c:out value="${home.military.destroyers}"/></td>
			</tr>
			<tr>
				<td>Cruisers</td>
				<td><c:out value="${home.military.cruisers}"/></td>
			</tr>
			<tr>
				<td>Battleships</td>
				<td><c:out value="${home.military.battleships}"/></td>
			</tr>
			<tr>
				<td>Submarines</td>
				<td><c:out value="${home.military.submarines}"/></td>
			</tr>
			<tr>
				<td>Troop Transports</td>
				<td><c:out value="${home.military.transports}"/></td>
			</tr>
		</table>
		<br>
		<table id="nation">
			<caption>Airforce</caption>
			<tr>
				<td>Fighters</td>
				<td><c:out value="${home.military.fighters}"/></td>
			</tr>
			<tr>
				<td>Zeppelins</td>
				<td><c:out value="${home.military.zeppelins}"/></td>
			</tr>
		</table>

		<h1>Wars</h1>
		<c:if test="${offensive != null}">
			<p>Offensive: <a href="${pageContext.request.contextPath}/nation.jsp?id=${offensive.id}">${offensive.cosmetic.nationName}</a></p>
		</c:if>
		<c:if test="${defensive != null}">
			<p>Defensive: <a href="${pageContext.request.contextPath}/nation.jsp?id=${defensive.id}">${defensive.cosmetic.nationName}</a></p>
		</c:if>
		<c:if test="${offensive == null && defensive == null}">
			<p>None</p>
		</c:if>
	</c:if>
	<p><br><br><br></p>
</div>
<p><br><br><br></p>
</body>
</html>