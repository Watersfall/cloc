<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<div id="navy">
	<img src="${pageContext.request.contextPath}/images/war/navy.png" alt="air"/><br>
	<button onclick="send('navy', null, ${nation.id})">Naval Battle</button><br>
	<button onclick="send('navyCity', null, ${nation.id})">Bombard City</button><br>
</div>