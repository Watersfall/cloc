<%--@elvariable id="estimatedPlan" type="net.watersfall.clocgame.model.military.army.EstimatedBattlePlan"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cloc" tagdir="/WEB-INF/tags" %>
<ul>
	<c:forEach var="plan" items="${estimatedPlan.armies.entrySet()}">
		<li>
			${plan.key.name} - <fmt:formatNumber value="${plan.value * 100}" maxFractionDigits="0"/>% chance of joining battle
		</li>
	</c:forEach>
</ul>
