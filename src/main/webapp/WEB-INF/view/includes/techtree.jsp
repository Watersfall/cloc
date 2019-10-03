<%--@elvariable id="type" type="java.lang.String"--%>
<%--@elvariable id="category" type="java.lang.String"--%>
<%--@elvariable id="techs" type="java.util.List"--%>
<%--@elvariable id="categories" type="java.util.List"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
<%--@elvariable id="cat" type="com.watersfall.clocgame.model.technology.technologies.Category"--%>
<%@include file="default.jsp"%>
<c:forEach items="${techs}" var="tech">
	<c:if test="${tech.category == category}">
		<div style="grid-column: ${tech.x};grid-row: ${tech.y};">
			<div style="position:relative;">
				<div id="${tech.name()}" onclick="tech('${tech.name()}', [])" class="tech ${home.tech.researchedTechs.contains(tech)? 'researched': tech.technology.isAvailable(home)? 'available' : 'unavailable'}">
					<p>${tech.technology.name}</p>
				</div>
				<c:if test="${tech.cssClass.contains(',')}" var="multiple">
					<c:set var="array" value="${fn:split(tech.cssClass, ',')}"/>
					<c:forEach var="divClass" items="${array}">
						<div class="${divClass}" style="${tech.technology.isAvailable(home) ? 'border-color: gold' : home.tech.researchedTechs.contains(tech) ? 'border-color: green' : 'border-color: gray'}"></div>
					</c:forEach>
				</c:if>
				<c:if test="${multiple == false}">
					<div class="${tech.cssClass}" style="${tech.technology.isAvailable(home) ? 'border-color: gold' : home.tech.researchedTechs.contains(tech) ? 'border-color: green' : 'border-color: gray'}"></div>
				</c:if>
			</div>
		</div>
	</c:if>
</c:forEach>