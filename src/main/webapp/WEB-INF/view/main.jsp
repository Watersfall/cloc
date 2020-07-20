<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<% pageContext.setAttribute("netApproval", TextKey.Approval.NET); %>
<% pageContext.setAttribute("netStability", TextKey.Stability.NET); %>
<% pageContext.setAttribute("netManpower", TextKey.Manpower.NET); %>
<% pageContext.setAttribute("netGrowth", TextKey.Growth.NET); %>
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
		<h2>Modifiers</h2>
		<table class="standardTable nationTable">
			<c:forEach items="${home.modifiers}" var="modifier">
				<tr>
					<td>${modifier.type.name()}</td>
					<td>${modifier.type.effects}</td>
				</tr>
			</c:forEach>
		</table>
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
					<div class="toggleable detailsDown" id="Approval">
						<p>${home.domestic.approval}% Approval</p>
						<c:choose>
							<c:when test="${home.approvalChange.get(netApproval) > 0}">
								<p class="positive">+${home.approvalChange.get(netApproval)}${netApproval.text}</p>
							</c:when>
							<c:when test="${home.approvalChange.get(netApproval) < 0}">
								<p class="negative">${home.approvalChange.get(netApproval)}${netApproval.text}</p>
							</c:when>
							<c:otherwise>
								<p class="neutral">0${netApproval.text}</p>
							</c:otherwise>
						</c:choose>
						<ul class="bulletList">
							<c:forEach items="${home.approvalChange.entrySet()}" var="i">
								<c:if test="${i.key != 'NET'}">
									<c:choose>
										<c:when test="${i.value > 0}">
											<li><p class="positive">+${i.value}${i.key.text}</p></li>
										</c:when>
										<c:when test="${i.value < 0}">
											<li><p class="negative">${i.value}${i.key.text}</p></li>
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><p>Stability</p></td>
				<td onclick="toggleTab('Stability');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><cloc:stability value="${home.domestic.stability}"/></p>
					<div class="toggleable detailsDown" id="Stability">
						<p>${home.domestic.stability}% Stability</p>
						<c:choose>
							<c:when test="${home.stabilityChange.get(netStability) > 0}">
								<p class="positive">+${home.stabilityChange.get(netStability)}${netStability.text}</p>
							</c:when>
							<c:when test="${home.stabilityChange.get(netStability) < 0}">
								<p class="negative">${home.stabilityChange.get(netStability)}${netStability.text}</p>
							</c:when>
							<c:otherwise>
								<p class="neutral">0${netStability.text}</p>
							</c:otherwise>
						</c:choose>
						<ul class="bulletList">
							<c:forEach items="${home.stabilityChange.entrySet()}" var="i">
								<c:if test="${i.key != 'NET'}">
									<c:choose>
										<c:when test="${i.value > 0}">
											<li><p class="positive">+${i.value}${i.key.text}</p></li>
										</c:when>
										<c:when test="${i.value < 0}">
											<li><p class="negative">${i.value}${i.key.text}</p></li>
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
						</ul>

					</div>
				</td>
			</tr>
			<tr>
				<td><p>Land</p></td>
				<td onclick="toggleTab('Land');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.freeLand}"/> km<sup>2</sup> / <fmt:formatNumber value="${home.domestic.land}"/> km<sup>2</sup></p>
					<div class="toggleable detailsDown" id="Land">
						<p><fmt:formatNumber value="${home.totalLandUsage}"/>km<sup>2</sup> Total Land Used</p>
						<ul class="bulletList">
							<c:forEach items="${home.landUsage.entrySet()}" var="i">
								<li><p>${i.key}</p></li>
								<ul class="bulletList">
									<c:forEach items="${i.value.entrySet()}" var="entry">
										<c:if test="${entry.value > 0}">
											<li><p class="negative"><fmt:formatNumber value="${entry.value}"/>km<sup>2</sup>${entry.key.text}</p></li>
										</c:if>
									</c:forEach>
								</ul>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><p>Population</p></td>
				<td><p><fmt:formatNumber value="${home.totalPopulation}"/> People</p></td>
			</tr>
			<tr>
				<td><p>Manpower</p></td>
				<td onclick="toggleTab('Manpower');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.freeManpower}"/> Bodies</p>
					<div class="toggleable detailsDown" id="Manpower">
						<p><fmt:formatNumber value="${home.totalManpower}"/> Total Manpower</p>
						<ul class="bulletList">
							<c:forEach items="${home.usedManpower.entrySet()}" var="i">
								<c:if test="${i.value < 0}">
									<li><p class="negative"><fmt:formatNumber value="${i.value * -1}"/>${i.key.text}</p></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
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
					<div class="toggleable detailsDown" id="Growth">
						<c:choose>
							<c:when test="${home.growthChange.get(netGrowth) > 0}">
								<p class="positive">+${home.growthChange.get(netGrowth)}${netGrowth.text}</p>
							</c:when>
							<c:when test="${home.growthChange.get(netGrowth) < 0}">
								<p class="negative">${home.growthChange.get(netGrowth)}${netGrowth.text}</p>
							</c:when>
							<c:otherwise>
								<p class="neutral">0${netGrowth.text}</p>
							</c:otherwise>
						</c:choose>
						<ul>
							<c:forEach items="${home.growthChange.entrySet()}" var="i">
								<c:if test="${i.key != 'NET'}">
									<c:choose>
										<c:when test="${i.value > 0}">
											<li><p class="positive">+${i.value}${i.key.text}</p></li>
										</c:when>
										<c:when test="${i.value < 0}">
											<li><p class="negative">${i.value}${i.key.text}</p></li>
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		<h2>Foreign</h2>
		<table class="standardTable nationTable">
			<tr>
				<td><p>Region</p></td>
				<td><a href="${pageContext.request.contextPath}/map/region/${home.foreign.region.name()}"><p>${home.foreign.region.name}</p></a></td>
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
					<div class="toggleable detailsDown" id="Equipment">
						<p><fmt:formatNumber value="${home.totalInfantryEquipment}"/> Total Infantry Equipment</p>
						<ul>
							<c:forEach items="${home.equipment.entrySet()}" var="i">
								<c:if test="${i.value > 0}">
									<li><p><fmt:formatNumber value="${i.value}"/>${' '}${i.key}</p></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><p>Artillery</p></td>
				<td><p><fmt:formatNumber value="${home.army.artillery}"/> Pieces / <fmt:formatNumber value="${home.army.size * 5}"/> max </p></td>
			</tr>
			<tr>
				<td><p>Fortification</p></td>
				<td onclick="toggleTab('Fortification');">
					<div style="background: red; position: relative;">
						<div style="background: green; width: ${home.army.fortification / 100}%">
							<div style="background: black; width: 0.1em; left: ${home.maximumFortificationLevel / 100}%; height: 100%; position: absolute; top: 0;"></div>
							<p class="neutral">Fortification:&nbsp;${home.army.fortification / 100}%</p>
						</div>
					</div>
					<div class="toggleable detailsDown" id="Fortification">
						<c:set var="net" value="fortification.net"/>
						<c:if test="${home.fortificationChange.get(net) > 0}">
							<p class="positive"><fmt:formatNumber value="${home.fortificationChange.get(net) / 100}" maxFractionDigits="2"/>${home.getDisplayString(net)}</p>
						</c:if>
						<c:if test="${home.fortificationChange.get(net) < 0}">
							<p class="negative"><fmt:formatNumber value="${home.fortificationChange.get(net) / 100}" maxFractionDigits="2"/>${home.getDisplayString(net)}</p>
						</c:if>
						<c:if test="${home.fortificationChange.get(net) == 0}">
							<p>No net change...</p>
						</c:if>
						<ul>
							<c:forEach var="entry" items="${home.fortificationChange}">
								<c:if test="${not fn:contains(entry.key, 'net')}">
									<li>
										<c:if test="${entry.value > 0}">
											<p class="positive"><fmt:formatNumber value="${entry.value / 100}" maxFractionDigits="2"/>${home.getDisplayString(entry.key)}</p>
										</c:if>
										<c:if test="${entry.value < 0}">
											<p class="negative"><fmt:formatNumber value="${entry.value / 100}" maxFractionDigits="2"/>${home.getDisplayString(entry.key)}</p>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
						<br>
						<p>Max Fortification: <fmt:formatNumber value="${home.maximumFortificationLevel / 100}" maxFractionDigits="2"/>%</p>
						<ul>
							<c:forEach var="entry" items="${home.maximumFortificationLevelMap}">
								<li>
									<c:if test="${not fn:contains(entry.key, 'net')}">
										<c:if test="${entry.value > 0}">
											<p class="positive">+${entry.value / 100}${home.getDisplayString(entry.key)}</p>
										</c:if>
										<c:if test="${entry.value < 0}">
											<p class="negative">${entry.value / 100}${home.getDisplayString(entry.key)}</p>
										</c:if>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		<br><br>
		<table class="standardTable nationTable">
			<caption><p>Airforce</p></caption>
			<tr>
				<td><p>Fighters</p></td>
				<td onclick="toggleTab('Fighters');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.fighterCount}"/> Planes</p>
					<div class="toggleable detailsDown" id="Fighters">
						<p><fmt:formatNumber value="${home.fighterCount}"/> Total Fighters</p>
						<ul>
							<c:forEach items="${fighters}" var="fighter">
								<c:if test="${home.getFighter(fighter) > 0}">
									<li>
										<c:if test="${home.getFighter(fighter) > 1}">
											<p>${home.getFighter(fighter)} ${' '.concat(fighter.name)}s</p>
										</c:if>
										<c:if test="${home.getFighter(fighter) == 1}">
											<p>${home.getFighter(fighter)} ${' '.concat(fighter.name)}</p>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><p>Bombers</p></td>
				<td onclick="toggleTab('Bombers');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.bomberCount}"/> Planes</p>
					<div class="toggleable detailsDown" id="Bombers">
						<p><fmt:formatNumber value="${home.bomberCount}"/> Total Bombers</p>
						<ul>
							<c:forEach items="${bombers}" var="bomber">
								<c:if test="${home.getBomber(bomber) > 0}">
									<li>
										<c:if test="${home.getBomber(bomber) > 1}">
											<p>${home.getBomber(bomber)} ${' '.concat(bomber.name)}s</p>
										</c:if>
										<c:if test="${home.getBomber(bomber) == 1}">
											<p>${home.getBomber(bomber)} ${' '.concat(bomber.name)}</p>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><p>Recon Aircraft</p></td>
				<td onclick="toggleTab('Recon');">
					<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
					<p class="clickable"><fmt:formatNumber value="${home.reconCount}"/> Planes</p>
					<div class="toggleable detailsDown" id="Recon">
						<p><fmt:formatNumber value="${home.reconCount}"/> Total Recon Aircraft</p>
						<ul>
							<c:forEach items="${reconPlanes}" var="recon">
								<c:if test="${home.getReconPlane(recon) > 0}">
									<li>
										<c:if test="${home.getReconPlane(recon) > 1}">
											<p>${home.getReconPlane(recon)} ${' '.concat(recon.name)}s</p>
										</c:if>
										<c:if test="${home.getReconPlane(recon) == 1}">
											<p>${home.getReconPlane(recon)} ${' '.concat(recon.name)}</p>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</td>
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
		<h2>Wars</h2>
		<c:if test="${home.atWar}">
			<c:if test="${home.offensive != null}">
				Offensive war against ${home.offensive.defender.nationUrl}<br>
			</c:if>
			<c:if test="${home.defensive != null}">
				Defensive war against ${home.defensive.attacker.nationUrl}
			</c:if>
		</c:if>
		<c:if test="${!home.atWar}">
			<i>None</i>
		</c:if>
		<br><br>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>