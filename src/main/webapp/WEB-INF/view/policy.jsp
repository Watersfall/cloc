<%@ include file="includes/defaultTop.jsp" %>
	<h1>State Policy</h1>
	<div class="specialTable">
		<div class="header">
			<div class="name"><p class="halfPad">Decision</p></div>
			<div class="cost"><p class="halfPad">Options</p></div>
			<div class="description"><p class="halfPad">Description</p></div>
			<div class="button"><p class="halfPad">Apply</p></div>
		</div>
		<div class="policy">
			<div class="name"><p class="halfPad">Economy</p></div>
			<div class="cost">
				<label for="economy"></label>
				<select class="halfPad" id="economy" onchange="updateDesc('economy')" ${home.policy.changeEconomy + 1 > turn ? 'disabled' : ''}>
					<option id="economy_socialist" ${home.policy.economy == 0 ? 'selected' : ''} disabled>Socialist Economy</option>
					<option id="economy_civilian" ${home.policy.economy == 1 ? 'selected' : ''}>Civilian Economy</option>
					<option id="economy_normal" ${home.policy.economy == 2 ? 'selected' : ''}>Standard Economy</option>
					<option id="economy_military" ${home.policy.economy == 3 ? 'selected' : ''}>Military Economy</option>
					<option id="economy_total" ${home.policy.economy == 4 ? 'selected' : ''}>Total Mobilization</option>
				</select>
			</div>
			<div class="description">
				<p class="desc halfPad" id="economy_socialist_desc" style="display:${home.policy.economy == 0 ? 'block' : 'none'};">Socialist Economy</p>
				<p class="desc halfPad" id="economy_civilian_desc" style="display:${home.policy.economy == 1 ? 'block' : 'none'};">Civilian Economy</p>
				<p class="desc halfPad" id="economy_normal_desc" style="display:${home.policy.economy == 2 ? 'block' : 'none'};">Standard Economy</p>
				<p class="desc halfPad" id="economy_military_desc" style="display:${home.policy.economy == 3 ? 'block' : 'none'};">Military Economy</p>
				<p class="desc halfPad" id="economy_total_desc" style="display:${home.policy.economy == 3 ? 'block' : 'none'};">Total Mobilization</p>
			</div>
			<div class="button">
				<button onclick="policy('economy')" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>Update</button>
			</div>
		</div>
		<div class="policy">
			<div class="name"><p class="halfPad">Manpower</p></div>
			<div class="cost">
				<label for="manpower"></label>
				<select id="manpower" onchange="updateDesc('manpower')" class="halfPad" ${home.policy.changeManpower + 1 > turn ? 'disabled' : ''}>
					<option id="manpower_disarmed" ${home.policy.manpower == 0 ? 'selected' : ''}>Disarmed Populace</option>
					<option id="manpower_volunteer" ${home.policy.manpower == 1 ? 'selected' : ''}>Volunteer Army</option>
					<option id="manpower_drives" ${home.policy.manpower == 2 ? 'selected' : ''}>Recruitment Drives</option>
					<option id="manpower_draft" ${home.policy.manpower == 3 ? 'selected' : ''}>Mandatory Draft</option>
					<option id="manpower_scraping" ${home.policy.manpower == 4 ? 'selected' : ''}>Extended Draft</option>
				</select>
			</div>
			<div class="description">
				<p class="desc halfPad" id="manpower_disarmed_desc" style="display:${home.policy.manpower == 0 ? 'block' : 'none'};">Disarmed Populace</p>
				<p class="desc halfPad" id="manpower_volunteer_desc" style="display:${home.policy.manpower == 1 ? 'block' : 'none'};">Volunteer Army</p>
				<p class="desc halfPad" id="manpower_drives_desc" style="display:${home.policy.manpower == 2 ? 'block' : 'none'};">Recruitment Drives</p>
				<p class="desc halfPad" id="manpower_draft_desc" style="display:${home.policy.manpower == 3 ? 'block' : 'none'};">Mandatory Draft</p>
				<p class="desc halfPad" id="manpower_scraping_desc" style="display:${home.policy.manpower == 4 ? 'block' : 'none'};">Extended Draft</p>
			</div>
			<div class="button">
				<button onclick="policy('manpower')" ${home.policy.changeManpower + 1 > turn ? 'disabled' : ''}>Update</button>
			</div>
		</div>
		<div class="policy">
			<div class="name"><p class="halfPad">Food</p></div>
			<div class="cost">
				<label for="food2"></label>
				<select id="food2" onchange="updateDesc('food2')" class="halfPad" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>
					<option id="food2_ration" ${home.policy.food == 0 ? 'selected' : ''}>Strict rationing</option>
					<option id="food2_normal" ${home.policy.food == 1 ? 'selected' : ''}>Normal</option>
					<option id="food2_free" ${home.policy.food == 2 ? 'selected' : ''}>Free Food</option>
				</select>
			</div>
			<div class="description">
				<p class="desc halfPad" id="food2_ration_desc" style="display:${home.policy.food == 0 ? 'block' : 'none'};">Strict rationing</p>
				<p class="desc halfPad" id="food2_normal_desc" style="display:${home.policy.food == 1 ? 'block' : 'none'};">Normal</p>
				<p class="desc halfPad" id="food2_free_desc" style="display:${home.policy.food == 2 ? 'block' : 'none'};">Free Food</p>
			</div>
			<div class="button">
				<button onclick="policy('food2')" ${home.policy.changeFood + 1 > turn ? 'disabled' : ''}>Update</button>
			</div>
		</div>
	</div>
<%@ include file="includes/defaultBottom.jsp" %>