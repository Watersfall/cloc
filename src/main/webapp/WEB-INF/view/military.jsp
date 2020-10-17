<%@ include file="includes/top.jsp" %>
<%@ page import="com.watersfall.clocgame.model.producible.ProducibleCategory" %>
<% pageContext.setAttribute("net", TextKey.Reinforcement.NET); %>
<div class="title">Military</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Army</div>
				<div class="subtile left_text">
					<div class="title">Army Stats</div>
					<table class="nation nation_left full_width">
						<tr>
							<td colspan="2">Total Army Size</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.armySize}"/> Soldiers</td>
							<td>
								<a href="#">
									+0 Per Turn
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="2">Manpower Reinforcement Capacity</td>
						</tr>
						<tr>
							<td colspan="2">
								<a href="#">
									<fmt:formatNumber value="${home.manpowerReinforcementCapacity.get(net)}"/> Soldiers
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="2">Equipment Reinforcement Capacity</td>
						</tr>
						<tr>
							<td colspan="2">
								<a href="#">
									<fmt:formatNumber value="${home.equipmentReinforcementCapacity.get(net)}"/> Whatevers
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="title">Armies</div>
				<c:forEach var="army" items="${home.armies}">
					<div class="subtile">
						<div class="title">
							<a href="/army/${army.id}">${army.name}</a>
						</div>
						<table class="nation nation_left full_width">
							<tr>
								<td colspan="2">Size</td>
							</tr>
							<tr>
								<td><fmt:formatNumber value="${army.size}"/> / <fmt:formatNumber value="${army.maxSize}"/> Soldiers</td>
								<td>
									<a href="#">
										+<fmt:formatNumber value="${home.armyManpowerChange.getOrDefault(army, 0)}"/> Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
									</a>
								</td>
							</tr>
							<tr>
								<td colspan="2">Equipment</td>
							</tr>
							<tr>
								<td>
									<c:forEach var="equipment" items="${army.maxEquipment.entrySet()}">
										<fmt:formatNumber value="${army.equipment.getOrDefault(equipment.key, 0)}"/> / <fmt:formatNumber value="${equipment.value}"/>${' '.concat(equipment.key.name())}<br>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="equipment" items="${army.maxEquipment.entrySet()}">
										<a href="#">
											<c:set var="hasUpgrade" value="false"/>
											<c:if test="${home.equipmentUpgrades.get(army) != null && home.equipmentUpgradesByCategory.get(army).getOrDefault(equipment.key, 0) > 0}">
												+<fmt:formatNumber value="${home.equipmentUpgradesByCategory.get(army).getOrDefault(equipment.key, 0)}"/> Upgrade,
												<c:set var="hasUpgrade" value="true"/>
											</c:if>
											<c:choose>
												<c:when test="${home.armyEquipmentChange.get(army) != null && home.armyEquipmentChange.get(army).getOrDefault(equipment.key, 0) > 0}">
													+<fmt:formatNumber value="${home.armyEquipmentChange.get(army).getOrDefault(equipment.key, 0)}"/> New
												</c:when>
												<c:when test="${!hasUpgrade}">
													+0 Per Month
												</c:when>
											</c:choose>
											<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
											<br>
										</a>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td colspan="2">Training</td>
							</tr>
							<tr>
								<td><fmt:formatNumber value="${army.training / 100}" maxFractionDigits="0"/>%</td>
								<td>
									<a href="#">
										+0 Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
									</a>
								</td>
							</tr>
							<tr>
								<td colspan="2">Experience</td>
							</tr>
							<tr>
								<td><fmt:formatNumber value="${army.experience / 100}" maxFractionDigits="0"/>%</td>
								<td>
									<a href="#">
										+0 Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
									</a>
								</td>
							</tr>
							<tr>
								<td colspan="2">Specialization</td>
							</tr>
							<tr>
								<td>${army.specialization.name()}</td>
								<td><fmt:formatNumber value="${army.specializationAmount / 100}" maxFractionDigits="0"/>%</td>
							</tr>
							<tr>
								<td colspan="2">Location</td>
							</tr>
							<tr>
								<td>
									<c:if test="${army.nation != null}">
										The Nation of ${army.nation.nationUrl}
									</c:if>
									<c:if test="${army.city != null}">
										The City of ${army.city.url}
									</c:if>
								</td>
								<td>
									<button onclick="army(${army.id}, 'delete_army')" class="red right">Delete</button>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
				<c:if test="${home.canCreateNewArmy()}">
					<div class="subtile centered">
						<button onclick="army(0, 'create_army');" class="blue centered">Create New Army</button>
					</div>
				</c:if>
			</div>
		</div>
		<div class="column">
			<div class="tile">
				<div class="title">Airforce</div>
				<div class="subtile">
					<label>
						<div class="title">Fighters</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)}" ${home.stats.maxFighters < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxFighters)} id="fighters"/>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', document.getElementById('fighters').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
					<label>
						<div class="title">Bombers</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)}" ${home.stats.maxBombers < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxBombers)} id="bombers"/>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', document.getElementById('bombers').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
					<label>
						<div class="title">Recon Planes</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)}" ${home.stats.maxRecon < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxRecon)} id="recon"/>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', document.getElementById('recon').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
				</div>
			</div>
			<div class="tile">
				<div class="title">Navy</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>