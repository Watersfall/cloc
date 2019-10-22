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
				<div class="${tech.cssClass}" style="${tech.technology.isAvailable(home) ? 'border-color: rgb(250,189,41);' : home.tech.researchedTechs.contains(tech) ? 'border-color: rgb(95,183,97)' : 'border-color: #424242'}"></div>
			</div>
		</div>
	</c:if>
</c:forEach>