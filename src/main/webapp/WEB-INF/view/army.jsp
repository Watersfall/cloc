<%--@elvariable id="army" type="com.watersfall.clocgame.model.military.army.Army"--%>
<%@ include file="includes/top.jsp" %>
<%@ page import="com.watersfall.clocgame.model.military.army.BattalionType" %>
<%@ page import="com.watersfall.clocgame.model.military.army.Priority" %>
	<div class="title">${army.name}</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Stats</div>
				<table class="nation nation_left full_width">
					<tr>
						<td colspan="2">Size</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${army.size}"/> / <fmt:formatNumber value="${army.maxSize}"/> Soldiers</td>
						<td>
							<a href="#">
								+<fmt:formatNumber value="${home.armyManpowerChange.getOrDefault(army, 0)}"/> Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							Priority
						</td>
					</tr>
					<tr>
						<td colspan="2">${army.priority.name()}</td>
					</tr>
					<tr>
						<td colspan="2">Equipment</td>
					</tr>
					<tr>
						<td>
							<c:forEach var="equipment" items="${army.maxEquipment.entrySet()}">
								<fmt:formatNumber value="${army.equipment.getOrDefault(equipment.key, 0)}"/> / <fmt:formatNumber value="${equipment.value}"/>${' '.concat(equipment.key.name())}<br>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="equipment" items="${army.maxEquipment.entrySet()}">
								<a href="#">
									<c:choose>
										<c:when test="${home.armyEquipmentChange.get(army) != null}">
											+<fmt:formatNumber value="${home.armyEquipmentChange.get(army).getOrDefault(equipment.key, 0)}"/> per month
										</c:when>
										<c:otherwise>
											+0 per month
										</c:otherwise>
									</c:choose>
									<img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
									<br>
								</a>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td colspan="2">Training</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${army.training / 100}" maxFractionDigits="0"/>%</td>
						<td>
							<a href="#">
								+0 Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">Experience</td>
					</tr>
					<tr>
						<td><fmt:formatNumber value="${army.experience / 100}" maxFractionDigits="0"/>%</td>
						<td>
							<a href="#">
								+0 Per Month <img class="match_text" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="dropdown"/>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">Specialization</td>
					</tr>
					<tr>
						<td>${army.specialization.name()}</td>
						<td><fmt:formatNumber value="${army.specializationAmount / 100}" maxFractionDigits="0"/>%</td>
					</tr>
					<tr>
						<td colspan="2">Attack</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:formatNumber value="${army.attack}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">Defense</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:formatNumber value="${army.defense}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">Breakthrough</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:formatNumber value="${army.breakthrough}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">Location</td>
					</tr>
					<tr>
						<td colspan="2">
							<c:if test="${army.nation != null}">
								The Nation of ${army.nation.nationUrl}
							</c:if>
							<c:if test="${army.city != null}">
								The City of ${army.city.url}
							</c:if>
						</td>
					</tr>
				</table>
			</div>
			<div class="tile">
				<div class="title">Equipment</div>
				<ul>
					<c:forEach items="${army.equipmentTypes.entrySet()}" var="category">
						<li>${category.key}</li>
						<ul>
							<c:forEach items="${category.value.entrySet()}" var="equipment">
								<li>
									<fmt:formatNumber value="${equipment.value}"/> ${' '.concat(equipment.key)}
								</li>
							</c:forEach>
						</ul>
					</c:forEach>
				</ul>
			</div>
			<div class="tile">
				<div class="title">Priority</div>
				<button onclick="army(${army.id}, 'set_priority', '${Priority.HIGH.name()}')" class="red">High</button>
				<button onclick="army(${army.id}, 'set_priority', '${Priority.NORMAL.name()}')" class="green">Normal</button>
				<button onclick="army(${army.id}, 'set_priority', '${Priority.LOW.name()}')" class="blue">Low</button>
			</div>
		</div>
		<div class="column">
			<div class="tile">
				<div class="title">Battalions</div>
				<c:forEach var="battalion" items="${army.battalions}">
					<div class="subtile">
						<div class="title">${battalion.type}</div>
						<table class="nation nation_left left_text full_width">
							<tr>
								<td colspan="2">Size</td>
							</tr>
							<tr>
								<td colspan="2">
									<fmt:formatNumber value="${battalion.size}"/> / <fmt:formatNumber value="${battalion.maxSize}"/> Soldiers
								</td>
							</tr>
							<tr>
								<td colspan="2">Equipment</td>
							</tr>
							<tr>
								<td colspan="2">
									<c:forEach items="${battalion.equipment}" var="equipment">
										<fmt:formatNumber value="${equipment.amount}"/> ${' '.concat(equipment.equipment)}<br>
									</c:forEach>
								</td>
							</tr>
						</table>
						<div class="right_text">
							<button onclick="army(${army.id}, 'delete_battalion', ${battalion.id})" class="red">Delete</button>
						</div>
					</div>
				</c:forEach>
				<button onclick="army(${army.id}, 'create_battalion', '${BattalionType.INFANTRY.name()}')" class="blue">Add Infantry Battalion</button>
				<button onclick="army(${army.id}, 'create_battalion', '${BattalionType.ARTILLERY.name()}')" class="blue">Add Artillery Battalion</button>
				<button onclick="army(${army.id}, 'create_battalion', '${BattalionType.ARMORED.name()}')" class="blue">Add Armored Battalion</button>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>