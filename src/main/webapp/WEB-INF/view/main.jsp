<% pageContext.setAttribute("netApproval", TextKey.Approval.NET); %>
<% pageContext.setAttribute("netStability", TextKey.Stability.NET); %>
<% pageContext.setAttribute("netManpower", TextKey.Manpower.NET); %>
<% pageContext.setAttribute("netGrowth", TextKey.Growth.NET); %>
<% pageContext.setAttribute("netFortification", TextKey.Fortification.NET); %>
<%@ page import="com.watersfall.clocgame.model.producible.ProducibleCategory" %>
<%@ page import="com.watersfall.clocgame.model.producible.Producibles" %>
<%@ page import="com.watersfall.clocgame.util.Util" %>
<%@ include file="includes/top.jsp" %>
	<c:if test="${home == null}">
		<p>You have visited this page incorrectly</p>
	</c:if>
	<c:if test="${home != null}">
		<div id="nation" class="tiling">
			<div id="column_1" class="column">
				<div id="stats" class="tile">
					<div class="title">Stats</div>
					<table class="nation">
						<thead class="blue">
						<tr>
							<td colspan="2">Domestic</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Government</td>
						</tr>
						<tr>
							<td colspan="2"><cloc:government value="${home.stats.government}"/></td>
						</tr>
						<tr>
							<td colspan="2">Approval</td>
						</tr>
						<tr>
							<td>${home.stats.approval}%</td>
							<td class="dropdown_parent" onclick="toggleUITab('approval_change')">
								<a href="#">
									<c:if test="${home.approvalChange.get(netApproval) > 0}">
										+<fmt:formatNumber value="${home.approvalChange.get(netApproval)}"/> per month
									</c:if>
									<c:if test="${home.approvalChange.get(netApproval) < 0}">
										<fmt:formatNumber value="${home.approvalChange.get(netApproval)}"/> per month
									</c:if>
									<c:if test="${home.approvalChange.get(netApproval) == 0}">
										No change
									</c:if>
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="approval_change" class="dropdown_2_right toggleable-default-off">
									<cloc:dropdown value="${Util.removeNetAndTotal(home.approvalChange)}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Stability</td>
						</tr>
						<tr>
							<td>${home.stats.stability}%</td>
							<td class="dropdown_parent" onclick="toggleUITab('stability_change')">
								<a href="#" >
									<c:if test="${home.stabilityChange.get(netStability) > 0}">
										+<fmt:formatNumber value="${home.stabilityChange.get(netStability)}"/> per month
									</c:if>
									<c:if test="${home.stabilityChange.get(netStability) < 0}">
										<fmt:formatNumber value="${home.stabilityChange.get(netStability)}"/> per month
									</c:if>
										<c:if test="${home.stabilityChange.get(netStability) == 0}">
										No change
									</c:if>
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="stability_change" class="dropdown_2_right toggleable-default-off">
									<cloc:dropdown value="${Util.removeNetAndTotal(home.stabilityChange)}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Land</td>

						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.freeLand}"/>km<sup>2</sup> Free</td>
							<td><fmt:formatNumber value="${home.stats.land}"/>km<sup>2</sup> Total</td>
						</tr>
						<tr>
							<td colspan="2">Population</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.totalPopulation}"/> People</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="gold">
						<tr>
							<td colspan="2">Economy</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Gross Domestic Product</td>
						</tr>
						<tr>
							<td colspan="2">$<fmt:formatNumber value="${home.stats.gdp}"/> Million</td>
						</tr>
						<tr>
							<td colspan="2">Growth</td>
						</tr>
						<tr>
							<td><fmt:formatNumber value="${home.stats.growth}"/> Million per month</td>
							<td class="dropdown_parent" onclick="toggleUITab('growth_change')">
								<a href="#">
									<c:choose>
										<c:when test="${home.growthChange.get(netGrowth) > 0}">
											+<fmt:formatNumber value="${home.growthChange.get(netGrowth)}"/> per month
										</c:when>
										<c:when test="${home.growthChange.get(netGrowth) < 0}">
											<fmt:formatNumber value="${home.growthChange.get(netGrowth)}"/> per month
										</c:when>
										<c:otherwise>
											No change
										</c:otherwise>
									</c:choose>
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div id="growth_change" class="dropdown_2_right toggleable-default-off">
									<cloc:dropdown value="${Util.removeNetAndTotal(home.growthChange)}"/>
								</div>
							</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="green">
						<tr>
							<td colspan="2">Foreign</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Region</td>
						</tr>
						<tr>
							<td colspan="2">${home.stats.region.name}</td>
						</tr>
						<tr>
							<td colspan="2">Official Alignment</td>
						</tr>
						<tr>
							<td colspan="2">${home.stats.alignment}</td>
						</tr>
						<tr>
							<td colspan="2">Treaty Membership</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:if test="${home.treaty == null}">
									None
								</c:if>
								<c:if test="${home.treaty != null}">
									${home.treaty.treatyUrl}
								</c:if>
							</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="red">
						<tr>
							<td colspan="2">Army</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Active Personnel</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.stats.armySize}"/>k Soldiers</td>
						</tr>
						<tr>
							<td colspan="2">Training</td>
						</tr>
						<tr>
							<td colspan="2"><fmt:formatNumber value="${home.stats.armyTraining}"/>%</td>
						</tr>
						<tr>
							<td>Equipment</td>
						</tr>
						<tr>
							<td class="dropdown_parent" onclick="toggleUITab('equipment')">
								<a href="#">
									<fmt:formatNumber value="${home.getTotalProduciblesByCategory(ProducibleCategory.INFANTRY_EQUIPMENT)}"/> / <fmt:formatNumber value="${home.stats.armySize * 1000}"/> requested
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_left toggleable-default-off" id="equipment">
									<ul>
										<c:forEach items="${Producibles.getProduciblesForCategory(ProducibleCategory.INFANTRY_EQUIPMENT)}" var="producible">
											<c:if test="${home.producibles.getProducible(producible) > 0}">
												<li>
													<fmt:formatNumber value="${home.producibles.getProducible(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="dropdown_parent" onclick="toggleUITab('equipment_production')">
								<a href="#">
									+<fmt:formatNumber value="${home.getProduciblesProductionByCategory(ProducibleCategory.INFANTRY_EQUIPMENT)}"/> per month
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_right toggleable-default-off" id="equipment_production">
									<ul>
										<c:forEach var="producible" items="${Producibles.getProduciblesForCategory(ProducibleCategory.INFANTRY_EQUIPMENT)}">
											<c:if test="${home.getProduciblesProduction(producible) > 0}">
												<li>
													+<fmt:formatNumber value="${home.getProduciblesProduction(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Fortification</td>
						</tr>
						<tr>
							<td>${home.stats.fortification / 100}%</td>
							<td class="dropdown_parent" onclick="toggleUITab('fortification_change')">
								<a href="#">
									<c:choose>
										<c:when test="${home.fortificationChange.get(netFortification) > 0}">
											+<fmt:formatNumber maxFractionDigits="2" value="${home.fortificationChange.get(netFortification) / 100}"/>% per month
										</c:when>
										<c:when test="${home.fortificationChange.get(netFortification) < 0}">
											<fmt:formatNumber maxFractionDigits="2" value="${home.fortificationChange.get(netFortification) / 100}"/>% per month
										</c:when>
										<c:otherwise>
											No change
										</c:otherwise>
									</c:choose>
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_right toggleable-default-off" id="fortification_change">
									<c:set var="fortMap" value="${Util.removeNetAndTotal(home.fortificationChange)}"/>
									<c:if test="${fortMap.size() <= 0}">
										<p>No change</p>
									</c:if>
									<c:if test="${fortMap.size() > 0}">
										<ul>
											<c:forEach var="entry" items="${fortMap.entrySet()}">
												<c:choose>
													<c:when test="${entry.value > 0}">
														<li><span class="positive">+<fmt:formatNumber value="${entry.value / 100}" maxFractionDigits="2"/>${entry.key.text}</span></li>
													</c:when>
													<c:when test="${entry.value < 0}">
														<li><span class="negative"><fmt:formatNumber value="${entry.value / 100}" maxFractionDigits="2"/>${entry.key.text}</span></li>
													</c:when>
												</c:choose>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</td>
						</tr>
					</table>
					<br>
					<table class="nation">
						<thead class="red">
						<tr>
							<td colspan="2">Airforce</td>
						</tr>
						</thead>
						<tr>
							<td colspan="2">Fighters</td>
						</tr>
						<tr>
							<td onclick="toggleUITab('fighters')" class="dropdown_parent">
								<a href="#">
									<fmt:formatNumber value="${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)}"/> Planes
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_left toggleable-default-off" id="fighters">
									<ul>
										<c:forEach items="${Producibles.getProduciblesForCategory(ProducibleCategory.FIGHTER_PLANE)}" var="producible">
											<c:if test="${home.producibles.getProducible(producible) > 0}">
												<li>
													<fmt:formatNumber value="${home.producibles.getProducible(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td onclick="toggleUITab('fighter_production')" class="dropdown_parent">
								<a href="#">
									+<fmt:formatNumber value="${home.getProduciblesProductionByCategory(ProducibleCategory.FIGHTER_PLANE)}"/> per month
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_right toggleable-default-off" id="fighter_production">
									<ul>
										<c:forEach var="producible" items="${Producibles.getProduciblesForCategory(ProducibleCategory.FIGHTER_PLANE)}">
											<c:if test="${home.getProduciblesProduction(producible) > 0}">
												<li>
													+<fmt:formatNumber value="${home.getProduciblesProduction(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Bombers</td>
						</tr>
						<tr>
							<td onclick="toggleUITab('bombers')" class="dropdown_parent">
								<a href="#">
									<fmt:formatNumber value="${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)}"/> Planes
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_left toggleable-default-off" id="bombers">
									<ul>
										<c:forEach items="${Producibles.getProduciblesForCategory(ProducibleCategory.BOMBER_PLANE)}" var="producible">
											<c:if test="${home.producibles.getProducible(producible) > 0}">
												<li>
													<fmt:formatNumber value="${home.producibles.getProducible(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td onclick="toggleUITab('bomber_production')" class="dropdown_parent">
								<a href="#">
									+<fmt:formatNumber value="${home.getProduciblesProductionByCategory(ProducibleCategory.BOMBER_PLANE)}"/> per month
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_right toggleable-default-off" id="bomber_production">
									<ul>
										<c:forEach var="producible" items="${Producibles.getProduciblesForCategory(ProducibleCategory.BOMBER_PLANE)}">
											<c:if test="${home.getProduciblesProduction(producible) > 0}">
												<li>
													+<fmt:formatNumber value="${home.getProduciblesProduction(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">Recon Planes</td>
						</tr>
						<tr>
							<td onclick="toggleUITab('recons')" class="dropdown_parent">
								<a href="#">
									<fmt:formatNumber value="${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)}"/> Planes
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_left toggleable-default-off" id="recons">
									<ul>
										<c:forEach items="${Producibles.getProduciblesForCategory(ProducibleCategory.RECON_PLANE)}" var="producible">
											<c:if test="${home.producibles.getProducible(producible) > 0}">
												<li>
													<fmt:formatNumber value="${home.producibles.getProducible(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td onclick="toggleUITab('recon_production')" class="dropdown_parent">
								<a href="#">
									+<fmt:formatNumber value="${home.getProduciblesProductionByCategory(ProducibleCategory.RECON_PLANE)}"/> per month
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
								</a>
								<div class="dropdown_2_right toggleable-default-off" id="recon_production">
									<ul>
										<c:forEach var="producible" items="${Producibles.getProduciblesForCategory(ProducibleCategory.RECON_PLANE)}">
											<c:if test="${home.getProduciblesProduction(producible) > 0}">
												<li>
													+<fmt:formatNumber value="${home.getProduciblesProduction(producible)}"/>${' '.concat(producible.name())}
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
					</table>
					<br>
				</div>
				<div class="tile">
					<div class="title">
						Modifiers
					</div>
					<div class="description">
						<c:forEach items="${home.modifiers}" var="modifier">
							<div class="subtitle">${modifier.type.name}</div>
							<div>
								<ul class="event_effects">
									<c:forEach var="current" items="${modifier.type.effects.entrySet()}">
										<li>
											<c:choose>
												<c:when test="${current.value > 0}">
													<c:choose>
														<c:when test="${current.key.global || modifier.city <= 0}">
															+<fmt:formatNumber value="${current.value}"/>${current.key.text}
														</c:when>
														<c:otherwise>
															+<fmt:formatNumber value="${current.value}"/>${current.key.getText(home.cities.get(modifier.city))}
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${current.key.global || modifier.city <= 0}">
															<fmt:formatNumber value="${current.value}"/>${current.key.text}
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value="${current.value}"/>${current.key.getText(home.cities.get(modifier.city))}
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div id="column_2" class="column">
				<c:if test="${home.eventCount <= 0 && home.hasUnreadNews()}">
					<div class="tile">
						<div class="title">Unread News</div>
						<%--@elvariable id="news" type="java.util.List"--%>
							<%--@elvariable id="event" type="com.watersfall.clocgame.model.news.News"--%>
						<c:forEach var="event" items="${news}">
							<div class="subtile">
								${event.content}
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${home.eventCount > 0}">
					<div class="tile">
						<div class="title">Events</div>
						<%--@elvariable id="news" type="java.util.List"--%>
						<%--@elvariable id="event" type="com.watersfall.clocgame.model.event.Event"--%>
						<c:forEach var="event" items="${home.events}">
							<div class="subtile">
								<div class="${event.event.color} title">${event.event.title}</div>
								<div class="description">
									${event.event.getDescription(home, event)}
								</div>
								<%--@elvariable id="action" type="com.watersfall.clocgame.model.event.EventActions"--%>
								<c:forEach var="action" items="${event.event.getPossibleResponses(home)}">
									<div>
										<div class="subtitle">${action.text}</div>
										<div>
											${action.description}
											<ul class="event_effects">
												<c:forEach var="effect" items="${action.effects}">
													<li>
														<c:choose>
															<c:when test="${effect.getClass().simpleName == 'Modifiers'}">
																Creates modifier "${effect.name}" for <b>${effect.length} months</b> with effects:
																<ul>
																	<c:forEach var="modifier" items="${effect.effects.entrySet()}">
																		<li>
																			<c:choose>
																				<c:when test="${modifier.value > 0}">
																					<c:choose>
																						<c:when test="${modifier.key.global || event.cityId <= 0}">
																							+<fmt:formatNumber value="${modifier.value}"/>${modifier.key.text}
																						</c:when>
																						<c:otherwise>
																							+<fmt:formatNumber value="${modifier.value}"/>${modifier.key.getText(home.cities.get(event.cityId))}
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${modifier.key.global || event.cityId <= 0}">
																							<fmt:formatNumber value="${modifier.value}"/>${modifier.key.text}
																						</c:when>
																						<c:otherwise>
																							<fmt:formatNumber value="${modifier.value}"/>${modifier.key.getText(home.cities.get(event.cityId))}
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</li>
																	</c:forEach>
																</ul>
															</c:when>
															<c:otherwise>
																${effect.display}
															</c:otherwise>
														</c:choose>
													</li>
												</c:forEach>
											</ul>
										</div>
										<button onclick="doEvent(${event.id}, '${action.name()}')">Select</button>
										<hr>
									</div>
								</c:forEach>
								<span class="caption">Expires in ${turn - event.month + 4} months</span>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${home.eventCount <= 0 && !home.hasUnreadNews()}">
					<div class="tile">
						<div class="title">No Unread News or Events</div>
					</div>
				</c:if>
				<div class="tile">
					<c:choose>
						<c:when test="${home.unreadMessages == null || home.unreadMessages.size() <= 0}">
							<div class="title">No Unread Messages</div>
						</c:when>
						<c:otherwise>
							<div class="title">Unread Messages</div>
							<c:forEach items="${home.unreadMessages}" var="message">
								<div class="subtile">
									<div class="title">Message From: ${message.senderNation.nationUrl}</div>
									<div class="description">${message.content}</div>
								</div>
							</c:forEach>
							<button onclick="mark('messages');">Mark as Read</button>
						</c:otherwise>
					</c:choose>
				</div>
				<c:if test="${home.offensive != null}">
					<c:set var="defender" value="${home.offensive.defender}"/>
					<div class="tile">
						<div class="title">Offensive War</div>
						<table class="nation">
							<thead class="red">
							<tr>
								<td colspan="2">${defender.nationUrl}</td>
							</tr>
							</thead>
							<tr>
								<td colspan="2">Estimated Army Size</td>
							</tr>
							<tr>
								<td colspan="2">${defender.stats.armySize}k Personnel</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Training</td>
							</tr>
							<tr>
								<td colspan="2">${defender.stats.armyTraining}%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Equipment Status</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber maxFractionDigits="0" value="${defender.getTotalProduciblesByCategory(ProducibleCategory.INFANTRY_EQUIPMENT) / (defender.stats.armySize * 10)}"/>%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Airforce Size</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber value="${defender.getTotalProduciblesByCategories(ProducibleCategory.FIGHTER_PLANE, ProducibleCategory.BOMBER_PLANE, ProducibleCategory.RECON_PLANE)}"/></td>
							</tr>
							<tr>
								<td colspan="2">Estimated Capital Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Escort Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
						</table>
						<div class="subtile">
							<div class="title">Land</div>
							<div class="description">
								Engage the enemy army with your own, either on the field or in their cities. This is the primary way of winning a war.
								Alternatively, you can fortify your army to make them more resistant to your enemy's attacks.
							</div>
							<button onclick="nation(${defender.id}, 'land_land', 0);">Field Battle</button>
							<button onclick="nation(${defender.id}, 'land_city', 0);">Siege City</button>
							<button onclick="nation(${defender.id}, 'land_fortify', 0);">Fortify</button>
						</div>
						<div class="subtile">
							<div class="title">Air</div>
							<div class="description">
								Use your airforce to diminish your enemies ability to wage war in the air, on the ground, and at home
							</div>
							<button onclick="nation(${defender.id}, 'air_air', 0);">Bomb Airforce</button>
							<button onclick="nation(${defender.id}, 'air_land', 0);">Bomb Troops</button>
							<button onclick="nation(${defender.id}, 'air_city', 0);">Bomb City</button>
						</div>
						<div class="subtile">
							<div class="title">Sea (Non-functional)</div>
							<div class="description">
								Your navy can be used similar to your airforce, but only on targets on or near the water
							</div>
							<button>Engage Fleet</button>
							<button>Bombard Troops</button>
							<button>Bombard Cities</button>
						</div>
					</div>
				</c:if>
				<c:if test="${home.defensive != null}">
					<c:set var="attacker" value="${home.defensive.attacker}"/>
					<div class="tile">
						<div class="title">Defensive War</div>
						<table class="nation">
							<thead class="red">
							<tr>
								<td colspan="2">${attacker.nationUrl}</td>
							</tr>
							</thead>
							<tr>
								<td colspan="2">Estimated Army Size</td>
							</tr>
							<tr>
								<td colspan="2">${attacker.stats.armySize}k Personnel</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Training</td>
							</tr>
							<tr>
								<td colspan="2">${attacker.stats.armyTraining}%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Equipment Status</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber maxFractionDigits="0" value="${attacker.getTotalProduciblesByCategory(ProducibleCategory.INFANTRY_EQUIPMENT) / (attacker.stats.armySize * 10)}"/>%</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Airforce Size</td>
							</tr>
							<tr>
								<td colspan="2"><fmt:formatNumber value="${attacker.getTotalProduciblesByCategories(ProducibleCategory.FIGHTER_PLANE, ProducibleCategory.BOMBER_PLANE, ProducibleCategory.RECON_PLANE)}"/></td>
							</tr>
							<tr>
								<td colspan="2">Estimated Capital Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
							<tr>
								<td colspan="2">Estimated Escort Ships</td>
							</tr>
							<tr>
								<td colspan="2">0</td>
							</tr>
						</table>
						<div class="subtile">
							<div class="title">Land</div>
							<div class="description">
								Engage the enemy army with your own, either on the field or in their cities. This is the primary way of winning a war.
								Alternatively, you can fortify your army to make them more resistant to your enemy's attacks.
							</div>
							<button onclick="nation(${attacker.id}, 'land_land', 0);">Field Battle</button>
							<button onclick="nation(${attacker.id}, 'land_city', 0);">Siege City</button>
							<button onclick="nation(${attacker.id}, 'land_fortify', 0);">Fortify</button>
						</div>
						<div class="subtile">
							<div class="title">Air</div>
							<div class="description">
								Use your airforce to diminish your enemies ability to wage war in the air, on the ground, and at home
							</div>
							<button onclick="nation(${attacker.id}, 'air_air', 0);">Bomb Airforce</button>
							<button onclick="nation(${attacker.id}, 'air_land', 0);">Bomb Troops</button>
							<button onclick="nation(${attacker.id}, 'air_city', 0);">Bomb City</button>
						</div>
						<div class="subtile">
							<div class="title">Sea (Non-functional)</div>
							<div class="description">
								Your navy can be used similar to your airforce, but only on targets on or near the water
							</div>
							<button>Engage Fleet</button>
							<button>Bombard Troops</button>
							<button>Bombard Cities</button>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>
<%@ include file="includes/bottom.jsp" %>