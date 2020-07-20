<%--@elvariable id="city" type="com.watersfall.clocgame.model.nation.City"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="taglibs.jsp" %>
<script>
	let cityName = "";

	function setCityName(name)
	{
		cityName = name;
	}

	function changeCityName()
	{
		setCityName(document.getElementById('cityNameSpan').innerHTML);
		toggle('cityName');
		toggle('nameChange');
	}
	function submitCityName(id)
	{
		document.getElementById('resultsContainer').style.visibility = "visible";
		document.getElementById("result").innerHTML = "<p>Loading...</p>";
		let xhttp = new XMLHttpRequest();
		setCityName(document.getElementById("name").value);
		let params = "name=" + document.getElementById("name").value;
		xhttp.open("POST", "/city/" + id);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(params);
		xhttp.onreadystatechange = function()
		{
			if(xhttp.readyState === 4 && xhttp.status === 200)
			{
				document.getElementById("result").innerHTML = xhttp.responseText;
				if(xhttp.responseText.indexOf("updated") !== -1)
				{
					document.getElementById("cityNameSpan").innerHTML = cityName;
					cancelCityName();
				}
			}
		};
	}
	function cancelCityName()
	{
		document.getElementById('name').value = cityName;
		toggle('cityName');
		toggle('nameChange');
	}
