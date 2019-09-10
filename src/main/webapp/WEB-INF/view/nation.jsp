<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<script src="${pageContext.request.contextPath}/js/nation.js"></script>
<div class="main">
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
					<td><c:out value="${nation.domestic.land}"/>km<sup>2</sup></td>
				</tr>
				<tr>
					<td>Rebel Threat</td>
					<td><cloc:rebels value="${nation.domestic.rebels}"/></td>
				</tr>
				<tr>
					<td>Population</td>
					<td><c:out value="${nation.domestic.population}"/> People</td>
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
					<td>$<c:out value="${nation.economy.gdp}"/> Million</td>
				</tr>
				<tr>
					<td>Growth</td>
					<td>$<c:out value="${nation.economy.growth}"/> Million per Month</td>
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
					<td><c:out value="${nation.treaty.name}"/></td>
				</tr>
			</table>
			<h1>Military</h1>
			<table id="nation">
				<caption>Army</caption>
				<tr>
					<th>Region</th>
					<th>Size</th>
					<th>Training</th>
					<th>Equipment</th>
					<th>Reinforce</th>
				</tr>
				<c:forEach var="i" items="${nation.armies.armies}">
					<tr>
						<td>${i.value.region.name}</td>
						<td>${i.value.army}</td>
						<td>${i.value.training}</td>
						<td>${i.value.weapons}</td>
						<td>${i.value.id}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<table id="nation">
				<caption>Navy</caption>
				<tr>
					<td>Destroyers</td>
					<td><c:out value="${nation.military.destroyers}"/></td>
				</tr>
				<tr>
					<td>Cruisers</td>
					<td><c:out value="${nation.military.cruisers}"/></td>
				</tr>
				<tr>
					<td>Battleships</td>
					<td><c:out value="${nation.military.battleships}"/></td>
				</tr>
				<tr>
					<td>Submarines</td>
					<td><c:out value="${nation.military.submarines}"/></td>
				</tr>
				<tr>
					<td>Troop Transports</td>
					<td><c:out value="${nation.military.transports}"/></td>
				</tr>
			</table>
			<br>
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
							<input type="number" id="amountCash" name="sendcash" min="0">
							<button type="submit" onclick="sendCash(document.getElementById('amountCash').value, <c:out
									value="${nation}"/>);">Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Iron</td>
						<td>
							<input type="number" id="amountIron" name="sendiron" min="0">
							<button type="submit"
									onclick="sendIron(document.getElementById('amountIron').value, <c:out value="${nation}"/>);">
								Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Coal</td>
						<td>
							<input type="number" id="amountCoal" name="sendcoal" min="0">
							<button type="submit"
									onclick="sendCoal(document.getElementById('amountCoal').value, <c:out value="${nation}"/>);">
								Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Oil</td>
						<td>
							<input type="number" id="amountOil" name="sendoil" min="0">
							<button type="submit"
									onclick="sendOil(document.getElementById('amountOil').value, <c:out value="${nation}"/>);">
								Send
							</button>
						</td>
					</tr>
					<tr>
						<td>Send Steel</td>
						<td>
							<input type="number" id="amountSteel" name="sendsteel" min="0">
							<button type="submit"
									onclick="sendSteel(document.getElementById('amountSteel').value, <c:out value="${nation}"/>);">
								Send
							</button>
						</td>
					</tr>
				</table>
				<c:choose>
					<c:when test="${home.defensive == nation.id || home.offensive == nation.id}">
						<form action="nation.do" method="POST">
							<input type="hidden" name="action" value="land"/>
							<input type="hidden" name="id" value="${nation.id}"/>
							<button type="submit">Attack!</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="nation.do" method="POST">
							<input type="hidden" name="action" value="war"/>
							<input type="hidden" name="id" value="${nation.id}"/>
							<button type="submit">Declare War</button>
						</form>
					</c:otherwise>
				</c:choose>

			</c:if>
		</c:otherwise>
	</c:choose>
	<p><br><br><br></p>
</div>
<p><br><br><br></p>
</body>
</html>