<%@ page import="net.watersfall.clocgame.model.decisions.CityDecisions" %>
<%@ include file="includes/top.jsp" %>
<%--@elvariable id="city" type="net.watersfall.clocgame.model.city.City"--%>
<% pageContext.setAttribute("key", TextKey.Resource.TOTAL_GAIN); %>
<% pageContext.setAttribute("garrisonKey", TextKey.Garrison.NET); %>
<%@ page import="net.watersfall.clocgame.model.json.JsonFields" %>
<%@ page import="net.watersfall.clocgame.util.Util" %>
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
							(<span id="${JsonFields.CITY_BUILD_SLOTS}_${city.id}">${city.freeSlots}</span> free slots)
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
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_COAL_MINE}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_COAL_MINE_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_COAL_MINE}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_COAL_MINES}_${city.id}">${city.coalMines}</td>
						</tr>
						<tr>
							<td colspan="3">Iron Mines</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_IRON_MINE}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_IRON_MINE_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_IRON_MINE}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_IRON_MINES}_${city.id}">${city.ironMines}</td>
						</tr>
						<tr>
							<td colspan="3">Oil Wells</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_OIL_WELL}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_OIL_WELL_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_OIL_WELL}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_OIL_WELLS}_${city.id}">${city.oilWells}</td>
						</tr>
						<tr>
							<td colspan="3">Civilian Industry</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_CIVILIAN_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_CIVILIAN_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_CIVILIAN_INDUSTRY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_CIVILIAN_INDUSTRY}_${city.id}">${city.industryCivilian}</td>
						</tr>
						<tr>
							<td colspan="3">Nitrogen Industry</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_NITROGEN_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_NITROGEN_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_NITROGEN_INDUSTRY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_NITROGEN_INDUSTRY}_${city.id}">${city.industryNitrogen}</td>
						</tr>
						<tr>
							<td colspan="3">Military Industry</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_MILITARY_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_MILITARY_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_MILITARY_INDUSTRY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_MILITARY_INDUSTRY}_${city.id}">${city.industryMilitary}</td>
						</tr>
						<tr>
							<td colspan="3">Universities</td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.BUILD_UNIVERSITY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_UNIVERSITY_COST}_${city.id}">${city.universityCost}</span></button></td>
							<td rowspan="2"><button onclick="cityDecision('${CityDecisions.REMOVE_UNIVERSITY}', ${city.id})" class="red">Remove</button></td>
						</tr>
						<tr>
							<td id="${JsonFields.CITY_UNIVERSITIES}_${city.id}">${city.universities}</td>
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
						<span id="${JsonFields.CITY_BUILD_SLOTS}_${city.id}">${city.freeSlots}</span> Free Build Slots (out of <span id="${JsonFields.CITY_MAX_BUILD_SLOTS}_${city.id}">${city.buildSlots}</span> total)<br>
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
						<b id="${JsonFields.CITY_COAL_MINES}_${city.id}">${city.coalMines}</b> coal mine${city.coalMines == 1 ? '' : 's'}<br>
					</div>
					<div class="subtitle">Iron Mines</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_IRON_MINES}_${city.id}">${city.ironMines}</b> iron mine${city.ironMines == 1 ? '' : 's'}<br>
					</div>
					<div class="subtitle">Oil Wells</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_OIL_WELLS}_${city.id}">${city.oilWells}</b> oil well${city.oilWells == 1 ? '' : 's'}<br>
					</div>
				</div>
			</div>
			<div class="column">
				<div class="tile">
					<div class="subtitle">Civilian Industry</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_CIVILIAN_INDUSTRY}_${city.id}">${city.industryCivilian}</b> civilian factor${city.industryCivilian == 1 ? 'y' : 'ies'}
					</div>
					<div class="subtitle">Nitrogen Factories</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_NITROGEN_INDUSTRY}_${city.id}">${city.industryNitrogen}</b> nitrogen factor${city.industryCivilian == 1 ? 'y' : 'ies'}
					</div>
					<div class="subtitle">Military Industry</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_MILITARY_INDUSTRY}_${city.id}">${city.industryMilitary}</b> military factor${city.industryMilitary == 1 ? 'y' : 'ies'}
					</div>
					<div class="subtitle">Universities</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_UNIVERSITIES}_${city.id}">${city.universities}</b> universit${city.industryMilitary == 1 ? 'y' : 'ies'}<br>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Infrastructure</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_INFRASTRUCTURE}_${city.id}">${city.railroads}</span> / 10<br>
					</div>
					<div class="subtitle">Military Barracks</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_BARRACKS}_${city.id}">${city.barracks}</span> / 10<br>
					</div>
					<div class="subtitle">Ports</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_PORTS}_${city.id}">${city.ports}</span> / 10<br>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${home.cities.containsKey(city.id)}">
		<div class="title">
			<div id="city_name_${city.id}">
				<span id="${JsonFields.CITY_NAME}_${city.id}"><c:out escapeXml="false" value="${city.name}"/> </span>
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
						<span id="${JsonFields.CITY_BUILD_SLOTS}_${city.id}">${city.freeSlots}</span> Free Build Slots (out of <span id="${JsonFields.CITY_MAX_BUILD_SLOTS}_${city.id}">${city.buildSlots}</span> total)<br>
						Population: <span id="${JsonFields.POPULATION}_${city.id}"><fmt:formatNumber value="${city.population}"/></span><br>
						<div class="dropdown_parent">
							<a href="javascript:void(0);" onclick="toggleUITab('garrison_dropdown')">
								Garrison: <fmt:formatNumber value="${city.garrisonSize.get(garrisonKey)}"/><c:out value=" "/>
								<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
							</a>
							<div class="toggleable-default-off dropdown_2 special-toggle" id="garrison_dropdown">
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
						<b id="${JsonFields.CITY_COAL_MINES}_${city.id}">${city.coalMines}</b> coal mine${city.coalMines == 1 ? '' : 's'} producing <b id="${JsonFields.CITY_COAL_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.coalProduction.get(key)}" maxFractionDigits="2"/></b> coal per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_COAL_MINE}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_COAL_MINE_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_COAL_MINE}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Iron Mines</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_IRON_MINES}_${city.id}">${city.ironMines}</b> iron mine${city.ironMines == 1 ? '' : 's'} producing <b id="${JsonFields.CITY_IRON_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.ironProduction.get(key)}" maxFractionDigits="2"/></b> iron per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_IRON_MINE}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_IRON_MINE_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_IRON_MINE}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Oil Wells</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_OIL_WELLS}_${city.id}">${city.oilWells}</b> oil well${city.oilWells == 1 ? '' : 's'} producing <b id="${JsonFields.CITY_OIL_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.oilProduction.get(key)}" maxFractionDigits="2"/></b> oil per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_OIL_WELL}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_OIL_WELL_COST}_${city.id}"><fmt:formatNumber value="${city.mineCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_OIL_WELL}', ${city.id})" class="red">Remove</button>
					</div>
				</div>
			</div>
			<div class="column">
				<div class="tile">
					<div class="subtitle">Civilian Industry</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_CIVILIAN_INDUSTRY}_${city.id}">${city.industryCivilian}</b> civilian factor${city.industryCivilian == 1 ? 'y' : 'ies'} producing <b id="${JsonFields.CITY_STEEL_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.steelProduction.get(key)}" maxFractionDigits="2"/></b> steel per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_CIVILIAN_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_CIVILIAN_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_CIVILIAN_INDUSTRY}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Nitrogen Factories</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_NITROGEN_INDUSTRY}_${city.id}">${city.industryNitrogen}</b> nitrogen factor${city.industryNitrogen == 1 ? 'y' : 'ies'} producing <b id="${JsonFields.CITY_NITROGEN_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.nitrogenProduction.get(key)}" maxFractionDigits="2"/></b> nitrogen per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_NITROGEN_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_NITROGEN_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_NITROGEN_INDUSTRY}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Military Industry</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_MILITARY_INDUSTRY}_${city.id}">${city.industryMilitary}</b> military factor${city.industryMilitary == 1 ? 'y' : 'ies'}<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_MILITARY_INDUSTRY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_MILITARY_INDUSTRY_COST}_${city.id}">${city.factoryCost}</span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_MILITARY_INDUSTRY}', ${city.id})" class="red">Remove</button>
					</div>
					<div class="subtitle">Universities</div>
					<div class="description left_text">
						<b id="${JsonFields.CITY_UNIVERSITIES}_${city.id}">${city.universities}</b> universit${city.universities == 1 ? 'y' : 'ies'} producing <b id="${JsonFields.CITY_RESEARCH_PRODUCTION}_${city.id}"><fmt:formatNumber value="${city.researchProduction.get(key)}" maxFractionDigits="2"/></b> research per month<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_UNIVERSITY}', ${city.id})" class="blue">Build: <span id="${JsonFields.CITY_UNIVERSITY_COST}_${city.id}">${city.universityCost}</span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_UNIVERSITY}', ${city.id})" class="red">Remove</button>
					</div>
				</div>
			</div>
		</div>
		<div class="tiling">
			<div class="column">
				<div class="tile">
					<div class="subtitle">Infrastructure</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_INFRASTRUCTURE}_${city.id}">${city.railroads}</span> / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_RAILROAD}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_INFRASTRUCTURE_COST}_${city.id}"><fmt:formatNumber value="${city.railCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_RAILROAD}', ${city.id})" class="red">Close</button>
					</div>
					<div class="subtitle">Military Barracks</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_BARRACKS}_${city.id}">${city.barracks}</span> / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_BARRACK}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_BARRACK_COST}_${city.id}"><fmt:formatNumber value="${city.barrackCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_BARRACK}', ${city.id})" class="red">Close</button>
					</div>
					<div class="subtitle">Ports</div>
					<div class="description left_text">
						<span id="${JsonFields.CITY_PORTS}_${city.id}">${city.ports}</span> / 10<br>
						<button onclick="cityDecision('${CityDecisions.BUILD_PORT}', ${city.id})" class="blue">Build: $<span id="${JsonFields.CITY_PORT_COST}_${city.id}"><fmt:formatNumber value="${city.portCost}"/></span></button>
						<button onclick="cityDecision('${CityDecisions.REMOVE_PORT}', ${city.id})" class="red">Close</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</c:if>
<%@ include file="includes/bottom.jsp" %>