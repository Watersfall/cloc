<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="id" type="java.lang.Integer"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:choose>
		<c:when test="${empty id}">
			<p>You have visited this page incorrectly!</p>
		</c:when>
		<c:when test="${nation == null}">
			<p>This nation does not exist!</p>
		</c:when>
		<c:when test="${not empty user and not empty nation and nation.id == user}">
			<c:redirect url="/main/"/>
		</c:when>
		<c:otherwise>
			<h1><c:out value="${nation.cosmetic.nationTitle}"/><br>of<br>${nation.cosmetic.nationName}</h1>
			<img class="veryLarge" src="/user/flag/${nation.cosmetic.flag}" alt="flag"/>
			<p><br><c:out value="${nation.cosmetic.description}"/></p><br>
			<img class="veryLong" src="/user/portrait/${nation.cosmetic.portrait}" alt="portrait"/>
			<h1><c:out value="${nation.cosmetic.leaderTitle}"/><c:out value=" "/><c:out value=" ${nation.cosmetic.username}"/></h1>
			<ui:lastlogin time="${nation.lastLogin}"/>
			<h2>Domestic</h2>
			<table class="standardTable nationTable">
				<tr>
					<td><p>Approval</p></td>
					<td><p><cloc:approval value="${nation.domestic.approval}"/></p></td>
				</tr>
				<tr>
					<td><p>Government</p></td>
					<td><p><cloc:government value="${nation.domestic.government}"/></p></td>
				</tr>
				<tr>
					<td><p>Stability</p></td>
					<td><p><cloc:stability value="${nation.domestic.stability}"/></p></td>
				</tr>
				<tr>
					<td><p>Land</p></td>
					<td><p><fmt:formatNumber value="${nation.domestic.land}"/>km<sup>2</sup></p></td>
				</tr>
				<tr>
					<td><p>Rebel Threat</p></td>
					<td><p><cloc:rebels value="${nation.domestic.rebels}"/></p></td>
				</tr>
				<tr>
					<td><p>Population</p></td>
					<td><p><fmt:formatNumber value="${nation.totalPopulation}"/> People</p></td>
				</tr>
			</table>
			<h2>Economy</h2>
			<table class="standardTable nationTable">
				<tr>
					<td><p>Economic System</p></td>
					<td><p><cloc:economic value="${nation.economy.economic}"/></p></td>
				</tr>
				<tr>
					<td><p>Gross Domestic Product</p></td>
					<td><p>$<fmt:formatNumber value="${nation.economy.gdp}"/> Million</p></td>
				</tr>
				<tr>
					<td><p>Growth</p></td>
					<td><p>$<fmt:formatNumber value="${nation.economy.growth}"/> Million per Month</p></td>
				</tr>
			</table>
			<h2>Foreign</h2>
			<table class="standardTable nationTable">
				<tr>
					<td><p>Region</p></td>
					<td><p><c:out value="${nation.foreign.region.name}"/></p></td>
				</tr>
				<tr>
					<td><p>Official Alignment</p></td>
					<td><p><cloc:alignment value="${nation.foreign.alignment}"/></p></td>
				</tr>
				<tr>
					<td><p>Treaty Membership</p></td>
					<td><p>
						<c:if test="${nation.treaty != null}">
							<a href="${pageContext.request.contextPath}/treaty/${nation.treaty.id}"><c:out value="${nation.treaty.name}"/></a>
						</c:if>
						<c:if test="${nation.treaty == null}">
							None
						</c:if>
					</p></td>
				</tr>
			</table>
			<h2>Military</h2>
			<table class="standardTable nationTable">
				<caption><p>Army</p></caption>
				<tr>
					<td><p>Size</p></td>
					<td><p><fmt:formatNumber value="${nation.army.size}"/>k Personnel</p></td>
				</tr>
				<tr>
					<td><p>Training</p></td>
					<td><p>${nation.army.training}%</p></td>
				</tr>
				<tr>
					<td><p>Equipment</p></td>
					<td><p><fmt:formatNumber value="${nation.totalInfantryEquipment}"/> / <fmt:formatNumber value="${nation.army.size * 1000}"/> needed</p></td>
				</tr>
				<tr>
					<td><p>Artillery</p></td>
					<td><p><fmt:formatNumber value="${nation.army.artillery}"/> Pieces</p></td>
				</tr>
			</table>
			<br><br>
			<table class="standardTable nationTable">
				<caption><p>Navy</p></caption>
				<tr>
					<td><p>Destroyers</p></td>
					<td><p><fmt:formatNumber value="${nation.military.destroyers}"/></p></td>
				</tr>
				<tr>
					<td><p>Cruisers</p></td>
					<td><p><fmt:formatNumber value="${nation.military.cruisers}"/></p></td>
				</tr>
				<tr>
					<td><p>Battleships</p></td>
					<td><p><fmt:formatNumber value="${nation.military.battleships}"/></p></td>
				</tr>
				<tr>
					<td><p>Submarines</p></td>
					<td><p><fmt:formatNumber value="${nation.military.submarines}"/></p></td>
				</tr>
				<tr>
					<td><p>Troop Transports</p></td>
					<td><p><fmt:formatNumber value="${nation.military.transports}"/></p></td>
				</tr>
			</table>
			<br><br>
			<table class="standardTable nationTable">
				<caption><p>Airforce</p></caption>
				<tr>
					<td><p>Fighters</p></td>
					<td><p><i>Unknown</i></p></td>
				</tr>
				<tr>
					<td><p>Zeppelins</p></td>
					<td><p><i>Unknown</i></p></td>
				</tr>
			</table>
			<c:if test="${not empty user}">
				<h2>Diplomacy</h2>
				<table class="standardTable nationTable">
					<tr>
						<td><p>Send Money</p></td>
						<td>
							<label for="amountCash"></label><input type="number" id="amountCash" name="sendcash" min="0">
							<button type="submit" onclick="send('sendmoney', document.getElementById('amountCash').value, '${nation.id}')">Send</button>
						</td>
					</tr>
					<tr>
						<td><p>Send Iron</p></td>
						<td>
							<label for="amountIron"></label><input type="number" id="amountIron" name="sendiron" min="0">
							<button type="submit" onclick="send('sendiron', document.getElementById('amountIron').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td><p>Send Coal</p></td>
						<td>
							<label for="amountCoal"></label><input type="number" id="amountCoal" name="sendcoal" min="0">
							<button type="submit" onclick="send('sendcoal', document.getElementById('amountCoal').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td><p>Send Oil</p></td>
						<td>
							<label for="amountOil"></label><input type="number" id="amountOil" name="sendoil" min="0">
							<button type="submit" onclick="send('sendoil', document.getElementById('amountOil').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td><p>Send Steel</p></td>
						<td>
							<label for="amountSteel"></label><input type="number" id="amountSteel" name="sendsteel" min="0">
							<button type="submit" onclick="send('sendsteel', document.getElementById('amountSteel').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
					<tr>
						<td><p>Send Nitrogen</p></td>
						<td>
							<label for="amountNitrogen"></label><input type="number" id="amountNitrogen" name="sendnitrogen" min="0">
							<button type="submit" onclick="send('sendnitrogen', document.getElementById('amountNitrogen').value, '${nation.id}')">Send
							</button>
						</td>
					</tr>
				</table>
				<h2>War</h2>
				<c:if test="${nation.atWar}">
					<c:if test="${nation.offensive != null}">
						Offensive war against ${nation.offensive.nationUrl}<br>
					</c:if>
					<c:if test="${nation.defensive != null}">
						Defensive war against ${nation.defensive.nationUrl}<br>
					</c:if>
				</c:if>
				<c:if test="${!nation.atWar}">
					<i>None</i><br>
				</c:if>
				<c:if test="${home.canDeclareWar(nation) == null}">
					<button id="decc" onclick="declareWar(${nation.id})">Declare War</button><br>
				</c:if>
				<c:if test="${home.isAtWarWith(nation)}">
					<div class="categories">
						<%@include file="includes/land.jsp"%>
						<%@include file="includes/air.jsp"%>
						<%@include file="includes/navy.jsp"%>
					</div>
					<br>
					<button onclick="send('peace', null, ${nation.id});">Offer Peace</button>
				</c:if>
			</c:if>
		</c:otherwise>
	</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>