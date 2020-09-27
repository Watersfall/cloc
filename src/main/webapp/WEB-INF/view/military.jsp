<%@ include file="includes/top.jsp" %>
<%@ page import="com.watersfall.clocgame.model.producible.ProducibleCategory" %>
<div class="title">Military</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<div class="title">Airforce</div>
				<div class="subtile">
					<label>
						<div class="title">Fighters</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)}" ${home.stats.maxFighters < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxFighters)} id="fighters"/>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', document.getElementById('fighters').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.FIGHTER_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
					<label>
						<div class="title">Bombers</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)}" ${home.stats.maxBombers < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxBombers)} id="bombers"/>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', document.getElementById('bombers').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.BOMBER_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
					<label>
						<div class="title">Recon Planes</div>
						<input style="min-width: 12rem;" type="number" min="0" max="${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)}" ${home.stats.maxRecon < 0 ? 'placeholder="UNLIMITED"' : 'value='.concat(home.stats.maxRecon)} id="recon"/>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', document.getElementById('recon').value);" class="blue">Submit</button>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', ${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)});" class="green">Set Max (${home.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE)})</button>
						<button onclick="setAirforceSize('${ProducibleCategory.RECON_PLANE.name()}', -1);" class="red">Set Unlimited</button>
					</label>
				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>