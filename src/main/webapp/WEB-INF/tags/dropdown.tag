<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="map" required="false" type="java.util.LinkedHashMap"%>
<%@ attribute name="value" required="true" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="nation" required="false" type="com.watersfall.clocgame.model.nation.Nation" %>
<div class="toggleable detailsDown" id="${name}">
	<c:if test="${name == 'Approval' || name == 'Stability'}">
		<p>${value}% ${name}</p>
	</c:if>
	<c:if test="${name == 'Manpower'}">
		<p class="positive"><fmt:formatNumber value="${value}"/> Total Manpower</p>
	</c:if>
	<c:if test="${name == 'Land'}">
		<p><fmt:formatNumber value="${nation.totalLandUsage}"/>km<sup>2</sup> Land Used</p>
	</c:if>
	<c:if test="${name == 'Equipment'}">
		<p><fmt:formatNumber value="${nation.totalInfantryEquipment}"/> Total Equipment</p>
	</c:if>
	<c:if test="${map != null}">
		<c:set var="net" value="${fn:toLowerCase(name)}${'.net'}"/>
		<c:if test="${map.get(net) > 0 && name != 'Manpower'}">
			<p class="positive"><fmt:formatNumber value="${map.get(net)}" maxFractionDigits="2"/>${nation.getDisplayString(net)}</p>
		</c:if>
		<c:if test="${map.get(net) < 0 || name == 'Manpower'}">
			<p class="negative"><fmt:formatNumber value="${map.get(net)}" maxFractionDigits="2"/>${nation.getDisplayString(net)}</p>
		</c:if>
		<c:if test="${map.get(net) == 0}">
			<p class="neutral">No net change</p>
		</c:if>
		<c:if test="${name != 'Land' && name != 'Manpower'}">
			<ul>
				<c:forEach items="${map.entrySet()}" var="entry">
					<c:if test="${!fn:contains(entry, 'net')}">
						<c:if test="${entry.value > 0 && name != 'Equipment'}">
							<li>
								<p class="positive">+<fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/><c:out value="${nation.getDisplayString(entry.key)}"/></p>
							</li>
						</c:if>
						<c:if test="${name == 'Equipment' && entry.value > 0}">
							<li>
								<p><fmt:formatNumber value="${entry.value}"/> ${entry.key}</p>
							</li>
						</c:if>
						<c:if test="${entry.value < 0}">
							<li>
								<p class="negative"><fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/><c:out value="${nation.getDisplayString(entry.key)}"/></p>
							</li>
						</c:if>
					</c:if>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${name == 'Land'}">
			<br>
			<c:forEach items="${map.entrySet()}" var="entry">
				<p>${entry.key}</p>
				<ul class="bulletList">
					<c:forEach items="${entry.value.entrySet()}" var="city">
						<li>
							<p><fmt:formatNumber value="${city.value}"/>km<sup>2</sup>${nation.getDisplayString(city.key)}</p>
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
		</c:if>
		<c:if test="${name == 'Manpower'}">
			<ul class="bulletList">
				<c:forEach items="${map.entrySet()}" var="entry">
					<c:if test="${entry.value < 0 && entry.key != net}">
						<li>
							<p class="negative"><fmt:formatNumber value="${-1 * entry.value}"/>${nation.getDisplayString(entry.key)}</p>
						</li>
					</c:if>
				</c:forEach>
			</ul>

		</c:if>
	</c:if>
</div>