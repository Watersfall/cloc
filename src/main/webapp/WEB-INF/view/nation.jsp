<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<c:choose>
		<c:when test="${param['id'] == null}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${nation == null}">
			<p>This nation does not exist!</p>
		</c:when>
		<c:when test="${not empty user and not empty nation and nation.id == user}">
			<c:redirect url="/main.jsp"/>
		</c:when>
		<c:otherwise>
			<div class="nation">
				<h1><c:out value="${nation.cosmetic.nationTitle}"/> <br> of <br><c:out value="${nation.cosmetic.nationName}"/></h1>
				<img class="flag" src="https://imgur.com/<c:out value="${nation.cosmetic.flag}"/>" alt="flag">
				<p><c:out value="${nation.cosmetic.description}"/></p>
				<img class="leader" src="https://imgur.com/<c:out value="${nation.cosmetic.portrait}"/>" alt="flag">
				<h1><c:out value="${nation.cosmetic.leaderTitle}"/>: <c:out value="${nation.cosmetic.username}"/></h1>
			</div>
			<h1>Domestic</h1>
			<table id="nation">
				<tr>
					<td>Approval</td>
					<td><cloc:approval value="${nation.domestic.approval}"/></td>
				</tr>
				<tr>
					<td>Government</td>
					<td><cloc:government value="${nation.domestic.government}"/></td>
				</tr>
				<tr>
					<td>Stability</td>
					<td><cloc:stability value="${nation.domestic.stability}"/></td>
				</tr>
				<tr>
					<td>Land</td>
					<td><fmt:formatNumber value="${nation.domestic.land}"/>km<sup>2</sup></td>
				</tr>
				<tr>
					<td>Rebel Threat</td>
					<td><cloc:rebels value="${nation.domestic.rebels}"/></td>
				</tr>
				<tr>
					<td>Population</td>
					<td><fmt:formatNumber value="${nation.domestic.population}"/> People</td>
				</tr>
			</table>
			<h1>Economy</h1>
			<table id="nation">
				<tr>
					<td>Economic System</td>
					<td><cloc:economic value="${nation.economy.economic}"/></td>
				</tr>
				<tr>
					<td>Gross Domestic Product</td>
					<td>$<fmt:formatNumber value="${nation.economy.gdp}"/> Million</td>
				</tr>
				<tr>
					<td>Growth</td>
					<td>$<fmt:formatNumber value="${nation.economy.growth}"/> Million per Month</td>
				</tr>
			</table>
			<h1>Foreign</h1>
			<table id="nation">
				<tr>
					<td>Region</td>
					<td><c:out value="${nation.foreign.region.name}"/></td>
				</tr>
				<tr>
					<td>Official Alignment</td>
					<td><cloc:alignment value="${nation.foreign.alignment}"/></td>
				</tr>
				<tr>
					<td>Treaty Membership</td>
					<td>
						<c:if test="${nation.treaty != null}">
							<a href="${pageContext.request.contextPath}/treaty.jsp?id=${nation.treaty.id}">${nation.treaty.name}</a>
						</c:if>
						<c:if test="${nation.treaty == null}">
							None
						</c:if>
					</td>
				</tr>
			</table>
			<h1>Military</h1>
			<table id="nation">
				<caption>Army</caption>
				<tr>
					<td>Size</td>
					<td><fmt:formatNumber value="${nation.army.size}"/></td>
				</tr>
				<tr>
					<td>Training</td>
					<td>${nation.army.training}%</td>
				</tr>
				<tr>
					<td>Equipment</td>
					<td><fmt:formatNumber value="${nation.army.musket}"/> / <fmt:formatNumber value="${nation.army.size * 1000}"/> needed</td>
				</tr>
			</table>
			<br><br>
			<table id="nation">
				<caption>Navy</caption>
				<tr>
					<td>Destroyers</td>
					<td><fmt:formatNumber value="${nation.military.destroyers}"/></td>
				</tr>
				<tr>
					<td>Cruisers</td>
					<td><fmt:formatNumber value="${nation.military.cruisers}"/></td>
				</tr>
				<tr>
					<td>Battleships</td>
					<td><fmt:formatNumber value="${nation.military.battleships}"/></td>
				</tr>
				<tr>
					<td>Submarines</td>
					<td><fmt:formatNumber value="${nation.military.submarines}"/></td>
				</tr>
				<tr>
					<td>Troop Transports</td>
					<td><fmt:formatNumber value="${nation.military.transports}"/></td>
				</tr>
			</table>
			<br><br>
			<table id="nation">
				<caption>Airforce</caption>
				<tr>
					<td>Fighters</td>
					<td><i>Unknown</i></td>
				</tr>
				<tr>
					<td>Zeppelins</td>
					<td><i>Unknown</i></td>
				</tr>
			</table>
			<c:if test="${not empty user}">
				<h1>Diplomacy</h1>
				<table id="nation">
					<tr>
						<td>Send Money</td>
						<td>
							<label for="amountCash"></label><input type="number" id="amountCash" name="sendcash" min="0">
							<button type="submit" onclick="send('sendmoney', document.getElementById('amountCash').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Iron</td>
						<td>
							<label for="amountIron"></label><input type="number" id="amountIron" name="sendiron" min="0">
							<button type="submit" onclick="send('sendiron', document.getElementById('amountIron').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Coal</td>
						<td>
							<label for="amountCoal"></label><input type="number" id="amountCoal" name="sendcoal" min="0">
							<button type="submit" onclick="send('sendcoal', document.getElementById('amountCoal').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Oil</td>
						<td>
							<label for="amountOil"></label><input type="number" id="amountOil" name="sendoil" min="0">
							<button type="submit" onclick="send('sendoil', document.getElementById('amountOil').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Steel</td>
						<td>
							<label for="amountSteel"></label><input type="number" id="amountSteel" name="sendsteel" min="0">
							<button type="submit" onclick="send('sendsteel', document.getElementById('amountSteel').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
				</table>
				<button id="land" style="display: ${home.offensive == nation.id || home.defensive == nation.id ? 'inline' : 'none'}" onclick="send('land', null, '${nation.id}')">Attack!</button>
				<button id="decc" style="display: ${home.offensive == nation.id || home.defensive == nation.id ? 'none' : 'inline'}" onclick="declareWar(${nation.id})">Declare War</button>
			</c:if>
		</c:otherwise>
	</c:choose>
	<p><br><br><br></p>
</div></div>
<p><br><br><br></p>
</body>
</html>