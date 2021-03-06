<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="title">
	<div id="army_name_${army.id}">
		<a><c:out escapeXml="false" value="${army.url} "/></a>
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
			<a href="javascript:void(0);">
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
				<a href="javascript:void(0);" onclick="toggleUITab('equipment_gain_${army.id}_${equipment.key.name()}')">
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
				<div id="equipment_gain_${army.id}_${equipment.key.name()}" class="dropdown_2 toggleable-default-off special-toggle">
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
			<a href="javascript:void(0);">
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
			<a href="javascript:void(0);">
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
			<button onclick="deleteArmy(${army.id})" class="red right">Delete</button>
		</td>
	</tr>
</table>