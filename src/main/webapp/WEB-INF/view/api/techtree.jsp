<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cloc" tagdir="/WEB-INF/tags" %>
<div class="tech_tree" id="tech_tree">
	<%--@elvariable id="techs" type="java.util.List"--%>
	<%--@elvariable id="tech" type="com.watersfall.clocgame.model.technology.Technologies"--%>
	<c:forEach var="tech" items="${techs}">
		<c:if test="${tech.category == category}">
			<div onclick="loadTech('${tech.name()}');" class="tech_tile" style="grid-column: ${tech.x}; grid-row: ${tech.y}">
				<div class="tech_wrapper">
					<c:choose>
						<c:when test="${home.hasTech(tech.technology)}">
							<div class="green tech">
									${tech.technology.name}
							</div>
						</c:when>
						<c:when test="${tech.technology.isAvailable(home)}">
							<div class="tech gold">
									${tech.technology.name}
							</div>
						</c:when>
						<c:otherwise>
							<div class="tech lightest">
									${tech.technology.name}
							</div>
						</c:otherwise>
					</c:choose>
					<c:forEach items="${tech.cssClass}" var="cssClass">
						<c:choose>
							<c:when test="${home.hasTech(tech.technology)}">
								<div class="green ${cssClass}"></div>
							</c:when>
							<c:when test="${tech.technology.isAvailable(home)}">
								<div class="gold ${cssClass}"></div>
							</c:when>
							<c:otherwise>
								<div class="gray ${cssClass}"></div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>