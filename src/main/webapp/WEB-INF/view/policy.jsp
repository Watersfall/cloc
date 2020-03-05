<%--@elvariable id="policies" type="java.util.LinkedHashMap"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<h1>State Policy</h1>
	<div class="specialTable">
		<div class="header">
			<div class="name"><p class="halfPad">Decision</p></div>
			<div class="cost"><p class="halfPad">Options</p></div>
			<div class="description"><p class="halfPad">Description</p></div>
			<div class="button"><p class="halfPad">Apply</p></div>
		</div>
		<c:forEach var="policy" items="${policies.entrySet()}">
			<div class="policy">
				<div class="name"><p class="halfPad">${policy.key}</p></div>
				<div class="cost">
					<label for="policy_${policy.key}"></label>
					<select class="halfPad" id="policy_${policy.key}" onchange="updateDesc('policy_${policy.key}')"}>
						<c:forEach items="${policy.value}" var="i">
							<option id="policy_${policy.key}_${i.name()}" ${(home.policy.getPolicy(i) == i) ? 'selected' : ''} value="${i.name()}">${i.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="description">
					<c:forEach items="${policy.value}" var="i">
						<p class="desc halfPad" id="policy_${policy.key}_${i.name()}_desc" style="display:${(home.policy.getPolicy(i) == i) ? 'block' : 'none'};">
							<c:forEach items="${i.getMap(i)}" var="map">
								<c:if test="${not fn:contains(map.key, 'consumption')}">
									<c:if test="${map.value > 0}">
										<span class="positive">+${map.value}${map.key}</span>
									</c:if>
									<c:if test="${map.value < 0}">
										<span class="negative">${map.value}${map.key}</span>
									</c:if>
								</c:if>
								<c:if test="${fn:contains(map.key, 'consumption')}">
									<c:if test="${map.value > 0}">
										<span class="negative">+${map.value}${map.key}</span>
									</c:if>
									<c:if test="${map.value < 0}">
										<span class="positive">${map.value}${map.key}</span>
									</c:if>
								</c:if>
								<br>
							</c:forEach>
						</p>
					</c:forEach>
				</div>
				<div class="button">
					<button onclick="policy('${policy.key}')">Update</button>
				</div>
			</div>
		</c:forEach>
	</div>
<%@ include file="includes/defaultBottom.jsp" %>