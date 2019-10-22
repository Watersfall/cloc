<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<%--@elvariable id="policy" type="com.watersfall.clocgame.action.PolicyActions"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%-- POLICIES --%>
<div class="container"><%@ include file="includes/results.jsp"%>

	<div class="main">
	<h1><c:out value="${param['policies']}"/> Policy</h1>
	<table id="nation">
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
						<button class="policyButton" type="submit" onclick="decision('freemoneycapitalist')">Get Rich
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
						<button class="policyButton" type="submit" onclick="decision('freemoneycommunist')">Get Rich
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
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ARREST)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('crackdown')">Crackdown</button>
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
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_FREE)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('free')">Free</button>
					</td>
				</tr>
				<tr>
					<td>
						Propaganda Campaign
					</td>
					<td>
						Put up posters saying how great you are! Increases approval. Cost is based on GDP and current approval
					</td>
					<td>
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_PROPAGANDA)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('propaganda')">Propaganda</button>
					</td>
				</tr>
				<c:if test="${home.offensive != 0 || home.defensive != 0}">
					<tr>
						<td>
							War Propaganda
						</td>
						<td>
							Rallying war speeches will make anyone love you right? Increases approval, but only available when at war. Cost is based on GDP and current approval
						</td>
						<td>
							$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_WAR_PROPAGANDA)}"/>k
						</td>
						<td>
							<button class="policyButton" type="submit" onclick="decision('warpropaganda')">Propaganda</button>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						Land Clearance
					</td>
					<td>
						Slash and burn some useless jungle to make room for our expanding economy
					</td>
					<td>
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_LAND_CLEARANCE)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('landclearance')">Burn</button>
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
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('alignentente')">Praise</button>
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
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('aligncentral')">Admire</button>
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
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('alignneutral')">Celebrate</button>
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
						Conscript
					</td>
					<td>
						Throw more men into your army at the cost of manpower and overall training.
					</td>
					<td>
						Manpower
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('conscript')">Conscript</button>
					</td>
				</tr>
				<tr>
					<td>
						Deconscript
					</td>
					<td>
						Maybe that's a little too much army...
					</td>
					<td>
						Nothing
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('deconscript')">Fire</button>
					</td>
				</tr>
				<tr>
					<td>
						Train
					</td>
					<td>
						Train your army
					</td>
					<td>
						$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_TRAIN)}"/>k
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('train')">Train</button>
					</td>
				</tr>
				<tr>
					<td>
						Build Muskets
					</td>
					<td>
						The finest of the 1850's, these muskets may not be "easy to load" or have fancy "rifled barrels" but they're better than nothing
					</td>
					<td>
						<c:forEach var="cost" items="${home.getPolicyCostMap(policy.ID_BUILD_MUSKETS).entrySet()}" varStatus="i">
							<c:if test="${i.index > 0 && !i.first && !i.last}">, </c:if>
							<c:if test="${i.last && i.count > 1}">, and </c:if>
							<c:out value="${cost.value} ${cost.key}"/>
						</c:forEach>
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('buildweapons')">Build</button>
					</td>
				</tr>
				<tr>
					<td>
						Build Artillery
					</td>
					<td>
						The most effective way to fight without fighting, artillery guns increase the strength of your army
					</td>
					<td>
						<c:forEach var="cost" items="${home.getPolicyCostMap(policy.ID_BUILD_ARTILLERY).entrySet()}" varStatus="i">
							<c:if test="${i.index > 0 && !i.first && !i.last}">, </c:if>
							<c:if test="${i.last && i.count > 2}">, and </c:if>
							<c:if test="${i.last && i.count == 2}"> and </c:if>
							<c:out value="${cost.value} ${cost.key}"/>
						</c:forEach>
					</td>
					<td>
						<button class="policyButton" type="submit" onclick="decision('buildartillery')">Build</button>
					</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
</div>
<%@ include file="includes/header.jsp" %>
</div>
</body>
</html>