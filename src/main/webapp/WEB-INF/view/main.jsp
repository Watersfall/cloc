<% pageContext.setAttribute("netApproval", TextKey.Approval.NET); %>
<% pageContext.setAttribute("netStability", TextKey.Stability.NET); %>
<% pageContext.setAttribute("netManpower", TextKey.Manpower.NET); %>
<% pageContext.setAttribute("netGrowth", TextKey.Growth.NET); %>
<% pageContext.setAttribute("netFortification", TextKey.Fortification.NET); %>
<%@ include file="includes/top.jsp" %>
	<c:if test="${home == null}">
		<p>You have visited this page incorrectly</p>
	</c:if>
	<c:if test="${home != null}">
		<div id="nation" class="tiling">
			<div id="column_1" class="column">
				<div id="stats" class="tile">
					<div class="title">Stats</div>
					<table class="nation">
						<thead class="blue">
						<tr>
							<td colspan="2">Domestic</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Government</td>
						</tr>
						<tr>
							<td colspan="2"><cloc:government value="${home.domestic.government}"/></td>
						</tr>
						<tr>
							<td colspan="2">Approval</td>
						</tr>
						<tr>
							<td>${home.domestic.approval}%</td>
							<td>
								<c:if test="${home.approvalChange.get(netApproval) > 0}">
									+<fmt:formatNumber value="${home.approvalChange.get(netApproval)}"/> per month
								</c:if>
								<c:if test="${home.approvalChange.get(netApproval) < 0}">
									<fmt:formatNumber value="${home.approvalChange.get(netApproval)}"/> per month
								</c:if>
								<c:if test="${home.approvalChange.get(netApproval) == 0}">
									No change
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">Stability</td>
						</tr>
						<tr>
							<td>${home.domestic.stability}%</td>
							<td>
								<c:if test="${home.stabilityChange.get(netStability) > 0}">
									+<fmt:formatNumber value="${home.stabilityChange.get(netStability)}"/> per month
								</c:if>
								<c:if test="${home.stabilityChange.get(netStability) < 0}">
									<fmt:formatNumber value="${home.stabilityChange.get(netStability)}"/> per month
								</c:if>
								<c:if test="${home.stabilityChange.get(netStability) == 0}">
									No change
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">Land</td>

						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.freeLand}"/>km<sup>2</sup> Free</td>
							<td><fmt:formatNumber value="${home.domestic.land}"/>km<sup>2</sup> Total</td>
						</tr>
						<tr>
							<td colspan="2">Population</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.totalPopulation}"/> People</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="gold">
						<tr>
							<td colspan="2">Economy</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Gross Domestic Product</td>
						</tr>
						<tr>
							<td colspan="2">$<fmt:formatNumber value="${home.economy.gdp}"/> Million</td>
						</tr>
						<tr>
							<td colspan="2">Growth</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.economy.growth}"/> Million per month</td>
							<td>
								<c:choose>
									<c:when test="${home.growthChange.get(netGrowth) > 0}">
										+<fmt:formatNumber value="${home.growthChange.get(netGrowth)}"/> per month
									</c:when>
									<c:when test="${home.growthChange.get(netGrowth) < 0}">
										<fmt:formatNumber value="${home.growthChange.get(netGrowth)}"/> per month
									</c:when>
									<c:otherwise>
										No change
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="green">
						<tr>
							<td colspan="2">Foreign</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Region</td>
						</tr>
						<tr>
							<td colspan="2">${home.foreign.region.name}</td>
						</tr>
						<tr>
							<td colspan="2">Official Alignment</td>
						</tr>
						<tr>
							<td colspan="2"><cloc:alignment value="${home.foreign.alignment}"/></td>
						</tr>
						<tr>
							<td colspan="2">Treaty Membership</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:if test="${home.treaty == null}">
									None
								</c:if>
								<c:if test="${home.treaty != null}">
									${home.treaty.treatyUrl}
								</c:if>
							</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="red">
						<tr>
							<td colspan="2">Army</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Active Personnel</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.army.size}"/>k Soldiers</td>
						</tr>
						<tr>
							<td colspan="2">Training</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.army.training}"/>%</td>
						</tr>
						<tr>
							<td>Equipment</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.totalInfantryEquipment}"/> / <fmt:formatNumber value="${home.army.size * 1000}"/> requested</td>
							<td>+0 per month</td>
						</tr>
						<tr>
							<td colspan="2">Fortification</td>
						</tr>
						<tr>
							<td>${home.army.fortification / 100}%</td>
							<td><c:choose>
								<c:when test="${home.fortificationChange.get(netFortification) > 0}">
									+<fmt:formatNumber maxFractionDigits="2" value="${home.fortificationChange.get(netFortification) / 100}"/>% per month
								</c:when>
								<c:when test="${home.fortificationChange.get(netFortification) < 0}">
									<fmt:formatNumber maxFractionDigits="2" value="${home.fortificationChange.get(netFortification) / 100}"/>% per month
								</c:when>
								<c:otherwise>
									No change
								</c:otherwise>
							</c:choose></td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="red">
						<tr>
							<td colspan="2">Airforce</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Fighters</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.fighterCount}"/> Planes</td>
							<td>+0 per month</td>
						</tr>
						<tr>
							<td colspan="2">Bombers</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.bomberCount}"/> Planes</td>
							<td>+0 per month</td>
						</tr>
						<tr>
							<td colspan="2">Recon Planes</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.reconCount}"/> Planes</td>
							<td>+0 per month</td>
						</tr>
					</table>
					<br>
				</div>
			</div>
			<div id="column_2" class="column">
				<c:if test="${home.eventCount <= 0 && home.anyUnreadNews}">
					<div class="tile">
						<div class="title">Unread News</div>
						<%--@elvariable id="news" type="java.util.List"--%>
						<%--@elvariable id="event" type="com.watersfall.clocgame.model.nation.News"--%>
						<c:forEach var="event" items="${news}">
							<div>
								${event.content}
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${home.eventCount > 0}">
					<div class="tile">
						<div class="title">Events</div>
						<%--@elvariable id="news" type="java.util.List"--%>
							<%--@elvariable id="event" type="com.watersfall.clocgame.model.event.Event"--%>
						<c:forEach var="event" items="${home.events}">
							<div class="subtile">
								<div class="title">${event.event.title}</div>
								<div class="description">
									${event.event.getDescription(home, event)}
								</div>
								<c:forEach var="action" items="${event.event.getPossibleResponses(home)}">
									<button>${action.text}</button>
								</c:forEach>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${!home.anyUnreadNews && home.eventCount <= 0}">
					<div class="tile">
						<div class="title">No Unread News or Events</div>
					</div>
				</c:if>
				<div class="tile">
					<div class="title">No Unread Messages</div>
				</div>
				<c:if test="${home.offensive != null}">
					<c:set var="defender" value="${home.offensive.defender}"/>
					<div class="tile">
						<div class="title">Offensive War</div>
						<table class="nation">
							<thead class="red">
							<tr>
								<td colspan="2">${defender.nationUrl}</td>
							</tr>
							</thead>
							<tr>
								<td colspan="2">Estimated Army Size</td>
							</tr>
							<tr>
								<td colspan="2">${defender.army.size}k Personnel</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Training</td>
							</tr>
							<tr>
								<td colspan="2">${defender.army.training}%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Equipment Status</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber maxFractionDigits="0" value="${defender.totalInfantryEquipment / (defender.army.size * 10)}"/>%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Airforce Size</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber value="${defender.fighterCount + defender.bomberCount + defender.reconCount}"/></td>
							</tr>
							<tr>
								<td colspan="2">Estimated Capital Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Escort Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
						</table>
						<div class="subtile">
							<div class="title">Land</div>
							<div class="description">
								Engage the enemy army with your own, either on the field or in their cities. This is the primary way of winning a war.
								Alternatively, you can fortify your army to make them more resistant to your enemy's attacks.
							</div>
							<button>Field Battle</button>
							<button>Siege City</button>
							<button>Fortify</button>
						</div>
						<div class="subtile">
							<div class="title">Air</div>
							<div class="description">
								Use your airforce to diminish your enemies ability to wage war in the air, on the ground, and at home
							</div>
							<button>Bomb Airforce</button>
							<button>Bomb Troops</button>
							<button>Bomb City</button>
						</div>
						<div class="subtile">
							<div class="title">Sea</div>
							<div class="description">
								Your navy can be used similar to your airforce, but only on targets on or near the water
							</div>
							<button>Engage Fleet</button>
							<button>Bombard Troops</button>
							<button>Bombard Cities</button>
						</div>
					</div>
				</c:if>
				<c:if test="${home.defensive != null}">
					<c:set var="attacker" value="${home.defensive.attacker}"/>
					<div class="tile">
						<div class="title">Defensive War</div>
						<table class="nation">
							<thead class="red">
							<tr>
								<td colspan="2">${attacker.nationUrl}</td>
							</tr>
							</thead>
							<tr>
								<td colspan="2">Estimated Army Size</td>
							</tr>
							<tr>
								<td colspan="2">${attacker.army.size}k Personnel</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Training</td>
							</tr>
							<tr>
								<td colspan="2">${attacker.army.training}%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Equipment Status</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber maxFractionDigits="0" value="${attacker.totalInfantryEquipment / (attacker.army.size * 10)}"/>%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Airforce Size</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber value="${attacker.fighterCount + attacker.bomberCount + attacker.reconCount}"/></td>
							</tr>
							<tr>
								<td colspan="2">Estimated Capital Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Escort Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
						</table>
						<div class="subtile">
							<div class="title">Land</div>
							<div class="description">
								Engage the enemy army with your own, either on the field or in their cities. This is the primary way of winning a war.
								Alternatively, you can fortify your army to make them more resistant to your enemy's attacks.
							</div>
							<button>Field Battle</button>
							<button>Siege City</button>
							<button>Fortify</button>
						</div>
						<div class="subtile">
							<div class="title">Air</div>
							<div class="description">
								Use your airforce to diminish your enemies ability to wage war in the air, on the ground, and at home
							</div>
							<button>Bomb Airforce</button>
							<button>Bomb Troops</button>
							<button>Bomb City</button>
						</div>
						<div class="subtile">
							<div class="title">Sea</div>
							<div class="description">
								Your navy can be used similar to your airforce, but only on targets on or near the water
							</div>
							<button>Engage Fleet</button>
							<button>Bombard Troops</button>
							<button>Bombard Cities</button>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>

<%@ include file="includes/bottom.jsp" %>