<%@ include file="includes/top.jsp" %>
<%--@elvariable id="city" type="com.watersfall.clocgame.model.nation.City"--%>
<% pageContext.setAttribute("key", TextKey.Resource.TOTAL_GAIN); %>
<c:if test="${city == null}">
	<div class="title">Cities</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<c:forEach var="city" items="${home.cities.values()}">
					<div class="subtitle"><c:out value="${city.url} " escapeXml="false"/> <img title="Edit City Name" class="match_text" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit"/> (${city.freeSlots} free slots)</div>
					<table class="nation nation_left">
						<tr>
							<td colspan="3">Coal Mines</td>
							<td rowspan="2"><button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.coalMines}</td>
						</tr>
						<tr>
							<td colspan="3">Iron Mines</td>
							<td rowspan="2"><button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.ironMines}</td>
						</tr>
						<tr>
							<td colspan="3">Oil Wells</td>
							<td rowspan="2"><button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button></td>
							<td rowspan="2"><button class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.oilWells}</td>
						</tr>
						<tr>
							<td colspan="3">Civilian Industry</td>
							<td rowspan="2"><button class="blue">Build: ${city.factoryCost}</button></td>
							<td rowspan="2"><button class="red">Remove</button></td>
						</tr>
						<tr>
							<td>${city.industryCivilian}</td>
						</tr>
						<tr>
							<td colspan="3">Military Industry</td>
							<td rowspan="2"><button class="blue">Build: ${city.factoryCost}</button></td>
							<td rowspan="2"><button class="red">Remove</button></td>
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
	<div class="title">${city.name}</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="subtitle">
					<c:out value="${city.size.name} "/><img title="Edit City Name" class="match_text black" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit"/>
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
					<b>${city.coalMines}</b> coal mine${city.coalMines == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.coalProduction.get(key)}" maxFractionDigits="2"/></b> coal per month<br>
					<button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
					<button class="red">Remove</button>
				</div>
				<div class="subtitle">Iron Mines</div>
				<div class="description left_text">
					<b>${city.ironMines}</b> iron mine${city.ironMines == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.ironProduction.get(key)}" maxFractionDigits="2"/></b> iron per month<br>
					<button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
					<button class="red">Remove</button>
				</div>
				<div class="subtitle">Oil Wells</div>
				<div class="description left_text">
					<b>${city.oilWells}</b> oil well${city.oilWells == 1 ? '' : 's'} producing <b><fmt:formatNumber value="${city.oilProduction.get(key)}" maxFractionDigits="2"/></b> oil per month<br>
					<button class="blue">Build: $<fmt:formatNumber value="${city.mineCost}"/></button>
					<button class="red">Remove</button>
				</div>
			</div>
		</div>
		<div class="column">
			<div class="tile">
				<div class="subtitle">Civilian Industry</div>
				<div class="description left_text">
					<b>${city.industryCivilian}</b> civilian factor${city.industryCivilian == 1 ? 'y' : 'ies'} producing <b><fmt:formatNumber value="${city.steelProduction.get(key)}" maxFractionDigits="2"/></b> steel per month<br>
					<button class="blue">Build: ${city.factoryCost}</button>
					<button class="red">Remove</button>
				</div>
				<div class="subtitle">Military Industry</div>
				<div class="description left_text">
					<b>${city.industryMilitary}</b> military factor${city.industryMilitary == 1 ? 'y' : 'ies'}<br>
					<button class="blue">Build: ${city.factoryCost}</button>
					<button class="red">Remove</button>
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
					<button class="blue">Build: <fmt:formatNumber value="${city.railCost}"/></button>
					<button class="red">Close</button>
				</div>
				<div class="subtitle">Military Barracks</div>
				<div class="description left_text">
					${city.barracks} / 10<br>
					<button class="blue">Build: <fmt:formatNumber value="${city.barrackCost}"/></button>
					<button class="red">Close</button>
				</div>
				<div class="subtitle">Ports</div>
				<div class="description left_text">
					${city.ports} / 10<br>
					<button class="blue">Build: <fmt:formatNumber value="${city.portCost}"/></button>
					<button class="red">Close</button>
				</div>
			</div>
		</div>
	</div>
</c:if>
<%@ include file="includes/bottom.jsp" %>