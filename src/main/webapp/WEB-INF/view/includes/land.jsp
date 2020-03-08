<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<a id="land">
	<img src="${pageContext.request.contextPath}/images/war/army.png" alt="army"/><br>
	<button onclick="send('land', null, ${nation.id})">Attack Army</button><br>
	<button onclick="send('landCity', null, ${nation.id})">Siege City</button><br>
	<button onclick="send('fortify', null, ${nation.id})">Fortify</button>
</a>