<%@ include file="includes/top.jsp" %>
<%@ page import="net.watersfall.clocgame.model.military.army.ArmyLocation" %>
<%--@elvariable id="defender" type="net.watersfall.clocgame.model.nation.Nation"--%>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Our Army</div>
				<div class="our_army_plan">
					<div class="subtitle">Target</div>
					<div class="left_text">
						<label onclick="getEstimatedBattlePlan(${defender.id}, '${ArmyLocation.NATION}_${defender.id}')">
							<input type="radio" name="target" id="nation" value="${ArmyLocation.NATION}_${defender.id}"/>
							Nation
						</label><br>
						Cities: <br>
						<c:forEach var="city" items="${defender.cities.values()}">
							<label onclick="getEstimatedBattlePlan(${defender.id}, '${ArmyLocation.CITY}_${city.id}')">
								<input type="radio" name="target" id="city_${city.id}" value="${ArmyLocation.CITY}_${city.id}"/>
								${city.name}
							</label>
						</c:forEach>
					</div>
					<div class="subtitle">Armies</div>
					<div class="left_text">
						<c:forEach var="army" items="${home.armies}">
							<label>
								<input type="checkbox" name="armies" id="army_${army.id}" value="${army.id}"/>
								${army.name}
							</label><br>
						</c:forEach>
					</div>
				</div>
				<button class="red" onclick="battle(${defender.id})">Launch Attack!</button>
			</div>
		</div>
		<div class="column">
			<div class="tile">
				<div class="title">Estimated Enemy Plan</div>
				<div id="enemy_battle_plan">

				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>