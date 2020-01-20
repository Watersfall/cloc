<%@ include file="default.jsp" %>
<%--@elvariable id="city" type="com.watersfall.clocgame.model.nation.City"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="city">
	<c:choose>
		<c:when test="${empty id}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${empty city}">
					<p>This city does not exist!</p>
				</c:when>
				<c:when test="${home != null && city.owner == home.id}">
					<c:set var="unicost" value="${city.universityCost}"/>
					<c:set var="factorycost" value="${city.factoryCost}"/>
					<style>
						.rename
						{
							font-size: 1em;
							margin: 0 !important;
							padding: 0 !important;
							outline: transparent !important;
							background: transparent !important;
							color: white !important;
							min-width: 1em !important;
							width: auto !important;
						}
					</style>
					<script>
						function changeCityName()
						{
							let inputBox = "<input class=\"rename\" type=\"text\" name=\"name\" value=\"<c:out value='${city.name}'/>\" id=\"name\"/>" +
								"  <img style=\"width: 0.5em;\" src=\"${pageContext.request.contextPath}/images/ui/checkmark.svg\" alt=\"submit\" onclick=\"submitCityName(\'${city.id}\');\"/>" +
								"  <img style=\"width: 0.5em;\" src=\"${pageContext.request.contextPath}/images/ui/cancel.svg\" alt=\"submit\" onclick=\"cancelCityName()\"/>";
							let city = document.getElementById("cityName");
							city.innerHTML = inputBox;
						}
						function submitCityName(id)
						{
							document.getElementById('resultsContainer').style.visibility = "visible";
							document.getElementById("result").innerHTML = "<p>Loading...</p>";
							var xhttp = new XMLHttpRequest();
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
										document.getElementById("cityName").innerHTML = document.getElementById("name").value + "<img style=\"width: 0.5em\" src=\"${pageContext.request.contextPath}/images/ui/edit.svg\" alt=\"edit\" onclick=\"changeCityName();\"/>";
									}
								}
							};
						}
						function cancelCityName()
						{
							document.getElementById("cityName").innerHTML = "<c:out value="${city.name}  "/>  <img style=\"width: 0.5em\" src=\"${pageContext.request.contextPath}/images/ui/edit.svg\" alt=\"edit\" onclick=\"changeCityName();\"/>";
						}
					</script>
					<h1 id="cityName"><c:out value="${city.name}  "/><img style="width: 0.5em" src="${pageContext.request.contextPath}/images/ui/edit.svg" alt="edit" onclick="changeCityName();"/></h1>
					<ul>
						<li class="cityElements">
							<h3>Coal Mines</h3>
							<img src="${pageContext.request.contextPath}/images/city/coalmine.png" alt="coal mine"/>
							<p>
								<fmt:formatNumber value="${city.coalMines}"/> coal mines producing <fmt:formatNumber value="${city.coalProduction.total}" maxFractionDigits="2"/> tons of coal per week
								<br>
								(<fmt:formatNumber value="${city.coalProduction.total / city.coalMines}" maxFractionDigits="2"/> tons per week per mine)
							</p>
							<button onclick="cityDecision('coalmine', '${city.id}')">Build - $<fmt:formatNumber value="${city.mineCost}"/>k</button>
							<button ${city.coalMines <= 0 ? 'disabled' : ''} onclick="cityDecision('uncoalmine', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Iron Mines</h3>
							<img src="${pageContext.request.contextPath}/images/city/ironmine.png" alt="iron mine"/>
							<p>
								<fmt:formatNumber value="${city.ironMines}"/> iron mines producing <fmt:formatNumber value="${city.ironProduction.total}" maxFractionDigits="2"/> tons of iron per week
								<br>
								(<fmt:formatNumber value="${city.ironProduction.total / city.ironMines}" maxFractionDigits="2"/> tons per week per mine)
							</p>
							<button onclick="cityDecision('ironmine', '${city.id}')">Build - $<fmt:formatNumber value="${city.mineCost}"/>k</button>
							<button ${city.ironMines <= 0 ? 'disabled' : ''} onclick="cityDecision('unironmine', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Oil Wells</h3>
							<img src="${pageContext.request.contextPath}/images/city/oilwell.png" alt="iron mine"/>
							<p>
								<fmt:formatNumber value="${city.oilWells}"/> oil wells producing <fmt:formatNumber value="${city.oilProduction.total}" maxFractionDigits="2"/> tons of oil per week
								<br>
								(<fmt:formatNumber value="${city.oilProduction.total / city.oilWells}" maxFractionDigits="2"/> tons per week per well)
							</p>
							<button onclick="cityDecision('drill', '${city.id}')">Build - $<fmt:formatNumber value="${city.wellCost}"/>k</button>
							<button ${city.oilWells <= 0 ? 'disabled' : ''} onclick="cityDecision('undrill', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Civilian Factories</h3>
							<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="civilian factory"/>
							<p>
								<fmt:formatNumber value="${city.industryCivilian}"/> civilian factories producing <fmt:formatNumber value="${city.steelProduction.total}" maxFractionDigits="2"/> tons of steel per week
								<br>
								(<fmt:formatNumber value="${city.steelProduction.total / city.industryCivilian}" maxFractionDigits="2"/> tons per week per factory)
							</p>
							<button onclick="cityDecision('industrialize', '${city.id}')">
								Build - <fmt:formatNumber value="${factorycost.iron}"/> Iron, <fmt:formatNumber value="${factorycost.coal}"/> Coal, and <fmt:formatNumber value="${factorycost.steel}"/> Steel
							</button>
							<button ${city.industryCivilian <= 0 ? 'disabled' : ''} onclick="cityDecision('unindustrialize', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Military Factories</h3>
							<img src="${pageContext.request.contextPath}/images/city/mil.png" alt="military factory"/>
							<p>

							</p>
							<button onclick="cityDecision('militarize', '${city.id}')">
								Build - <fmt:formatNumber value="${factorycost.iron}"/> Iron, <fmt:formatNumber value="${factorycost.coal}"/> Coal, and <fmt:formatNumber value="${factorycost.steel}"/> Steel
							</button>
							<button ${city.industryMilitary <= 0 ? 'disabled' : ''} onclick="cityDecision('unmilitarize', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Nitrogen Plants</h3>
							<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="nitrogen factory"/>
							<p>
								<fmt:formatNumber value="${city.industryNitrogen}"/> nitrogen factories producing  <fmt:formatNumber value="${city.nitrogenProduction.total}" maxFractionDigits="2"/> tons of nitrogen per week
								<br>
								(<fmt:formatNumber value="${city.nitrogenProduction.total / city.industryNitrogen}" maxFractionDigits="2"/> tons per week per factory)
							</p>
							<button onclick="cityDecision('nitrogenplant', '${city.id}')">
								Build - <fmt:formatNumber value="${factorycost.iron}"/> Iron, <fmt:formatNumber value="${factorycost.coal}"/> Coal, and <fmt:formatNumber value="${factorycost.steel}"/> Steel
							</button>
							<button ${city.industryNitrogen <= 0 ? 'disabled' : ''} onclick="cityDecision('unnitrogenplant', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Universities</h3>
							<img src="${pageContext.request.contextPath}/images/city/uni.png" alt="university"/>
							<p>
								<fmt:formatNumber value="${city.universities}"/> universities producing  <fmt:formatNumber value="${city.researchProduction.total}" maxFractionDigits="2"/> research per week
								<br>
								(${1} research per week per university)
							</p>
							<button onclick="cityDecision('university', '${city.id}')">
								Build - <fmt:formatNumber value="${unicost.iron}"/> Iron, <fmt:formatNumber value="${unicost.coal}"/> Coal, and <fmt:formatNumber value="${unicost.steel}"/> Steel
							</button>
							<button ${city.universities <= 0 ? 'disabled' : ''} onclick="cityDecision('ununiversity', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Railroads</h3>
							<img src="${pageContext.request.contextPath}/images/city/railroad.png" alt="railroad"/>
							<p>${city.railroads} / 10 Railroads</p>
							<button ${city.railroads >= 10 ? 'disabled' : ''} onclick="cityDecision('railroad', '${city.id}')">Build - <fmt:formatNumber value="${city.railCost}"/></button>
							<button ${city.railroads <= 0 ? 'disabled' : ''} onclick="cityDecision('unrailroad', '${city.id}')">Close</button>

						</li>
						<li class="cityElements">
							<h3>Military Barracks</h3>
							<img src="${pageContext.request.contextPath}/images/city/barrack.png" alt="barrack"/>
							<p>${city.barracks} / 10 Military Barracks</p>
							<button ${city.barracks >= 10 ? 'disabled' : ''} onclick="cityDecision('barrack', '${city.id}')">Build - <fmt:formatNumber value="${city.barrackCost}"/></button>
							<button ${city.barracks <= 0 ? 'disabled' : ''} onclick="cityDecision('unbarrack', '${city.id}')">Close</button>
						</li>
						<c:if test="${city.coastal}">
							<li class="cityElements">
								<h3>Ports</h3>
								<img src="${pageContext.request.contextPath}/images/city/port.png" alt="port"/>
								<p>${city.ports} / 10 Ports</p>
								<button ${city.ports >= 10 ? 'disabled' : ''} onclick="cityDecision('port', '${city.id}')">Build - <fmt:formatNumber value="${city.portCost}"/></button>
								<button ${city.ports <= 0 ? 'disabled' : ''} onclick="cityDecision('unport', '${city.id}')">Close</button>
							</li>
						</c:if>
					</ul>
				</c:when>
				<c:otherwise>
					<h1>${city.name}</h1>
					<table id="nation">
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
		</c:otherwise>
	</c:choose>
</div>