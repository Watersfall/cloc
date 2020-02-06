<%@ include file="includes/defaultTop.jsp" %>
	<h1 title="Title" id="top">The World's Finest</h1>
	<hr>
	<table class="standardTable">
		<tr>
			<th class="hideOnMobile" style="width: 8em;"><p>Nation</p></th>
			<th></th>
			<th><p>Leader</p></th>
			<th><p>Region</p></th>
			<th><p>GDP</p></th>
			<th><p>Treaty</p></th>
		</tr>
		<c:forEach items="${nations}" var="nation">
			<tr>
				<td class="hideOnMobile">
					<a href="${pageContext.request.contextPath}/nation/${nation.id}">
						<img class="large" src="${pageContext.request.contextPath}/user/flag/<c:out value="${nation.cosmetic.flag}"/>" alt="flag">
					</a>
				</td>
				<td><p class="textLeft">${nation.nationUrl}</p></td>
				<td><p><c:out value="${nation.cosmetic.username}"/></p></td>
				<td><p><c:out value="${nation.foreign.region.name}"/></p></td>
				<td><p>$<fmt:formatNumber value="${nation.economy.gdp}"/> Million</p></td>
				<c:if test="${nation.treaty != null}">
					<td>${nation.treaty.treatyUrl}</td>
				</c:if>
				<c:if test="${nation.treaty == null}">
					<td><p>None</p></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<%@include file="includes/pagination.jsp"%>
<%@ include file="includes/defaultBottom.jsp" %>