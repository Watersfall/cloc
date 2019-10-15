<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="offensive" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="defensive" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="production" type="java.util.HashMap"--%>
<%--@elvariable id="food" type="java.util.HashMap"--%>
<%--@elvariable id="population" type="java.util.HashMap"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:if test="${sessionScope.user == null}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${sessionScope.user != null}">
		<c:if test="${home.offensive != 0 || home.defensive != 0}">
			<div class="cityElements" style="width: 75%;">
				<p>Your nation is at war!</p>
			</div>
		</c:if>
		<div class="nation">
			<h1><c:out value="${home.cosmetic.nationTitle}"/><br> of <br><c:out value="${home.cosmetic.nationName}"/></h1>
			<img class="flag" src="https://imgur.com/${home.cosmetic.flag}" alt="flag">
			<p><c:out value="${home.cosmetic.description}"/></p>
			<img class="leader" src="https://imgur.com/${home.cosmetic.portrait}" alt="flag">
			<h1><c:out value="${home.cosmetic.leaderTitle}"/>: <c:out value="${home.cosmetic.username}"/></h1>
		</div>
		<h1>Domestic</h1>
		<table id="nation">
			<tr>
				<td>Government</td>
				<td><cloc:government value="${home.domestic.government}"/></td>
			</tr>
			<tr>
				<td>Approval</td>
				<td>
					<div class="noClose" onclick="showHideTab('approval')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						<cloc:approval value="${home.domestic.approval}"/>
					</div>
					<div class="resourceTab" id="approval">
						<p class="neutral">${home.domestic.approval}% Approval</p>
						<br>
						<br>
						<c:choose>
							<c:when test="${home.approvalChange.policies != 0}">
								<p class="${home.approvalChange.policies > 0 ? 'positive' : 'negative'}">
									${home.approvalChange.policies > 0 ? '+' : ''}${home.approvalChange.policies}% from policies
								</p>
							</c:when>
							<c:otherwise>
								<p class="neutral">No change...</p>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
			<tr>
				<td>Stability</td>
				<td>
					<div class="noClose" onclick="showHideTab('stability')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						<cloc:stability value="${home.domestic.stability}"/>
					</div>
					<div class="resourceTab" id="stability">
						<p class="neutral">${home.domestic.stability}% Stability</p>
						<br>
						<br>
						<p class="positive">+${home.stabilityChange.total}% from default</p>
					</div>
				</td>
			</tr>
			<tr>
				<td>Land</td>
				<td>
					<div class="noClose" onclick="showHideTab('land')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						<fmt:formatNumber value="${home.freeLand}"/> km<sup>2</sup> / <fmt:formatNumber value="${home.domestic.land}"/> km<sup>2</sup>
					</div>
					<div class="resourceTab" id="land">
						<p class="neutral">Land Usage</p><br>
						<c:forEach var="city" items="${home.landUsageByCityAndType}">
							<p class="neutral">${city.key}</p>
							<ul style="margin: 0; padding: 0 0 0 2em; list-style: circle">
							<c:forEach var="land" items="${city.value}">
								<c:if test="${land.value > 0}">
									<li class="neutral"><fmt:formatNumber value="${land.value}"/> km<sup>2</sup> from ${land.key}</li>
								</c:if>
							</c:forEach>
							</ul>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td>Population</td>
				<td>
					<div class="noClose" onclick="showHideTab('population')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						<fmt:formatNumber value="${home.domestic.population}"/> People
					</div>
					<div class="resourceTab" id="population">
						<p class="positive">+<fmt:formatNumber value="${population.base}" maxFractionDigits="2"/>% from base growth<br></p>
						<p class="${population.policies > 1 ? 'positive' : 'negative'}">Modified by <fmt:formatNumber value="${population.policies * 100}" maxFractionDigits="2"/>% from policies<br></p>
						<p class="${population.unemployment > 1 ? 'positive' : 'negative'}">Modified by <fmt:formatNumber value="${population.unemployment * 100}" maxFractionDigits="2"/>% from ${population.unemployment > 0 ? 'unemployment' : 'over population'}<br></p>
						<p class="${population.total > 0 ? 'positive' : 'negative'}">${population.total >= 0 ? '+' : ''}<fmt:formatNumber value="${population.total}" maxFractionDigits="2"/>% change total</p>
					</div>
				</td>
			</tr>
			<tr>
				<td>Manpower</td>
				<td>
					<div class="noClose" onclick="showHideTab('manpower')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
 						<fmt:formatNumber value="${home.freeManpower}"/>
					</div>
					<div class="resourceTab" id="manpower">
						<p class="neutral"><fmt:formatNumber value="${home.totalManpower}"/> Total</p><br><br>
						<c:set var="manpower" value="${home.usedManpower}"/>
						<c:forEach var="entry" items="${manpower}">
							<p class="negative"><fmt:formatNumber value="${entry.value}"/> in ${entry.key}<br></p>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td>Rebel Threat</td>
				<td><cloc:rebels value="${home.domestic.rebels}"/></td>
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
				<td>$<fmt:formatNumber value="${home.economy.gdp}"/> Million</td>
			</tr>
			<tr>
				<td>Growth</td>
				<td>
					<div class="noClose" onclick="showHideTab('growth')">
						<img class="noClose" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow"/>
						<fmt:formatNumber value="${home.economy.growth}"/> Million per Month
					</div>
					<div class="resourceTab" id="growth">
						<c:set value="${home.growthChange}" var="growth"/>
						<c:if test="${growth.factories != 0}">
							<p class="positive">+${growth.factories} from factories</p><br>
						</c:if>
						<c:if test="${growth.army != 0}">
							<p class="negative">-${growth.army} from army upkeep</p><br>
						</c:if>
						<c:if test="${growth.factories == 0 && growth.army == 0}">
							<p class="neutral">No change...</p>
						</c:if>
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
				<td>
					<c:if test="${home.treaty != null}">
						<a href="${pageContext.request.contextPath}/treaty.jsp?id=${home.treaty.id}">${home.treaty.name}</a>
					</c:if>
					<c:if test="${home.treaty == null}">
						None
					</c:if>
				</td>
			</tr>
		</table>
		<h1>Military</h1>
		<table id="nation">
			<caption>Army</caption>
			<tr>
				<td>Size</td>
				<td><fmt:formatNumber value="${home.army.size}"/>k Personnel</td>
			</tr>
			<tr>
				<td>Training</td>
				<td>${home.army.training}%</td>
			</tr>
			<tr>
				<td>Equipment</td>
				<td><fmt:formatNumber value="${home.army.musket}"/> / <fmt:formatNumber value="${home.army.size * 1000}"/> needed</td>
			</tr>
		</table>
		<br><br>
		<table id="nation">
			<caption>Navy</caption>
			<tr>
				<td>Destroyers</td>
				<td><fmt:formatNumber value="${home.military.destroyers}"/></td>
			</tr>
			<tr>
				<td>Cruisers</td>
				<td><fmt:formatNumber value="${home.military.cruisers}"/></td>
			</tr>
			<tr>
				<td>Battleships</td>
				<td><fmt:formatNumber value="${home.military.battleships}"/></td>
			</tr>
			<tr>
				<td>Submarines</td>
				<td><fmt:formatNumber value="${home.military.submarines}"/></td>
			</tr>
			<tr>
				<td>Troop Transports</td>
				<td><fmt:formatNumber value="${home.military.transports}"/></td>
			</tr>
		</table>
		<br><br>
		<table id="nation">
			<caption>Airforce</caption>
			<tr>
				<td>Fighters</td>
				<td><fmt:formatNumber value="${home.military.fighters}"/></td>
			</tr>
			<tr>
				<td>Zeppelins</td>
				<td><fmt:formatNumber value="${home.military.zeppelins}"/></td>
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
</div></div>
</body>
</html>