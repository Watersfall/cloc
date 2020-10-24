<%@ include file="includes/top.jsp" %>
	<%--@elvariable id="policies" type="java.util.LinkedHashMap"--%>
<%--@elvariable id="policy" type="net.watersfall.clocgame.model.policies.Policy"--%>
	<div class="tiling">
		<div class="column">
			<div class="title">Policy</div>
			<div class="tile">
				<c:forEach var="category" items="${policies}">
					<div class="subtile">
						<div class="title decision">
							<div class="left_text">
								<label for="${category.key.name()}">${category.key.name}</label>
								<select onchange="policyDesc('${category.key.name()}')" id="${category.key.name()}">
									<c:forEach var="policy" items="${category.value}">
										<option value="${policy.name()}" ${home.policy.getPolicy(policy) == policy ? 'selected' : ''}>
												${policy.name}
										</option>
									</c:forEach>
								</select>
							</div>
							<div class="button_right">
								<button onclick="policy('${category.key.name()}')" class="blue">Apply</button>
							</div>
						</div>
						<div class="left_text">
							<c:forEach var="policy" items="${category.value}">
								<c:if test="${policy.map.size() <= 0}">
									<div id="${policy.name()}_DESC">
										No Effects
									</div>
								</c:if>
								<c:if test="${policy.map.size() > 0}">
									<div id="${policy.name()}_DESC" class="${home.policy.getPolicy(policy) == policy ? '' : 'toggleable-default-off'}">
										<c:forEach var="effect" items="${policy.map}">
											<c:if test="${effect.value >= 0}">
												<span class="positive">+${effect.value}${effect.key}</span><br>
											</c:if>
											<c:if test="${effect.value < 0}">
												<span class="negative">${effect.value}${effect.key}</span><br>
											</c:if>
										</c:forEach>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>