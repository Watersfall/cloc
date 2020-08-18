<%@ page import="com.watersfall.clocgame.model.TextKey" %>
<%--suppress ELMethodSignatureInspection (it gets casted ok >:() --%>
<% pageContext.setAttribute("netKey", TextKey.Resource.NET); %>
			</main>
			<c:if test="${home != null}">
				<div id="resources">
					<ul>
						<c:forEach items="${home.allResources.entrySet()}" var="entry">
							<li>
								<c:set var="resource" value="${fn:toLowerCase(entry.key)}"/>
								<a href="#" id="${entry.key}" onclick="toggleUITab('${resource}_production')">
									<img src="${pageContext.request.contextPath}/images/ui/${resource}.svg" alt="${resource}"/>
									<cloc:formatNumber value="${entry.value}"/>
								</a>
								<div class="dropdown toggleable-default-off" id="${resource}_production">
									<span><fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/> <c:out value=" ${entry.key}"/></span><br>
									<c:set var="net" value="${home.allTotalProductions.get(entry.key).get(netKey)}"/>
									<c:if test="${net > 0}">
										<span class="positive">+<fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</span>
									</c:if>
									<c:if test="${net < 0}">
										<span class="negative"><fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</span>
									</c:if>
									<c:if test="${net == 0}">
										<span>0${' '}${netKey.text}</span>
									</c:if>
									<ul>
										<c:forEach var="production" items="${home.allTotalProductions.get(entry.key).entrySet()}">
											<c:if test="${production.key != 'NET' && production.key != 'TOTAL_GAIN'}">
												<c:if test="${production.value > 0}">
													<li>
													<span class="positive">
														+<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${production.key.text}
													</span>
													</li>
												</c:if>
												<c:if test="${production.value < 0}">
													<li>
													<span class="negative">
														<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${production.key.text}
													</span>
													</li>
												</c:if>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
	</body>
</html>