<%@ include file="includes/top.jsp" %>
	<%--@elvariable id="policies" type="java.util.LinkedHashMap"--%>
	<div class="tiling">
		<div class="column">
			<div class="title">Policy</div>
			<div class="tile">
				<c:forEach var="category" items="${policies}">
					<div class="subtitle">
						<label for="${category.key.name()}">${category.key.name}</label>
						<select id="${category.key.name()}">
							<c:forEach var="policy" items="${category.value}">
								<option ${home.policy.getPolicy(policy) == policy ? 'selected' : ''}>
										${policy.name}
								</option>
							</c:forEach>
						</select>
						<button class="right blue">Apply</button>
					</div>
					<div class="left_text">
						<c:forEach var="policy" items="${category.value}">
							<c:choose>
								<c:when test="${policy.map.entrySet().isEmpty()}">
									<div id="${policy.name}" class="${home.policy.getPolicy(policy) == policy ? '' : 'toggleable-default-off'}">
										No effects
									</div>
								</c:when>
							</c:choose>
							<c:forEach var="effect" items="${policy.map.entrySet()}">
								<div id="${policy.name}" class="${home.policy.getPolicy(policy) == policy ? '' : 'toggleable-default-off'}">
									${effect.value}${effect.key}
								</div>
							</c:forEach>
						</c:forEach>
					</div>
					<br>
				</c:forEach>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>