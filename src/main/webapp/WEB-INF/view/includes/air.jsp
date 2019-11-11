<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="air">
	<img src="${pageContext.request.contextPath}/images/war/air.png" alt="air"/><br>
	<button onclick="send('air', null, ${nation.id})">Attack Airforce</button><br>
	<button onclick="send('airCity', null, ${nation.id})">Bomb City</button><br>
	<button onclick="send('bomb', null, ${nation.id})">Bomb Troops</button><br>
</div>