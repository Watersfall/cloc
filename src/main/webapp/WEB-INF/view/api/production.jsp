<%--@elvariable id="production" type="com.watersfall.clocgame.model.nation.Production"--%>
<div class="tile production" id="production_${production.id}">
	<div class="title">
		<span id="name_${production.id}" class="toggleable-default-off" style="display: block;"> ${production.production}</span>
		<select id="select_${production.id}" class="toggleable-default-off">
			<c:forEach items="${home.tech.researchedTechs}" var="tech">
				<c:if test="${tech.technology.producible}">
					<option>${tech.technology.productionName}</option>
				</c:if>
			</c:forEach>
		</select>
	</div>
	<div class="contents">
		<div class="factories">
			<c:forEach var="factory" begin="1" end="15" varStatus="status">
				<div class="factory">
					<c:if test="${status.index <= production.factories.size()}">
						<img src="${pageContext.request.contextPath}/images/production/factory.svg" alt="factory">
					</c:if>
					<c:if test="${status.index > production.factories.size()}">
						<img src="${pageContext.request.contextPath}/images/production/blank.svg" alt="factory">
					</c:if>
				</div>
			</c:forEach>
		</div>
		<div class="resource">
			<c:forEach var="resource" items="${production.requiredResources.entrySet()}">
				<div>
					<span class="${production.givenResources.get(resource.key) >= resource.value ? '' : 'red_text'}">
						<img src="/images/ui/${resource.key}.svg" alt="${resource.key}">
						${resource.value}
					</span>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="progress_bar">
		<span>Efficiency:</span>
		<div>
			<div style="width: ${production.efficiency / 100}%;">
				<span><fmt:formatNumber value="${production.efficiency / 100}" maxFractionDigits="1"/>%</span>
			</div>
		</div>
	</div>
	<div class="progress_bar">
		<span>Progress:</span>
		<div>
			<div style="width: ${production.progress / production.productionAsTechnology.technology.productionICCost}%;">
				<span>${production.getProductionString(home.policy.economy)}</span>
			</div>
		</div>
	</div>
	<div>
		<button class="gold" onclick="toggle('select_${production.id}'); toggle('name_${production.id}');">Change Production</button>
		<button class="blue">Update</button>
		<button class="red">Delete</button>
	</div>
</div>