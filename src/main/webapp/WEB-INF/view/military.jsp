<%@ include file="includes/top.jsp" %>
<%@ page import="net.watersfall.clocgame.model.producible.ProducibleCategory" %>
<%@ page import="net.watersfall.clocgame.util.Util" %>
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
							<td class="dropdown_parent">
								<a href="#" onclick="toggleUITab('size_changes')">
									+<fmt:formatNumber value="${home.totalArmyIncrease}"/> Per Turn
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="size_changes" class="dropdown_2 toggleable-default-off">
									<ul>
										<c:if test="${home.armyManpowerChange.size() <= 0}">
											<li>No Change</li>
										</c:if>
										<c:if test="${home.armyManpowerChange.size() > 0}">
											<c:forEach items="${home.armyManpowerChange.entrySet()}" var="army">
												<li>+<fmt:formatNumber value="${army.value}"/> to ${army.key.name}</li>
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Manpower Reinforcement Capacity</td>
						</tr>
						<tr>
							<td colspan="2" class="dropdown_parent">
								<a href="#" onclick="toggleUITab('manpower_reinforcement_capacity')">
									<fmt:formatNumber value="${home.manpowerReinforcementCapacity.get(net)}"/> Soldiers
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="manpower_reinforcement_capacity" class="dropdown_2 toggleable-default-off">
									<ul>
										<c:set var="map" value="${Util.removeNetAndTotal(home.manpowerReinforcementCapacity)}"/>
										<c:if test="${map.size() <= 0}">
											<li>No Change</li>
										</c:if>
										<c:if test="${map.size() > 0}">
											<c:forEach items="${map.entrySet()}" var="army">
												<c:if test="${army.value != 0}">
													<li>+<fmt:formatNumber value="${army.value}"/>${army.key.text}</li>
												</c:if>
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Equipment Reinforcement Capacity</td>
						</tr>
						<tr>
							<td class="dropdown_parent" colspan="2">
								<a href="#" onclick="toggleUITab('equipment_reinforcement_capacity')">
									<fmt:formatNumber value="${home.equipmentReinforcementCapacity.get(net)}"/> Whatevers
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="equipment_reinforcement_capacity" class="dropdown_2 toggleable-default-off">
									<ul>
										<c:set var="map" value="${Util.removeNetAndTotal(home.equipmentReinforcementCapacity)}"/>
										<c:if test="${map.size() <= 0}">
											<li>No Change</li>
										</c:if>
										<c:if test="${map.size() > 0}">
											<c:forEach items="${map.entrySet()}" var="army">
												<c:if test="${army.value != 0}">
													<li>+<fmt:formatNumber value="${army.value}"/>${army.key.text}</li>
												</c:if>
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="title">Armies</div>
				<c:forEach var="army" items="${home.armies}">
					<div class="subtile">
						<div class="title">
							<div id="army_name_${army.id}">
								<c:out escapeXml="false" value="${army.url} "/>
								<img title="Edit Army Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit" onclick="editArmyName(${army.id})"/>
							</div>
							<div class="toggleable-default-off" id="army_name_change_${army.id}">
								<input id="new_name_${army.id}" style="font-size: inherit" type="text" value="${army.name}"/>
								<img onclick="confirmArmyName(${army.id});" title="Edit Army Name" class="match_text invert_svg" src="${pageContext.request.contextPath}/images/ui/checkmark.svg" alt="edit"/>
								<img onclick="cancelArmyName(${army.id});" title="Edit Army Name" class="match_text invert_svg" src="${pageContext.request.contextPath}/images/ui/cancel.svg" alt="edit"/>
							</div>
						</div>
						<table class="nation nation_left full_width">
							<tr>
								<td colspan="2">Size</td>
							</tr>
							<tr>
								<td><fmt:formatNumber value="${army.size}"/> / <fmt:formatNumber value="${army.maxSize}"/> Soldiers</td>
								<td>
									<a href="#">
										+<fmt:formatNumber value="${home.armyManpowerChange.getOrDefault(army, 0)}"/> Per Month
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
								<td class="dropdown_parent">
									<c:forEach var="equipment" items="${army.maxEquipment.entrySet()}">
										<a href="#" onclick="toggleUITab('equipment_gain_${army.id}_${equipment.key.name()}')">
											<c:set var="parent" value="${equipment.key}"/>
											<c:set var="hasUpgrade" value="false"/>
											<c:set var="hasEquipment" value="false"/>
											<c:if test="${home.equipmentUpgrades.get(army) != null && home.equipmentUpgradesByCategory.get(army).getOrDefault(equipment.key, 0) > 0}">
												+<fmt:formatNumber value="${home.equipmentUpgradesByCategory.get(army).getOrDefault(equipment.key, 0)}"/> Upgrade
												<c:set var="hasUpgrade" value="true"/>
											</c:if>
											<c:choose>
												<c:when test="${home.armyEquipmentChange.get(army) != null && home.armyEquipmentChange.get(army).getOrDefault(equipment.key, 0) > 0}">
													<c:if test="${hasUpgrade}">
														,
													</c:if>
													+<fmt:formatNumber value="${home.armyEquipmentChange.get(army).getOrDefault(equipment.key, 0)}"/> New
													<c:set var="hasEquipment" value="true"/>
												</c:when>
												<c:when test="${!hasUpgrade}">
													+0 Per Month
												</c:when>
											</c:choose>
											<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
											<br>
										</a>
										<div id="equipment_gain_${army.id}_${equipment.key.name()}" class="dropdown_2 toggleable-default-off">
											<c:if test="${hasEquipment}">
												New:
												<ul>
													<c:forEach items="${home.armyEquipmentChange.get(army).entrySet()}" var="equipment">
														<c:if test="${equipment.key == parent}">
															<li>
																+${equipment.value} ${equipment.key.name()}
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</c:if>
											<c:if test="${hasUpgrade}">
												Upgrades:
												<ul>
													<c:forEach var="equipment" items="${home.equipmentUpgrades.get(army)}">
														<c:if test="${equipment.key.producible.category == parent}">
															<li>
																+${equipment.value} ${equipment.key.name()}
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</c:if>
											<c:if test="${!hasUpgrade && !hasEquipment}">
												<ul>
													<li>No Change</li>
												</ul>
											</c:if>
										</div>
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
										+0 Per Month
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
										+0 Per Month
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