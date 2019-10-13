<%@ include file="default.jsp" %>
<%--@elvariable id="city" type="com.watersfall.clocgame.model.nation.City"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="city">
	<c:choose>
		<c:when test="${empty param['id']}">
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
					<h1>${city.name}</h1>
					<ul>
						<li class="cityElements">
							<h3>Coal Mines</h3>
							<img src="${pageContext.request.contextPath}/images/city/coalmine.png" alt="coal mine"/>
							<p>
								${city.coalMines} coal mines producing <fmt:formatNumber value="${city.coalProduction.total}" maxFractionDigits="2"/> tons of coal per week
								<br>
								(<fmt:formatNumber value="${city.coalProduction.total / city.coalMines}" maxFractionDigits="2"/> tons per week per mine)
							</p>
							<button onclick="cityPolicy('coalmine', '${city.id}')">Build - $${city.mineCost}k</button>
							<button ${city.coalMines <= 0 ? 'disabled' : ''} onclick="cityPolicy('uncoalmine', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Iron Mines</h3>
							<img src="${pageContext.request.contextPath}/images/city/ironmine.png" alt="iron mine"/>
							<p>
								${city.ironMines} iron mines producing <fmt:formatNumber value="${city.ironProduction.total}" maxFractionDigits="2"/> tons of iron per week
								<br>
								(<fmt:formatNumber value="${city.ironProduction.total / city.ironMines}" maxFractionDigits="2"/> tons per week per mine)
							</p>
							<button onclick="cityPolicy('ironmine', '${city.id}')">Build - $${city.mineCost}k</button>
							<button ${city.ironMines <= 0 ? 'disabled' : ''} onclick="cityPolicy('unironmine', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Oil Wells</h3>
							<img src="${pageContext.request.contextPath}/images/city/oilwell.png" alt="iron mine"/>
							<p>
								${city.oilWells} oil wells producing <fmt:formatNumber value="${city.oilProduction.total}" maxFractionDigits="2"/> tons of oil per week
								<br>
								(<fmt:formatNumber value="${city.oilProduction.total / city.oilWells}" maxFractionDigits="2"/> tons per week per well)
							</p>
							<button onclick="cityPolicy('drill', '${city.id}')">Build - $${city.wellCost}k</button>
							<button ${city.oilWells <= 0 ? 'disabled' : ''} onclick="cityPolicy('undrill', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Civilian Factories</h3>
							<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="civilian factory"/>
							<p>
								${city.industryCivilian} civilian factories producing <fmt:formatNumber value="${city.steelProduction.total}" maxFractionDigits="2"/> tons of steel per week
								<br>
								(<fmt:formatNumber value="${city.steelProduction.total / city.industryCivilian}" maxFractionDigits="2"/> tons per week per factory)
							</p>
							<button onclick="cityPolicy('industrialize', '${city.id}')">Build - ${factorycost.iron} Iron, ${factorycost.coal} Coal, and ${factorycost.steel} Steel</button>
							<button ${city.industryCivilian <= 0 ? 'disabled' : ''} onclick="cityPolicy('unindustrialize', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Military Factories</h3>
							<img src="${pageContext.request.contextPath}/images/city/mil.png" alt="military factory"/>
							<p>
								${city.industryMilitary} military factories producing <fmt:formatNumber value="${city.weaponsProduction.total}" maxFractionDigits="2"/> weapons per week
								<br>
								(<fmt:formatNumber value="${city.weaponsProduction.total / city.industryMilitary}" maxFractionDigits="2"/> weapons per week per factory)
							</p>
							<button onclick="cityPolicy('militarize', '${city.id}')">Build - ${factorycost.iron} Iron, ${factorycost.coal} Coal, and ${factorycost.steel} Steel</button>
							<button ${city.industryMilitary <= 0 ? 'disabled' : ''} onclick="cityPolicy('unmilitarize', '${city.id}')">Close</button>
						</li>
						<li class="cityElements">
							<h3>Nitrogen Plants</h3>
							<img src="${pageContext.request.contextPath}/images/city/civ.png" alt="nitrogen factory"/>
							<p>
								${city.industryNitrogen} nitrogen factories producing  <fmt:formatNumber value="${city.nitrogenProduction.total}" maxFractionDigits="2"/> tons of nitrogen per week
								<br>
								(<fmt:formatNumber value="${city.nitrogenProduction.total / city.industryNitrogen}" maxFractionDigits="2"/> tons per week per factory)
							</p>
							<button onclick="cityPolicy('nitrogenplant', '${city.id}')">Build - ${factorycost.iron} Iron, ${factorycost.coal} Coal, and ${factorycost.steel} Steel</button>
							<button ${city.industryNitrogen <= 0 ? 'disabled' : ''} onclick="cityPolicy('unnitrogenplant', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Universities</h3>
							<img src="${pageContext.request.contextPath}/images/city/uni.png" alt="university"/>
							<p>
								${city.universities} universities producing  <fmt:formatNumber value="${city.researchProduction.total}" maxFractionDigits="2"/> research per week
								<br>
								(${1} research per week per university)
							</p>
							<button onclick="cityPolicy('university', '${city.id}')">Build - ${unicost.iron} Iron, ${unicost.coal} Coal, and ${unicost.steel} Steel</button>
							<button ${city.universities <= 0 ? 'disabled' : ''} onclick="cityPolicy('ununiversity', '${city.id}')">Close</button>
						</li>
					</ul>
					<ul>
						<li class="cityElements">
							<h3>Railroads</h3>
							<img src="${pageContext.request.contextPath}/images/city/railroad.png" alt="railroad"/>
							<p>${city.railroads} / 10 Railroads</p>
							<button ${city.railroads >= 10 ? 'disabled' : ''} onclick="cityPolicy('railroad', '${city.id}')">Build - ${city.railCost}</button>
							<button ${city.railroads <= 0 ? 'disabled' : ''} onclick="cityPolicy('unrailroad', '${city.id}')">Close</button>

						</li>
						<li class="cityElements">
							<h3>Military Barracks</h3>
							<img src="${pageContext.request.contextPath}/images/city/barrack.png" alt="barrack"/>
							<p>${city.barracks} / 10 Military Barracks</p>
							<button ${city.barracks >= 10 ? 'disabled' : ''} onclick="cityPolicy('barrack', '${city.id}')">Build - ${city.barrackCost}</button>
							<button ${city.barracks <= 0 ? 'disabled' : ''} onclick="cityPolicy('unbarrack', '${city.id}')">Close</button>
						</li>
						<c:if test="${city.coastal}">
							<li class="cityElements">
								<h3>Ports</h3>
								<img src="${pageContext.request.contextPath}/images/city/port.png" alt="port"/>
								<p>${city.ports} / 10 Ports</p>
								<button ${city.ports >= 10 ? 'disabled' : ''} onclick="cityPolicy('port', '${city.id}')">Build - ${city.portCost}</button>
								<button ${city.ports <= 0 ? 'disabled' : ''} onclick="cityPolicy('unport', '${city.id}')">Close</button>
							</li>
						</c:if>
					</ul>
				</c:when>
				<c:otherwise>
					<h1>${city.name}</h1>
					<table id="nation">
						<tr>
							<td>Coal Mines</td>
							<td>${city.coalMines} Mines</td>
						</tr>
						<tr>
							<td>Iron Mines</td>
							<td>${city.ironMines} Mines</td>
						</tr>
						<tr>
							<td>Oil Wells</td>
							<td>${city.oilWells} Wells</td>
						</tr>
						<tr>
							<td>Civilian Factories</td>
							<td>${city.industryCivilian} Factories</td>
						</tr>
						<tr>
							<td>Military Factories</td>
							<td>${city.industryMilitary} Factories</td>
						</tr>
						<tr>
							<td>Nitrogen Plants</td>
							<td>${city.industryNitrogen} Factories</td>
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