</script>
<div id="city">
	<c:choose>
		<c:when test="${empty id}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${empty city}">
			<p>This city does not exist!</p>
		</c:when>
		<c:when test="${home != null && city.owner == home.id}">
			<h1 class="toggleable" style="display: block;">
				<span id="cityName" class="toggleable" style="display: block;">
					<span id="cityNameSpan"><c:out value="${city.name}"/></span>
					<img class="sidePad extraTiny" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit" onclick="changeCityName()">
				</span>
				<span id="nameChange" class="toggleable">
					<label for="name"></label>
					<input class="rename" type="text" name="name" value="<c:out value='${city.name}'/>" id="name"/>
					<img class="extraTiny" src="${pageContext.request.contextPath}/images/ui/checkmark.svg" alt="submit" onclick="submitCityName('${city.id}');"/>
					<img class="extraTiny" src="${pageContext.request.contextPath}/images/ui/cancel.svg" alt="submit" onclick="cancelCityName()"/>
				</span>
			</h1>
			<div>
				<div class="element" style="min-width: 50%;">
					<h3>${city.size.name}</h3>
					<h4>${city.freeSlots} Free Build Slots (out of ${city.buildSlots} total)</h4>
					<img src="${pageContext.request.contextPath}/images/city/size/${city.size.name()}.png" alt="city">
					<table class="standardTable">
						<tr>
							<td style="width: 50%;"><p>Population</p></td>
							<td onclick="toggleTab('Population');">
								<img class="floatLeft tiny" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="arrow">
								<p class="clickable"><fmt:formatNumber value="${city.population}"/></p>
								<cloc:dropdown value="${city.population}" name="Population" map="${city.getPopulationGrowth(home)}" nation="${home}"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div>
				<div class="element">
					<h3>Coal Mines</h3>
					<img src="${pageContext.request.contextPath}/images/city/coalmine.png" alt="coal mine"/>
					<br><br>
					<p>
						<b><fmt:formatNumber value="${city.coalMines}"/></b> Coal Mines producing
						<b><fmt:formatNumber value="${city.coalProduction.get('resource.total')}"/></b> coal per month
					</p>
					<c:if test="${city.coalMines > 0}">
						<p>(
							<b><fmt:formatNumber value="${city.coalProduction.get('resource.total') / city.coalMines}" maxFractionDigits="2"/></b>
							coal per mine
							)</p>
					</c:if>
					<button onclick="cityDecision('coalmine', '${city.id}')">Build - $<fmt:formatNumber value="${city.mineCost}"/></button>
					<button ${city.coalMines <= 0 ? 'disabled' : ''} onclick="cityDecision('uncoalmine', '${city.id}')">Close</button>
				</div>
				<div class="element">
					<h3>Iron Mines</h3>
					<img src="${pageContext.request.contextPath}/images/city/ironmine.png" alt="iron mine"/>
					<br><br>
					<p>
						<b><fmt:formatNumber value="${city.ironMines}"/></b> Iron Mines producing
						<b><fmt:formatNumber value="${city.ironProduction.get('resource.total')}"/></b> iron per month
					</p>
					<c:if test="${city.ironMines > 0}">
						<p>(
							<b><fmt:formatNumber value="${city.ironProduction.get('resource.total') / city.coalMines}" maxFractionDigits="2"/></b>
							iron per mine
							)</p>
					</c:if>
					<button onclick="cityDecision('ironmine', '${city.id}')">Build - $<fmt:formatNumber value="${city.mineCost}"/></button>
					<button ${city.ironMines <= 0 ? 'disabled' : ''} onclick="cityDecision('unironmine', '${city.id}')">Close</button>
				</div>
				<div class="element">
					<h3>Oil Wells</h3>
					<img src="${pageContext.request.contextPath}/images/city/oilwell.png" alt="oil well"/>
					<br><br>
					<p>
						<b><fmt:formatNumber value="${city.oilWells}"/></b> Oil Wells producing
						<b><fmt:formatNumber value="${city.oilProduction.get('resource.total')}"/></b> oil per month
					</p>
					<c:if test="${city.oilWells > 0}">
						<p>(
							<b><fmt:formatNumber value="${city.oilProduction.get('resource.total') / city.oilWells}" maxFractionDigits="2"/></b>
							oil per well
							)</p>
					</c:if>
					<button onclick="cityDecision('drill', '${city.id}')">Build - $<fmt:formatNumber value="${city.wellCost}"/></button>
					<button ${city.oilWells <= 0 ? 'disabled' : ''} onclick="cityDecision('undrill', '${city.id}')">Close</button>
				</div>
			</div>
			<div>
				<div class="element">
					<h3>Civilian Industry</h3>
					<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="civilian factory"/>
					<br><br>
					<p>
						<b><fmt:formatNumber value="${city.industryCivilian}"/></b> Factories producing
						<b><fmt:formatNumber value="${city.steelProduction.get('resource.total')}"/></b> steel per month
					</p>
					<c:if test="${city.industryCivilian > 0}">
						<p>(
							<b><fmt:formatNumber value="${city.steelProduction.get('resource.total') / city.industryCivilian}" maxFractionDigits="2"/></b>
							steel per factory
							)</p>
					</c:if>
					<button onclick="cityDecision('industrialize', '${city.id}')">Build - ${city.factoryCost}</button>
					<button ${city.industryCivilian <= 0 ? 'disabled' : ''} onclick="cityDecision('unindustrialize', '${city.id}')">Close</button>
				</div>
				<div class="element">
					<h3>Military Industry</h3>
					<img src="${pageContext.request.contextPath}/images/city/mil.png" alt="military factory"/>
					<br><br>
					<p>

					</p>
					<button onclick="cityDecision('militarize', '${city.id}')">Build - ${city.factoryCost}</button>
					<button ${city.industryMilitary <= 0 ? 'disabled' : ''} onclick="cityDecision('unmilitarize', '${city.id}')">Close</button>
				</div>
				<div class="element">
					<h3>Nitrogen Industry</h3>
					<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="nitrogen factory"/>
					<br><br>
					<p>
						<b><fmt:formatNumber value="${city.industryNitrogen}"/></b> Factories producing
						<b><fmt:formatNumber value="${city.nitrogenProduction.get('resource.total')}"/></b> nitrogen per month
					</p>
					<c:if test="${city.industryNitrogen > 0}">
						<p>(
							<b><fmt:formatNumber value="${city.nitrogenProduction.get('resource.total') / city.industryNitrogen}" maxFractionDigits="2"/></b>
							nitrogen per factory
							)</p>
					</c:if>
					<button onclick="cityDecision('nitrogen', '${city.id}')">Build - ${city.factoryCost}</button>
					<button ${city.industryNitrogen <= 0 ? 'disabled' : ''} onclick="cityDecision('unnitrogen', '${city.id}')">Close</button>
				</div>
			</div>
			<div>
				<div class="element">
					<h3>Infrastructure</h3>
					<img src="${pageContext.request.contextPath}/images/city/railroad.png" alt="infrastructure"/>
					<br><br>
					<p>${city.railroads} / 10</p>
					<button ${city.railroads >= 10 ? 'disabled' : ''} onclick="cityDecision('railroad', '${city.id}')">Build - ${city.railCost}</button>
					<button ${city.railroads <= 0 ? 'disabled' : ''} onclick="cityDecision('unrailroad', '${city.id}')">Close</button>
				</div>
				<div class="element">
					<h3>Military Barracks</h3>
					<img src="${pageContext.request.contextPath}/images/city/barrack.png" alt="barrack"/>
					<br><br>
					<p>${city.barracks} / 10</p>
					<button ${city.barracks >= 10 ? 'disabled' : ''} onclick="cityDecision('barrack', '${city.id}')">Build - ${city.barrackCost}</button>
					<button ${city.barracks <= 0 ? 'disabled' : ''} onclick="cityDecision('unbarrack', '${city.id}')">Close</button>
				</div>
				<c:if test="${city.coastal}">
					<div class="element">
						<h3>Ports</h3>
						<img src="${pageContext.request.contextPath}/images/city/port.png" alt="port"/>
						<br><br>
						<p>${city.ports} / 10</p>
						<button ${city.ports >= 10 ? 'disabled' : ''} onclick="cityDecision('port', '${city.id}')">Build - ${city.portCost}</button>
						<button ${city.ports <= 0 ? 'disabled' : ''} onclick="cityDecision('unport', '${city.id}')">Close</button>
					</div>
				</c:if>
			</div>
		</c:when>
		<c:otherwise>
			<h1>${city.name}</h1>
			<table class="standardTable">
				<tr>
					<td>Coal Mines</td>
					<td><fmt:formatNumber value="${city.coalMines}"/> Mines</td>
				</tr>
				<tr>
					<td>Iron Mines</td>
					<td><fmt:formatNumber value="${city.ironMines}"/> Mines</td>
				</tr>
				<tr>
					<td>Oil Wells</td>
					<td><fmt:formatNumber value="${city.oilWells}"/> Wells</td>
				</tr>
				<tr>
					<td>Civilian Factories</td>
					<td><fmt:formatNumber value="${city.industryCivilian}"/> Factories</td>
				</tr>
				<tr>
					<td>Military Factories</td>
					<td><fmt:formatNumber value="${city.industryMilitary}"/> Factories</td>
				</tr>
				<tr>
					<td>Nitrogen Plants</td>
					<td><fmt:formatNumber value="${city.industryNitrogen}"/> Factories</td>
				</tr>
				<tr>
					<td>Railroads</td>
					<td>${city.railroads} / 10 Infrastructure</td>
				</tr>
				<c:if test="${city.coastal == true}">
					<tr>
						<td>Naval Ports</td>
						<td>${city.ports} / 10 Naval Infrastructure</td>
					</tr>
				</c:if>
				<tr>
					<td>Military Barracks</td>
					<td>${city.barracks} / 10 Military Infrastructure</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</div>