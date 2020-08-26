<%@ include file="includes/top.jsp" %>
	<div class="title">World News</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Wars</div>
				<div class="pagination">
					<a href="${pageContext.request.contextPath}/worldnews/ongoing">Ongoing Wars</a>
					<a href="${pageContext.request.contextPath}/worldnews/ended">Ended Wars</a>
				</div>
				<br>
				<%--@elvariable id="wars" type="java.util.List"--%>
				<%--@elvariable id="war" type="com.watersfall.clocgame.model.war.War"--%>
				<c:forEach var="war" items="${wars}">
					<div class="subtitle"><c:out value="${war.name}"/></div>
					<div class="wars">
						<div>
							<span>Attacker</span><br>
							<img src="/user/flag/${war.attacker.cosmetic.flag}" alt="flag" class="small_flag"/> ${war.attacker.nationUrl}
						</div>
						<div>
							<span>Treaty</span><br>
							${war.attacker.treaty == null ? 'None' : war.attacker.treaty.treatyUrl}
						</div>
						<div>
							<span>Defender</span><br>
							<img src="/user/flag/${war.defender.cosmetic.flag}" alt="flag" class="small_flag"/> ${war.defender.nationUrl}
						</div>
						<div>
							<span>Treaty</span><br>
							${war.defender.treaty == null ? 'None' : war.defender.treaty.treatyUrl}
						</div>
					</div>
				</c:forEach>
				<br>
				<%@ include file="includes/pagination.jsp"%>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>