<%@ page import="com.watersfall.clocgame.model.TextKey" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<% pageContext.setAttribute("netKey", TextKey.Resource.NET); %>
<c:if test="${sessionScope.user != null}">
	<script>
		window.onload = function(){
			document.addEventListener("click", test);
		};

		function test(event)
		{
			if(!anyParentIsToggleTab(event.target))
			{
				toggleTab(null);
			}
		}
	</script>
	<div id="bottom">
		<c:forEach items="${home.allResources.entrySet()}" var="resource">
			<a onclick="toggleTab('${resource.key}');">
				<div>
					<p><cloc:formatNumber value="${resource.value}"/><img src="${pageContext.request.contextPath}/images/ui/${fn:toLowerCase(resource.key)}.svg" alt="resource"></p>
					<div id="${resource.key}" class="detailsUp toggleable">
						<p class="textLeft"><fmt:formatNumber value="${resource.value}" maxFractionDigits="2"/> <c:out value=" ${resource.key}"/></p>
						<c:set var="net" value="${home.allTotalProductions.get(resource.key).get(netKey)}"/>
						<c:if test="${net > 0}">
							<p class="positive textLeft">+<fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</p>
						</c:if>
						<c:if test="${net < 0}">
							<p class="negative textLeft"><fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</p>
						</c:if>
						<c:if test="${net == 0}">
							<p class="neutral textLeft">0${' '}${netKey.text}</p>
						</c:if>
						<ul class="bulletList">
							<c:forEach var="production" items="${home.allTotalProductions.get(resource.key).entrySet()}">
								<c:if test="${production.key != 'NET' && production.key != 'TOTAL_GAIN'}">
									<c:if test="${production.value > 0}">
										<li><p class="positive textLeft">+<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${production.key.text}</p></li>
									</c:if>
									<c:if test="${production.value < 0}">
										<li><p class="negative textLeft"><fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${production.key.text}</p></li>
									</c:if>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</a>
		</c:forEach>
	</div>
</c:if>