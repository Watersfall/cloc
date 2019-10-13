<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="turn" type="java.lang.Integer"--%>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<table id="nation">
		<tr>
			<th>Decision</th>
			<th>Description</th>
			<th>Options</th>
			<th>Description</th>
			<th>Apply</th>
		</tr>
		<tr>
			<td>Economy</td>
			<td>The structure of our economy</td>
			<td>
				<label for="economy"></label><select id="economy" onchange="updateDesc('economy')" class="select" ${home.policy.changeEconomy + 1 > turn ? 'disabled' : ''}>
					<option class="option" id="economy_socialist" ${home.policy.economy == 0 ? 'selected' : ''} disabled>Socialist Economy</option>
					<option class="option" id="economy_civilian" ${home.policy.economy == 1 ? 'selected' : ''}>Civilian Economy</option>
					<option class="option" id="economy_normal" ${home.policy.economy == 2 ? 'selected' : ''}>Standard Economy</option>
					<option class="option" id="economy_military" ${home.policy.economy == 3 ? 'selected' : ''}>Military Economy</option>
					<option class="option" id="economy_total" ${home.policy.economy == 4 ? 'selected' : ''}>Total Mobilization</option>
				</select>
			</td>
			<td>
				<p class="optionDesc" id="economy_socialist_desc" style="display:${home.policy.economy == 0 ? 'block' : 'none'};">Socialist Economy</p>
				<p class="optionDesc" id="economy_civilian_desc" style="display:${home.policy.economy == 1 ? 'block' : 'none'};">Civilian Economy</p>
				<p class="optionDesc" id="economy_normal_desc" style="display:${home.policy.economy == 2 ? 'block' : 'none'};">Standard Economy</p>
				<p class="optionDesc" id="economy_military_desc" style="display:${home.policy.economy == 3 ? 'block' : 'none'};">Military Economy</p>
				<p class="optionDesc" id="economy_total_desc" style="display:${home.policy.economy == 3 ? 'block' : 'none'};">Total Mobilization</p>
			</td>
			<td>
				<button class="policyButton" onclick="policy('economy')" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>Update</button>
			</td>
		</tr>
		<tr>
			<td>Manpower</td>
			<td>Raise or lower manpower</td>
			<td>
				<label for="manpower"></label><select id="manpower" onchange="updateDesc('manpower')" class="select" ${home.policy.changeManpower + 1 > turn ? 'disabled' : ''}>
					<option class="option" id="manpower_disarmed" ${home.policy.manpower == 0 ? 'selected' : ''}>Disarmed Populace</option>
					<option class="option" id="manpower_volunteer" ${home.policy.manpower == 1 ? 'selected' : ''}>Volunteer Army</option>
					<option class="option" id="manpower_drives" ${home.policy.manpower == 2 ? 'selected' : ''}>Recruitment Drives</option>
					<option class="option" id="manpower_draft" ${home.policy.manpower == 3 ? 'selected' : ''}>Mandatory Draft</option>
					<option class="option" id="manpower_scraping" ${home.policy.manpower == 4 ? 'selected' : ''}>Extended Draft</option>
				</select>
			</td>
			<td>
				<p class="optionDesc" id="manpower_disarmed_desc" style="display:${home.policy.manpower == 0 ? 'block' : 'none'};">Disarmed Populace</p>
				<p class="optionDesc" id="manpower_volunteer_desc" style="display:${home.policy.manpower == 1 ? 'block' : 'none'};">Volunteer Army</p>
				<p class="optionDesc" id="manpower_drives_desc" style="display:${home.policy.manpower == 2 ? 'block' : 'none'};">Recruitment Drives</p>
				<p class="optionDesc" id="manpower_draft_desc" style="display:${home.policy.manpower == 3 ? 'block' : 'none'};">Mandatory Draft</p>
				<p class="optionDesc" id="manpower_scraping_desc" style="display:${home.policy.manpower == 4 ? 'block' : 'none'};">Extended Draft</p>
			</td>
			<td>
				<button class="policyButton" onclick="policy('manpower')" ${home.policy.changeManpower + 1 > turn ? 'disabled' : ''}>Update</button>
			</td>
		</tr>
		<tr>
			<td>Food</td>
			<td>How much food do people deserve</td>
			<td>
				<label for="food2"></label><select id="food2" onchange="updateDesc('food2')" class="select" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>
					<option class="option" id="food2_ration" ${home.policy.food == 0 ? 'selected' : ''}>Strict rationing</option>
					<option class="option" id="food2_normal" ${home.policy.food == 1 ? 'selected' : ''}>Normal</option>
					<option class="option" id="food2_free" ${home.policy.food == 2 ? 'selected' : ''}>Free Food</option>
				</select>
			</td>
			<td>
				<p class="optionDesc" id="food2_ration_desc" style="display:${home.policy.food == 0 ? 'block' : 'none'};">Strict rationing</p>
				<p class="optionDesc" id="food2_normal_desc" style="display:${home.policy.food == 1 ? 'block' : 'none'};">Normal</p>
				<p class="optionDesc" id="food2_free_desc" style="display:${home.policy.food == 2 ? 'block' : 'none'};">Free Food</p>
			</td>
			<td>
				<button class="policyButton" onclick="policy('food2')" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>Update</button>
			</td>
		</tr>
	</table>
</div>
</div>
</body>
</html>