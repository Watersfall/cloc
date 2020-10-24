<%@ include file="includes/top.jsp" %>
<%@ page import="net.watersfall.clocgame.model.producible.ProducibleCategory" %>
	<script>
		window.onload = function() {
			document.addEventListener("click", test);
			setFreeFactories(${home.freeFactories});
		}
	</script>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title"><span id="free_factories">${home.freeFactories}</span> Free Factories</div>
				<div class="description left_text">
					<span class="left_text">Total Production:</span>
					<ul class="less_margin">
						<c:forEach items="${ProducibleCategory.values()}" var="category">
							<c:if test="${home.getProduciblesProductionByCategory(category) > 0}">
								<li class="dropdown_parent">
									<a href="#" onclick="toggleUITab('${category}')">
										<fmt:formatNumber value="${home.getProduciblesProductionByCategory(category)}"/> total ${category.name()} per month
										<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
									</a>
									<div id="${category}" class="dropdown_2 toggleable-default-off special-toggle">
										<ul>
											<c:forEach var="equipment" items="${home.produciblesProductionMapByCategories.get(category).entrySet()}">
												<li>
													+<fmt:formatNumber value="${equipment.value}"/> ${' '.concat(equipment.key.name())} per month
												</li>
											</c:forEach>
										</ul>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="tiling">
		<div class="column">
			<div class="production_list">
				<c:forEach var="production" items="${home.production.entrySet()}">
					<c:set value="${production.value}" var="production"/>
					<%@ include file="api/production.jsp"%>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<img onclick="createNewProduction();" src="${pageContext.request.contextPath}/images/production/new.svg" alt="new"/>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>