<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<%--@elvariable id="costs" type="com.watersfall.clocgame.action.PolicyActions"--%>
<%-- POLICIES --%>
<div class="main">
	<%@ include file="includes/results.jsp" %>
	<h1><c:out value="${param['policies']}"/> Policy</h1>
	<table id="policy">
		<tr>
			<th style="width: 15%">
				Policy
			</th>
			<th style="width: 50%">
				Description
			</th>
			<th style="width: 20%;">
				Cost
			</th>
			<th style="width: 20%"></th>
		</tr>
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<p>You must be logged in to view this page!</p>
			</c:when>
			<c:when test="${param['policies'] == 'Economic'}">
				<tr>
					<td>
						Free Money
					</td>
					<td>
						Acquire some free money, the Free Market way.
					</td>
					<td>
						Free!
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('freemoneycapitalist')">Get Rich
						</button>
					</td>
				</tr>
				<tr>
					<td>
						Free Money
					</td>
					<td>
						Acquire some free money, the Communist way.
					</td>
					<td>
						Free!
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('freemoneycommunist')">Get Rich
						</button>
					</td>
				</tr>
			</c:when>
			<c:when test="${param['policies'] == 'Domestic'}">
				<tr>
					<td>
						Increase Arrest Quotas
					</td>
					<td>
						Tell your police force they need to arrest more criminals! Increases stability, but lowers
						approval and moves your government to the right.
					</td>
					<td>
						$<c:out value="${costs.crackdownCost}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('crackdown')">Crackdown</button>
					</td>
				</tr>
				<tr>
					<td>
						Pardon Petty Criminals
					</td>
					<td>
						Release the jaywalkers from their cells! Decreases stability, but increases approval and moves
						your government to the left.
					</td>
					<td>
						$<c:out value="${costs.freeCost}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('free')">Free</button>
					</td>
				</tr>
			</c:when>
			<c:when test="${param['policies'] == 'Foreign'}">
				<tr>
					<td>
						Align With The Entente
					</td>
					<td>
						Praise France's Democracy, hoping to make them like you.
					</td>
					<td>
						$<c:out value="${costs.alignCost}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('alignentente')">Praise</button>
					</td>
				</tr>
				<tr>
					<td>
						Align With The Central Powers
					</td>
					<td>
						Admire the German <i>Stahlhelm</i>, hoping to protect yourself from shrapnel.
					</td>
					<td>
						$<c:out value="${costs.alignCost}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('aligncentral')">Admire</button>
					</td>
				</tr>
				<tr>
					<td>
						Declare Neutrality
					</td>
					<td>
						Go out on stage and celebrate your people's strength!
					</td>
					<td>
						$<c:out value="${costs.alignCost}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('alignneutral')">Celebrate</button>
					</td>
				</tr>
				<tr>
					<td>
						Form Treaty
					</td>
					<td>
						Create an international alliance to keep yourself safe
					</td>
					<td>
						$0k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="window.location.href = '/createtreaty.jsp'">Create</button>
					</td>
				</tr>
			</c:when>
			<c:when test="${param['policies'] == 'Military'}">
				<tr>
					<td>
						Build Weapons
					</td>
					<td>
						Build some weapons to equip your army
					</td>
					<td>
						<c:out value="${costs.weaponsSteel}"/> Steel
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('buildweapons')">Build</button>
					</td>
				</tr>
				<tr>
					<td>
						Build Artillery
					</td>
					<td>
						Build some artillery guns to increase the lethality of your army
					</td>
					<td>
						<c:out value="${costs.artillerySteel}"/> Steel and ${costs.artilleryNitrogen} Nitrogen
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="policy('buildartillery')">Build</button>
					</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
</div>
</body>
</html>