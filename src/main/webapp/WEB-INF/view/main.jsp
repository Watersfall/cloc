<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:if test="${sessionScope.user == null}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${sessionScope.user != null}">
		<c:if test="${home.atWar}">
			<div class="element" style="width: 75%; margin-top: 1em;">
				<h2 class="negative">You are at war!</h2>
			</div>
		</c:if>
		<h1><c:out value="${home.cosmetic.nationTitle}"/><br>of<br>${home.cosmetic.nationName}</h1>
		<img class="veryLarge" src="/user/flag/${home.cosmetic.flag}" alt="flag"/>
		<p><br><c:out value="${home.cosmetic.description}"/></p><br>
		<img class="veryLong" src="/user/portrait/${home.cosmetic.portrait}" alt="portrait"/>
		<h1><c:out value="${home.cosmetic.leaderTitle}"/><c:out value=" "/><c:out value=" ${home.cosmetic.username}"/></h1>
		<h2>Domestic</h2>
		<table class="standardTable nationTable">
			<tr>
				<td><p>Government</p></td>
				<td><p><cloc:government value="${home.domestic.government}"/></p></td>
			</tr>
			<tr>
				<td><p>Approval</p></td>
				<td onclick="toggleTab('Approval');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><cloc:approval value="${home.domestic.approval}"/></p>
					<ui:dropdown value="${home.domestic.approval}" name="Approval" map="${home.approvalChange}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Stability</p></td>
				<td onclick="toggleTab('Stability');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><cloc:stability value="${home.domestic.stability}"/></p>
					<ui:dropdown value="${home.domestic.stability}" name="Stability" map="${home.stabilityChange}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Land</p></td>
				<td onclick="toggleTab('Land');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.freeLand}"/> km<sup>2</sup> / <fmt:formatNumber value="${home.domestic.land}"/> km<sup>2</sup></p>
					<ui:dropdown value="${home.domestic.land}" name="Land" map="${home.landUsage}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Population</p></td>
				<td onclick="toggleTab('Population');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.totalPopulation}"/> People</p>
					<ui:dropdown value="${home.totalPopulation}" name="Population" map="${home.populationGrowth}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Manpower</p></td>
				<td onclick="toggleTab('Manpower');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.freeManpower}"/> Bodies</p>
					<ui:dropdown value="${home.totalManpower}" name="Manpower" map="${home.usedManpower}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Rebel Threat</p></td>
				<td><p><cloc:rebels value="${home.domestic.rebels}"/></p></td>
			</tr>
		</table>
		<h2>Economy</h2>
		<table class="standardTable nationTable">
			<tr>
				<td><p>Economic System</p></td>
				<td><p><cloc:economic value="${home.economy.economic}"/></p></td>
			</tr>
			<tr>
				<td><p>Gross Domestic Product</p></td>
				<td><p><fmt:formatNumber value="${home.economy.gdp}"/> Million</p></td>
			</tr>
			<tr>
				<td><p>Growth</p></td>
				<td onclick="toggleTab('Growth')">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.economy.growth}"/> Million per month</p>
					<ui:dropdown value="${home.economy.growth}" name="Growth" map="${home.growthChange}" nation="${home}"/>
				</td>
			</tr>
		</table>
		<h2>Foreign</h2>
		<table class="standardTable nationTable">
			<tr>
				<td><p>Region</p></td>
				<td><a href="${pageContext.request.contextPath}/map/region/${home.foreign.region.name}"><p>${home.foreign.region.name}</p></a></td>
			</tr>
			<tr>
				<td><p>Official Alignment</p></td>
				<td><p><cloc:alignment value="${home.foreign.alignment}"/></p></td>
			</tr>
			<tr>
				<td><p>Treaty Membership</p></td>
				<td>
					<c:if test="${home.treaty == null}">
						<p>None</p>
					</c:if>
					<c:if test="${home.treaty != null}">
						<p>${home.treaty.treatyUrl}</p>
					</c:if>
				</td>
			</tr>
		</table>
		<h2>Military</h2>
		<table class="standardTable nationTable">
			<caption><p>Army</p></caption>
			<tr>
				<td><p>Active Personnel</p></td>
				<td><p><fmt:formatNumber value="${home.army.size}"/>k Personnel</p></td>
			</tr>
			<tr>
				<td><p>Training</p></td>
				<td><p><fmt:formatNumber value="${home.army.training}"/>%</p></td>
			</tr>
			<tr>
				<td><p>Equipment</p></td>
				<td onclick="toggleTab('Equipment');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.totalInfantryEquipment}"/>/<fmt:formatNumber value="${home.army.size * 1000}"/> Required</p>
					<ui:dropdown value="${home.totalInfantryEquipment}" name="Equipment" map="${home.equipment}" nation="${home}"/>
				</td>
			</tr>
			<tr>
				<td><p>Artillery</p></td>
				<td><p><fmt:formatNumber value="${home.army.artillery}"/> Pieces</p></td>
			</tr>
		</table>
		<br><br>
		<table class="standardTable nationTable">
			<caption><p>Navy</p></caption>
			<tr>
				<td><p>Destroyers</p></td>
				<td><p><fmt:formatNumber value="${home.military.destroyers}"/></p></td>
			</tr>
			<tr>
				<td><p>Cruisers</p></td>
				<td><p><fmt:formatNumber value="${home.military.cruisers}"/></p></td>
			</tr>
			<tr>
				<td><p>Battleships</p></td>
				<td><p><fmt:formatNumber value="${home.military.battleships}"/></p></td>
			</tr>
			<tr>
				<td><p>Submarines</p></td>
				<td><p><fmt:formatNumber value="${home.military.submarines}"/></p></td>
			</tr>
			<tr>
				<td><p>Troop Transports</p></td>
				<td><p><fmt:formatNumber value="${home.military.transports}"/></p></td>
			</tr>
		</table>
		<br><br>
		<table class="standardTable nationTable">
			<caption><p>Airforce</p></caption>
		</table>
		<h2>Wars</h2>
		<c:if test="${home.atWar}">
			<c:if test="${home.offensive != null}">
				Offensive war against ${home.offensive.nationUrl}<br>
			</c:if>
			<c:if test="${home.defensive != null}">
				Defensive war against ${home.defensive.nationUrl}
			</c:if>
		</c:if>
		<c:if test="${!home.atWar}">
			<i>None</i>
		</c:if>
		<br><br>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>