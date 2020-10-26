<%@ page import="net.watersfall.clocgame.model.TextKey" %>
<%--suppress ELMethodSignatureInspection (it gets casted ok >:() --%>
<% pageContext.setAttribute("netKey", TextKey.Resource.NET); %>
			</main>
			<footer>
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate var="serverTime" value="${now}" pattern="hh:mm" />
				<div id="footer">
					<h3>Today's Date: ${time.date}</h3>
					<h4>Server Time: ${serverTime}</h4>
				</div>
			</footer>
			<c:if test="${home != null}">
				<div id="resources">
					<ul>
						<c:forEach items="${home.allResources.entrySet()}" var="entry">
							<li>
								<c:set var="resource" value="${fn:toUpperCase(entry.key)}"/>
								<a href="javascript:void(0);" onclick="toggleUITab('${resource}_CHANGE')">
									<img src="${pageContext.request.contextPath}/images/ui/${resource.toLowerCase()}.svg" alt="${resource}"/>
									<span id="${resource}_SHORT"><cloc:formatNumber value="${entry.value}"/></span>
								</a>
								<div class="dropdown toggleable-default-off special-toggle" id="${resource}_CHANGE">
									<span id="${resource}"><fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/> <c:out escapeXml="false" value=" ${entry.key}"/></span><br>
									<c:set var="net" value="${home.allTotalProductions.get(entry.key).get(netKey)}"/>
									<c:if test="${net > 0}">
										<span id="${resource}_CHANGE_${netKey.name()}" class="positive">+<fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</span>
									</c:if>
									<c:if test="${net < 0}">
										<span id="${resource}_CHANGE_${netKey.name()}" class="negative"><fmt:formatNumber value="${net}" maxFractionDigits="2"/>${' '}${netKey.text}</span>
									</c:if>
									<c:if test="${net == 0}">
										<span id="${resource}_CHANGE_${netKey.name()}">0${' '}${netKey.text}</span>
									</c:if>
									<ul>
										<c:forEach var="production" items="${home.allTotalProductions.get(entry.key).entrySet()}">
											<c:if test="${production.key != 'NET' && production.key != 'TOTAL_GAIN'}">
												<c:if test="${production.value > 0}">
													<li>
														<span id="${resource}_CHANGE_${production.key.name()}" class="positive">
															+<fmt:formatNumber value="${production.value}" maxFractionDigits="2"/>${production.key.text}
														</span>
													</li>
												</c:if>
												<c:if test="${production.value < 0}">
													<li>
														<span id="${resource}_CHANGE_${production.key.name()}" class="negative">
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