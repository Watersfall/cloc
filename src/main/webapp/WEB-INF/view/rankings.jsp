<%@ include file="includes/top.jsp" %>
	<div class="title">World Rankings</div>
	<div class="tiling">
		<div class="column">
			<div class="tile left_text">
				<%--@elvariable id="nations" type="java.util.List"--%>
				<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
				<c:forEach items="${nations}" var="nation">
					<div class="rankings">
						<div class="subtitle">
							<img src="/user/flag/${nation.cosmetic.flag}" alt="flag"/>
								${nation.nationUrl}
						</div>
						<table>
							<tr>
								<td>Leader</td>
								<td>GDP</td>
								<td>Region</td>
								<td>Treaty</td>
							</tr>
							<tr>
								<td><c:out escapeXml="false" value="${nation.cosmetic.username}"/></td>
								<td>$<fmt:formatNumber value="${nation.economy.gdp}"/></td>
								<td>${nation.foreign.region.name}</td>
								<td>
									<c:if test="${nation.treaty == null}">
										None
									</c:if>
									<c:if test="${nation.treaty != null}">
										${nation.treaty.treatyUrl}
									</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>