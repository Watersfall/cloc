<%@ include file="includes/top.jsp" %>
<%@ page import="net.watersfall.clocgame.model.producible.ProducibleCategory" %>
<%@ page import="net.watersfall.clocgame.model.producible.Producibles" %>
<%--@elvariable id="nation" type="net.watersfall.clocgame.model.nation.Nation"--%>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<h1><c:out escapeXml="false" value="${nation.cosmetic.nationTitle}"/><br>of<br><c:out escapeXml="false" value="${nation.cosmetic.nationName}"/></h1>
				<img class="large_flag" src="/user/flag/${nation.cosmetic.flag}" alt="flag"/>
				<h2><c:out escapeXml="false" value="${nation.cosmetic.leaderTitle} "/><c:out escapeXml="false" value="${nation.cosmetic.username}"/></h2>
				<p>${nation.lastOnline}</p>
			</div>
		</div>
	</div>
	<div class="tiling">
		<div class="column">
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
						<td colspan="2"><cloc:government value="${nation.stats.government}"/></td>
					</tr>
					<tr>
						<td colspan="2">Approval</td>
					</tr>
					<tr>
						<td>${nation.stats.approval}%</td>
					</tr>
					<tr>
						<td colspan="2">Stability</td>
					</tr>
					<tr>
						<td>${nation.stats.stability}%</td>
					</tr>
					<tr>
						<td colspan="2">Land</td>

					</tr>
					<tr>
						<td><fmt:formatNumber value="${nation.freeLand}"/>km<sup>2</sup> Free</td>
						<td><fmt:formatNumber value="${nation.stats.land}"/>km<sup>2</sup> Total</td>
					</tr>
					<tr>
						<td colspan="2">Population</td>
					</tr>
					<tr>
						<td colspan="2"><fmt:formatNumber value="${nation.totalPopulation}"/> People</td>
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
						<td colspan="2">$<fmt:formatNumber value="${nation.stats.gdp}"/> Million</td>
					</tr>
					<tr>
						<td colspan="2">Growth</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${nation.stats.growth}"/> Million per month</td>
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
						<td colspan="2">${nation.stats.region.name}</td>
					</tr>
					<tr>
						<td colspan="2">Official Alignment</td>
					</tr>
					<tr>
						<td colspan="2">${nation.stats.alignment}</td>
					</tr>
					<tr>
						<td colspan="2">Treaty Membership</td>
					</tr>
					<tr>
						<td colspan="2">
							<c:if test="${nation.treaty == null}">
								None
							</c:if>
							<c:if test="${nation.treaty != null}">
								${nation.treaty.treatyUrl}
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
						<td colspan="2"><fmt:formatNumber value="${nation.armySize}"/> Soldiers</td>
					</tr>
					<tr>
						<td>Equipment</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${nation.getTotalProduciblesByCategory(ProducibleCategory.INFANTRY_EQUIPMENT)}"/> / <fmt:formatNumber value="${nation.armySize}"/> requested</td>
					</tr>
					<tr>
						<td colspan="2">Fortification</td>
					</tr>
					<tr>
						<td>${nation.stats.fortification / 100}%</td>
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
						<td><fmt:formatNumber value="${nation.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)}"/> Planes</td>
					</tr>
					<tr>
						<td colspan="2">Bombers</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${nation.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)}"/> Planes</td>
					</tr>
					<tr>
						<td colspan="2">Recon Planes</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${nation.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)}"/> Planes</td>
					</tr>
				</table>
				<br>
			</div>
			<div class="tile">
				<div class="title">Wars</div>
				<c:if test="${nation.defensive == null && nation.offensive == null}">
					<div class="subtile">
						No wars
					</div>
				</c:if>
				<c:if test="${nation.offensive != null}">
					<div class="subtile">
						<div class="title">Offensive War Against: ${nation.offensive.defender.nationUrl}</div>
					</div>
				</c:if>
				<c:if test="${nation.defensive != null}">
					<div class="subtile">
						<div class="title">Defensive War Against: ${nation.defensive.attacker.nationUrl}</div>
					</div>
				</c:if>
			</div>
		</div>
		<c:if test="${not empty home}">
			<div class="column">
				<div class="tile">
					<div class="title">Diplomacy</div>
					<div class="subtile">
						<label>
							<div class="title">Send Money</div>
							<input type="number" id="send_budget"/>
							<button onclick="nation(${nation.id}, 'sendmoney', document.getElementById('send_budget').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Coal</div>
							<input type="number" id="send_coal"/>
							<button onclick="nation(${nation.id}, 'sendcoal', document.getElementById('send_coal').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Iron</div>
							<input type="number" id="send_iron"/>
							<button onclick="nation(${nation.id}, 'sendiron', document.getElementById('send_iron').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Oil</div>
							<input type="number" id="send_oil"/>
							<button onclick="nation(${nation.id}, 'sendoil', document.getElementById('send_oil').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Steel</div>
							<input type="number" id="send_steel"/>
							<button onclick="nation(${nation.id}, 'sendsteel', document.getElementById('send_steel').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Nitrogen</div>
							<input type="number" id="send_nitrogen"/>
							<button onclick="nation(${nation.id}, 'sendnitrogen', document.getElementById('send_nitrogen').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile">
						<label>
							<div class="title">Send Equipment</div>
							<input type="number" id="send_equipment"/>
							<select id="equipment">
								<c:forEach var="equipment" items="${Producibles.values()}">
									<c:if test="${home.producibles.getProducible(equipment) > 0}">
										<option value="${equipment.name()}">${equipment.name()} - (${home.producibles.getProducible(equipment)} available)</option>
									</c:if>
								</c:forEach>
							</select>
							<button onclick="nationEquipment(${nation.id}, document.getElementById('equipment').value, document.getElementById('send_equipment').value)" class="blue">Send</button>
						</label>
					</div>
					<div class="subtile" id="send_message">
						<label>
							<div class="title">Send Message</div>
							<textarea id="message"></textarea>
							<div class="centered">
								<button class="blue" onclick="nationMessage(${nation.id}, 'message', document.getElementById('message').value);">Send</button>
							</div>
						</label>
					</div>
				</div>
				<div class="tile">
					<div class="title">War</div>
					<c:if test="${home.isAtWarWith(nation)}">
						<div class="subtile">
							<div class="title">Land</div>
							<div class="description">
								Engage the enemy army with your own, either on the field or in their cities. This is the primary way of winning a war.
								Alternatively, you can fortify your army to make them more resistant to your enemy's attacks.
							</div>
							<button onclick="window.location = PATH + '/battle/${nation.id}'">Battle</button>
							<button onclick="nation(${nation.id}, 'land_fortify', 0);">Fortify</button>
						</div>
						<div class="subtile">
							<div class="title">Air</div>
							<div class="description">
								Use your airforce to diminish your enemies ability to wage war in the air, on the ground, and at home
							</div>
							<button onclick="nation(${nation.id}, 'air_air', 0);">Bomb Airforce</button>
							<button onclick="nation(${nation.id}, 'air_land', 0);">Bomb Troops</button>
							<button onclick="nation(${nation.id}, 'air_city', 0);">Bomb City</button>
						</div>
						<div class="subtile">
							<div class="title">Sea (Non-functional)</div>
							<div class="description">
								Your navy can be used similar to your airforce, but only on targets on or near the water
							</div>
							<button>Engage Fleet</button>
							<button>Bombard Troops</button>
							<button>Bombard Cities</button>
						</div>
						<div class="subtile">
							<button onclick="nation(${nation.id}, 'peace', 0);" class="blue">Send Peace</button>
						</div>
					</c:if>
					<c:if test="${!home.isAtWarWith(nation)}">
						<c:if test="${home.canDeclareWar(nation) == null}">
							<button onclick="nation(${nation.id}, 'war', 0);" class="red">Declare War</button>
						</c:if>
						<c:if test="${home.canDeclareWar(nation) != null}">
							You can not declare war on this nation<br>
							${home.canDeclareWar(nation)}
						</c:if>
					</c:if>
				</div>
			</div>
		</c:if>
	</div>
<%@ include file="includes/bottom.jsp" %>