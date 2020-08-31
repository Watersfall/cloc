<%--@elvariable id="technology" type="com.watersfall.clocgame.model.technology.Technology"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cloc" tagdir="/WEB-INF/tags" %>
<div class="column">
	<div class="tile">
		<div class="subtile">
			<div class="title">${technology.name}</div>
			<div class="description">
				<c:out value="${technology.desc}"/>
				Effects:
				<ul>
					<c:forEach var="effect" items="${technology.effects}">
						<li><c:out value="${effect}"/></li>
					</c:forEach>
				</ul>
				Prerequisites:
				<ul>
					<c:forEach var="pre" items="${technology.prerequisites}">
						<li><c:out value="${pre.technology.name}"/></li>
					</c:forEach>
				</ul>
				Costs:
				<ul>
					<c:forEach var="cost" items="${technology.costs}">
						<li>${cost.value}${' '.concat(cost.key)}</li>
					</c:forEach>
				</ul>
				<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
				<c:if test="${technology.isAvailable(home)}">
					<button onclick="research('${technology.technology.name()}', '${technology.technology.category}');" class="blue">Research</button>
				</c:if>
				<button onclick="document.getElementById('tech').style.display = 'none';" class="red">Close</button>
			</div>
		</div>
	</div>
</div>