<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="production" type="java.util.Map.Entry<Integer, Production>"--%>
<div id="id${production.value.id}" style="height: 100%;" onmouseover="resetProduction(event, ${production.value.id}, ${production.value.factories})">
	<img class="left" style="width: auto; height: 6em;" src="${pageContext.request.contextPath}/images/production/gun.svg" alt="production icon"/>
	<div>
		<table style="min-width: 8em;">
			<c:forEach var="row" begin="1" end="3">
				<tr>
					<c:forEach var="column" begin="1" end="5">
						<td>
							<c:choose>
								<c:when test="${production.value.factories >= ((row - 1) * 5) + (column)}">
									<img onclick="clickProduction(event, ${production.value.id}, ${((row - 1) * 5) + (column)})" onmouseover="processProduction(this)" class="id=${production.value.id} number=${((row - 1) * 5) + (column)} default=${production.value.factories}" style="height: 2em; width: 2em;" src="${pageContext.request.contextPath}/images/production/factory.svg" alt="factory"/>
								</c:when>
								<c:otherwise>
									<img onclick="clickProduction(event, ${production.value.id}, ${((row - 1) * 5) + (column)})" onmouseover="processProduction(this)" class="id=${production.value.id} number=${((row - 1) * 5) + (column)} default=${production.value.factories}" style="height: 2em; width: 2em;" src="${pageContext.request.contextPath}/images/production/blank.svg" alt="nofactory"/>
								</c:otherwise>
							</c:choose>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<div>
			<div style="background: red;">
				<div style="background: green; width: ${production.value.progress / production.value.productionAsTechnology.technology.productionCost}%;"><p class="neutral">${production.value.productionString}</p></div>
			</div>
			<div style="background: red;">
				<div style="background: green; width: ${production.value.efficiency / 100}%"><p class="neutral">Efficiency:&nbsp;${production.value.efficiency / 100}%</p></div>
			</div>
		</div>
	</div>
	<select id="change${production.value.id}" class="toggleClass">
		<c:forEach items="${home.tech.researchedTechs}" var="tech">
			<c:if test="${tech.technology.producible}">
				<option value="${tech.name()}"} ${(tech.name() == production.value.production) ? 'selected' : ''}>${tech.technology.name}</option>
			</c:if>
		</c:forEach>
	</select>
	<button onclick="deleteProduction(${production.value.id});">Delete</button>
	<button onclick="changeProduction(${production.value.id});">Change</button>
	<button onclick="sendProduction(${production.value.id}, document.getElementsByClassName('id=${production.value.id}')[0].classList.item(2).substring(8))">Update</button>
</div>