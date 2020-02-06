<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="decisions" type="java.lang.String"--%>
<%--@elvariable id="policy" type="com.watersfall.clocgame.action.PolicyActions"--%>
<%@ include file="includes/defaultTop.jsp" %>
<c:choose>
	<c:when test="${empty home}">
		<p>You must be logged in to view this page</p>
	</c:when>
	<c:otherwise>
		<br>
		<div class="categories four">
			<a href="${pageContext.request.contextPath}/decisions/Economic/">Economy</a>
			<a href="${pageContext.request.contextPath}/decisions/Domestic/">Domestic</a>
			<a href="${pageContext.request.contextPath}/decisions/Foreign/">Foreign</a>
			<a href="${pageContext.request.contextPath}/decisions/Military/">Military</a>
		</div>
		<h1><c:out value="${decisions}"/> Decisions</h1>
		<div class="specialTable">
			<div class="header">
				<div class="name">
					<p class="halfPad">Policy</p>
				</div>
				<div class="description">
					<p class="halfPad">Description</p>
				</div>
				<div class="cost">
					<p class="halfPad">Cost</p>
				</div>
				<div class="button"></div>
			</div>
			<c:if test="${empty decisions || decisions == 'Economic'}">
				<div class="policy">
					<div class="name">
						<p class="fullPad">Free Money</p>
					</div>
					<div class="description">
						<p class="fullPad">Acquire some free money, the Free Market way.</p>
					</div>
					<div class="cost">
						<p class="fullPad">Free!</p>
					</div>
					<div class="button">
						<button onclick="decision('freemoneycapitalist');">Get Rich</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Free Money</p>
					</div>
					<div class="description">
						<p class="fullPad">Acquire some free money, the Socialist way.</p>
					</div>
					<div class="cost">
						<p class="fullPad">Free!</p>
					</div>
					<div class="button">
						<button onclick="decision('freemoneycommunist');">Get Rich</button>
					</div>
				</div>
			</c:if>
			<c:if test="${empty decisions || decisions == 'Domestic'}">
				<div class="policy">
					<div class="name">
						<p class="fullPad">Increase Arrest Quotas</p>
					</div>
					<div class="description">
						<p class="fullPad">Tell your police force they need to arrest more criminals! Increases
						stability, but lowers approval and makes your government more authoritarian.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ARREST)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('crackdown');">Crackdown</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Pardon Petty Criminals</p>
					</div>
					<div class="description">
						<p class="fullPad">Release the jaywalkers from their cells! Decreases stability, but increases
						approval and makes your government less authoritarian.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_FREE)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('free');">Free</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Propaganda Campaign</p>
					</div>
					<div class="description">
						<p class="fullPad">Put up posters saying how great you are! Increases approval. Cost is based on
						current GDP and approval.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_PROPAGANDA)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('propaganda');">Propaganda</button>
					</div>
				</div>
				<c:if test="${home.atWar}">
					<div class="policy">
						<div class="name">
							<p class="fullPad">War Propaganda</p>
						</div>
						<div class="description">
							<p class="fullPad">Rallying war speeches will make anyone love you, right? Increases approval,
							but only available when at war. Cost is based on GDP and approval.</p>
						</div>
						<div class="cost">
							<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_WAR_PROPAGANDA)}"/></p>
						</div>
						<div class="button">
							<button onclick="decision('warpropaganda');">War Propaganda</button>
						</div>
					</div>
				</c:if>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Land Clearance</p>
					</div>
					<div class="description">
						<p class="fullPad">Slash and burn some useless jungle to make room for our expanding economy.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_LAND_CLEARANCE)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('landclearance');">Burn</button>
					</div>
				</div>
			</c:if>
			<c:if test="${empty decisions || decisions == 'Foreign'}">
				<div class="policy">
					<div class="name">
						<p class="fullPad">Align with the Entente</p>
					</div>
					<div class="description">
						<p class="fullPad">Praise France's Democracy, hoping to make them like you. Officially aligns us with
						the Entente</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('alignentente');">Praise</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Align With the Central Powers</p>
					</div>
					<div class="description">
						<p class="fullPad">Admire the German <i>Stahlhelm</i>, hoping to protect yourself from shrapnel.
						Officially aligns us with the Central Powers.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('aligncentral');">Admire</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Declare Neutrality</p>
					</div>
					<div class="description">
						<p class="fullPad">Go out on stage and celebrate your people's strength!</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_ALIGN)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('alignneutral');">Celebrate</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Form Treaty</p>
					</div>
					<div class="description">
						<p class="fullPad">Create an international alliance to keep yourself safe.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_CREATE_TREATY)}"/></p>
					</div>
					<div class="button">
						<button onclick="window.location.href = '${pageContext.request.contextPath}/createtreaty/'">
							Create
						</button>
					</div>
				</div>
			</c:if>
			<c:if test="${empty decisions || decisions == 'Military'}">
				<div class="policy">
					<div class="name">
						<p class="fullPad">Conscript</p>
					</div>
					<div class="description">
						<p class="fullPad">Throw more men into your army at the cost of manpower and overall training.</p>
					</div>
					<div class="cost">
						<p class="fullPad">Manpower and Training</p>
					</div>
					<div class="button">
						<button onclick="decision('conscript');">Draft</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Deconscript</p>
					</div>
					<div class="description">
						<p class="fullPad">Maybe that's a little too much army...</p>
					</div>
					<div class="cost">
						<p class="fullPad">Nothing</p>
					</div>
					<div class="button">
						<button onclick="decision('deconscript');">Fire</button>
					</div>
				</div>
				<div class="policy">
					<div class="name">
						<p class="fullPad">Train</p>
					</div>
					<div class="description">
						<p class="fullPad">Train your army.</p>
					</div>
					<div class="cost">
						<p class="fullPad">$<fmt:formatNumber value="${home.getPolicyCost(policy.ID_TRAIN)}"/></p>
					</div>
					<div class="button">
						<button onclick="decision('train');">Train</button>
					</div>
				</div>
			</c:if>
		</div>
	</c:otherwise>
</c:choose>
<%@ include file="includes/defaultBottom.jsp" %>