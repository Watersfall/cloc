<%@ include file="taglibs.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="production" type="java.util.Map.Entry<Integer, Production>"--%>
<div id="production_id_${production.value.id}" style="max-width: 22em;" class="original=${production.value.factories.size()}">
	<p class="neutral name">${production.value.productionAsTechnology.technology.name}</p>
	<div style="width: 100%;">
		<div style="min-width: 8em; width: 50%; float: left;">
			<span style="display: inline-block; vertical-align: middle;"></span>
			<img style="width: 100%; height: auto; vertical-align: middle; margin-top: 1.25em;" src="${pageContext.request.contextPath}/images/production/gun.svg" alt="production icon"/>
		</div>
		<div style="min-width: 8em; width: 50%; float: left">
			<table style="min-width: 8em; width: 100%;">
				<c:forEach var="row" begin="1" end="3">
					<tr>
						<c:forEach var="column" begin="1" end="5">
							<td>
								<c:choose>
									<c:when test="${production.value.factories.size() >= ((row - 1) * 5) + (column)}">
										<img
											src="${pageContext.request.contextPath}/images/production/factory.svg"
											alt="factory"
											class="smallSquare production_table_id=${production.value.id}"
											onclick="clickProduction(${production.value.id}, ${((row - 1) * 5) + (column)})"
										>
									</c:when>
									<c:otherwise>
										<img
											src="${pageContext.request.contextPath}/images/production/blank.svg"
											alt="blank"
											class="smallSquare production_table_id=${production.value.id}"
											onclick="clickProduction(${production.value.id}, ${((row - 1) * 5) + (column)})"
										>
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div style="width: 100%; float: left;">
		<div style="background: red;">
			<div style="background: green; width: ${production.value.progress / production.value.productionAsTechnology.technology.productionCost}%;"><p class="neutral">${production.value.productionString}</p></div>
		</div>
		<div style="background: red;">
			<div style="background: green; width: ${production.value.efficiency / 100}%"><p class="neutral">Efficiency:&nbsp;${production.value.efficiency / 100}%</p></div>
		</div>
	</div>
	<select id="change_${production.value.id}">
		<c:forEach items="${home.tech.researchedTechs}" var="tech">
			<c:if test="${tech.technology.producible}">
				<option value="${tech.name()}" ${(tech.name() == production.value.production) ? 'selected' : ''}>${tech.technology.name}</option>
			</c:if>
		</c:forEach>
	</select>
	<br>
	<button onclick="deleteProduction(${production.value.id});">Delete</button>
	<button onclick="updateProduction(${production.value.id});">Update</button>
</div>