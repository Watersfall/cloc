<%@ page import="com.watersfall.clocgame.model.decisions.CityDecisions" %>
<%@ include file="includes/top.jsp" %>
<%--@elvariable id="city" type="com.watersfall.clocgame.model.city.City"--%>
<% pageContext.setAttribute("key", TextKey.Resource.TOTAL_GAIN); %>
<% pageContext.setAttribute("garrisonKey", TextKey.Garrison.NET); %>
<%@ page import="com.watersfall.clocgame.util.Util" %>
<c:if test="${city == null}">
	<div class="title">Cities</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<c:forEach var="city" items="${home.cities.values()}">
					<div class="subtitle">
						<div id="city_name_${city.id}">
							<c:out escapeXml="false" value="${city.url} "/>
							<img title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit" onclick="editCityName(${city.id})"/>
							(${city.freeSlots} free slots)
						</div>
						<div class="toggleable-default-off" id="city_name_change_${city.id}">
							<input id="new_name_${city.id}" style="font-size: inherit" type="text" value="${city.name}"/>
							<img onclick="confirmCityName(${city.id});" title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/checkmark.svg" alt="edit"/>
							<img onclick="cancelCityName(${city.id});" title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/cancel.svg" alt="edit"/>
						</div>
					</div>
					<table class="nation nation_left">
						<tr>
							<td colspan="3">Coal Mines</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_COAL_MINE}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_COAL_MINE}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.coalMines}</td>
						</tr>
						<tr>
							<td colspan="3">Iron Mines</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_IRON_MINE}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_IRON_MINE}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.ironMines}</td>
						</tr>
						<tr>
							<td colspan="3">Oil Wells</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_OIL_WELL}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_OIL_WELL}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.oilWells}</td>
						</tr>
						<tr>
							<td colspan="3">Civilian Industry</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_CIVILIAN_INDUSTRY}', ${city.id})" class="blue">Build: ${city.factoryCost}</button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_CIVILIAN_INDUSTRY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.industryCivilian}</td>
						</tr>
						<tr>
							<td colspan="3">Military Industry</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_MILITARY_INDUSTRY}', ${city.id})" class="blue">Build: ${city.factoryCost}</button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_MILITARY_INDUSTRY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.industryMilitary}</td>
						</tr>
					</table>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${city != null}">
	<c:if test="${!home.cities.containsKey(city.id)}">
		<div class="title">
			<c:out value="${city.name}"/>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">
						<c:out escapeXml="false" value="${city.size.name} "/>
					</div>
					<div class="description left_text">
							${city.freeSlots} Free Build Slots (out of ${city.buildSlots} total)<br>
						Population: <fmt:formatNumber value="${city.population}"/>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Coal Mines</div>
					<div class="description left_text">
						<b>${city.coalMines}</b> coal mine${city.coalMines == 1 ? '' : 's'}<br>
					</div>
					<div class="subtitle">Iron Mines</div>
					<div class="description left_text">
						<b>${city.ironMines}</b> iron mine${city.ironMines == 1 ? '' : 's'}<br>
					</div>
					<div class="subtitle">Oil Wells</div>
					<div class="description left_text">
						<b>${city.oilWells}</b> oil well${city.oilWells == 1 ? '' : 's'}<br>
					</div>
				</div>
			</div>
			<div class="column">
				<div class="tile">
					<div class="subtitle">Civilian Industry</div>
					<div class="description left_text">
						<b>${city.industryCivilian}</b> civilian factor${city.industryCivilian == 1 ? 'y' : 'ies'}
					</div>
					<div class="subtitle">Military Industry</div>
					<div class="description left_text">
						<b>${city.industryMilitary}</b> military factor${city.industryMilitary == 1 ? 'y' : 'ies'}<br>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Infrastructure</div>
					<div class="description left_text">
							${city.railroads} / 10<br>
					</div>
					<div class="subtitle">Military Barracks</div>
					<div class="description left_text">
							${city.barracks} / 10<br>
					</div>
					<div class="subtitle">Ports</div>
					<div class="description left_text">
							${city.ports} / 10<br>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${home.cities.containsKey(city.id)}">
		<div class="title">
			<div id="city_name_${city.id}">
				<c:out escapeXml="false" value="${city.name} "/>
				<img title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit" onclick="editCityName(${city.id})"/>
			</div>
			<div class="toggleable-default-off" id="city_name_change_${city.id}">
				<input id="new_name_${city.id}" style="font-size: inherit" type="text" value="${city.name}"/>
				<img onclick="confirmCityName(${city.id});" title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/checkmark.svg" alt="edit"/>
				<img onclick="cancelCityName(${city.id});" title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/cancel.svg" alt="edit"/>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">
						<c:out escapeXml="false" value="${city.size.name} "/>
					</div>
					<div class="description left_text">
							${city.freeSlots} Free Build Slots (out of ${city.buildSlots} total)<br>
						Population: <fmt:formatNumber value="${city.population}"/><br>
						<div class="dropdown_parent">
							<a href="#" onclick="toggleUITab('garrison_dropdown')">
								Garrison: <fmt:formatNumber value="${city.garrisonSize.get(garrisonKey)}"/><c:out value=" "/>
								<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
							</a>
							<div class="toggleable-default-off dropdown_2" id="garrison_dropdown">
								<ul>
									<c:forEach var="garrison" items="${Util.removeNetAndTotal(city.garrisonSize).entrySet()}">
										<c:if test="${garrison.value != 0}">
											<li>
												<c:if test="${garrison.value > 0}">
													<span class="positive">+<fmt:formatNumber value="${garrison.value}"/>${garrison.key.text}</span>
												</c:if>
												<c:if test="${garrison.value < 0}">
													<span class="negative"><fmt:formatNumber value="${garrison.value}"/>${garrison.key.text}</span>
												</c:if>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Coal Mines</div>
					<div class="description left_text">
						<b>${city.coalMines}</b> coal mine${city.coalMines == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.coalProduction.get(key)}" maxFractionDigits="2"/></b> coal per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_COAL_MINE}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_COAL_MINE}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Iron Mines</div>
					<div class="description left_text">
						<b>${city.ironMines}</b> iron mine${city.ironMines == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.ironProduction.get(key)}" maxFractionDigits="2"/></b> iron per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_IRON_MINE}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_IRON_MINE}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Oil Wells</div>
					<div class="description left_text">
						<b>${city.oilWells}</b> oil well${city.oilWells == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.oilProduction.get(key)}" maxFractionDigits="2"/></b> oil per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_OIL_WELL}', ${city.id})" class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_OIL_WELL}', ${city.id})" class="red">Remove</button>
					</div>
				</div>
			</div>
			<div class="column">
				<div class="tile">
					<div class="subtitle">Civilian Industry</div>
					<div class="description left_text">
						<b>${city.industryCivilian}</b> civilian factor${city.industryCivilian == 1 ? 'y' : 'ies'} producing <b><fmt:formatNumber value="${city.steelProduction.get(key)}" maxFractionDigits="2"/></b> steel per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_CIVILIAN_INDUSTRY}', ${city.id})" class="blue">Build: ${city.factoryCost}</button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_CIVILIAN_INDUSTRY}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Military Industry</div>
					<div class="description left_text">
						<b>${city.industryMilitary}</b> military factor${city.industryMilitary == 1 ? 'y' : 'ies'}<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_MILITARY_INDUSTRY}', ${city.id})" class="blue">Build: ${city.factoryCost}</button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_MILITARY_INDUSTRY}', ${city.id})" class="red">Remove</button>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Infrastructure</div>
					<div class="description left_text">
							${city.railroads} / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_RAILROAD}', ${city.id})" class="blue">Build: <fmt:formatNumber value="${city.railCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_RAILROAD}', ${city.id})" class="red">Close</button>
					</div>
					<div class="subtitle">Military Barracks</div>
					<div class="description left_text">
							${city.barracks} / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_BARRACK}', ${city.id})" class="blue">Build: <fmt:formatNumber value="${city.barrackCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_BARRACK}', ${city.id})" class="red">Close</button>
					</div>
					<div class="subtitle">Ports</div>
					<div class="description left_text">
							${city.ports} / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_PORT}', ${city.id})" class="blue">Build: <fmt:formatNumber value="${city.portCost}"/></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_PORT}', ${city.id})" class="red">Close</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</c:if>
<%@ include file="includes/bottom.jsp" %>