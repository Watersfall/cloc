<%--@elvariable id="army" type="java.util.HashMap$Node"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<li class="cityElements">
	<h3>${army.value.region == home.foreign.region ? 'Home' : army.value.region.adjective} Army</h3>
	<img src="${pageContext.request.contextPath}/images/army/army.png" alt="army"/>
	<p>Size: ${army.value.army}k Personnel</p>
	<p>Training: ${army.value.training} / 100</p>
	<p>Weapons : ${army.value.weapons} / ${army.value.army * 1000} needed</p>
	<p>Artillery : ${army.value.artillery}</p>
	<button onclick="armyPolicy('conscript', '${army.value.id}')">Conscript</button>
	<button onclick="armyPolicy('train', '${army.value.id}')">Train - $${army.value.trainingCost}k</button>
</li>