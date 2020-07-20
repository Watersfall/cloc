<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
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
					<c:if test="${resource.key == 'Budget'}">
						<p><cloc:formatNumber value="${resource.value}"/><img src="${pageContext.request.contextPath}/images/ui/${fn:toLowerCase(resource.key)}.svg" alt="resource"></p>
					</c:if>
					<c:if test="${resource.key != 'Budget'}">
						<p><cloc:formatNumber value="${resource.value}"/><img src="${pageContext.request.contextPath}/images/ui/${fn:toLowerCase(resource.key)}.svg" alt="resource"></p>
					</c:if>
					<div id="${resource.key}" class="detailsUp toggleable">
						<c:if test="${resource.key == 'Budget'}">
							<p class="textLeft">$<fmt:formatNumber value="${resource.value}"/>${' '}${resource.key}</p>
							<ul class="bulletList">
								<c:forEach items="${home.allTotalProductions.get(resource.key).entrySet()}" var="production">
									<c:if test="${!fn:contains(resource.key, 'total')}">
										<c:if test="${production.value > 0}">
											<li><p class="positive textLeft">+$<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${home.getDisplayString(production.key)}</p></li>
										</c:if>
										<c:if test="${production.value < 0}">
											<li><p class="negative textLeft">$<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${home.getDisplayString(production.key)}</p></li>
										</c:if>
									</c:if>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${resource.key != 'Budget'}">
							<p class="textLeft"><fmt:formatNumber value="${resource.value}" maxFractionDigits="2"/> <c:out value=" ${resource.key}"/></p>
							<c:set var="net" value="${home.allTotalProductions.get(resource.key).get('resource.net')}"/>
							<c:if test="${net > 0}">
								<p class="positive textLeft">+<fmt:formatNumber value="${net}" maxFractionDigits="2"/>${home.getDisplayString('resource.net')}</p>
							</c:if>
							<c:if test="${net < 0}">
								<p class="negative textLeft"><fmt:formatNumber value="${net}" maxFractionDigits="2"/>${home.getDisplayString('resource.net')}</p>
							</c:if>
							<ul class="bulletList">
								<c:forEach items="${home.allTotalProductions.get(resource.key).entrySet()}" var="production">
									<c:if test="${production.key != 'resource.net' && production.key != 'resource.total'}">
										<c:if test="${production.value > 0}">
											<li><p class="positive textLeft">+<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${home.getDisplayString(production.key)}</p></li>
										</c:if>
										<c:if test="${production.value < 0}">
											<li><p class="negative textLeft"><fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${home.getDisplayString(production.key)}</p></li>
										</c:if>
									</c:if>
								</c:forEach>
							</ul>
						</c:if>
					</div>
				</div>
			</a>
		</c:forEach>
	</div>
</c:if>