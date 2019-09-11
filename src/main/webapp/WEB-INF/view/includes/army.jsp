<%@ include file="default.jsp" %>
<%--@elvariable id="army" type="java.util.HashMap$Node"--%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<style>
	.table{
		width: 75%;
		margin-left:auto;
		margin-right:auto;
	}
	.table td{
		text-align: center;
	}
	p{
		text-align: inherit;
	}
</style>
<li class="cityElements">
	<h3>${army.value.region == home.foreign.region ? 'Home' : army.value.region.adjective} Army</h3>
	<img src="${pageContext.request.contextPath}/images/army/army.png" alt="army"/>
	<table class="table">
		<tr>
			<td><p>Size: </p></td>
			<td><p>${army.value.army}k Personnel</p></td>
		</tr>
		<tr>
			<td><p>Training: </p></td>
			<td><p>${army.value.training} / 100</p></td>
		</tr>
		<tr>
			<td><p>Weapons: </p></td>
			<td><p><fmt:formatNumber value="${army.value.weapons}"/> / <fmt:formatNumber value="${army.value.army * 1000}"/> needed</p></td>
		</tr>
		<tr>
			<td><p>Artillery: </p></td>
			<td><p>${army.value.artillery}</p></td>
		</tr>
	</table>
	<button ${manpower < 2000 ? 'disabled' : ''} onclick="armyPolicy('conscript', '${army.value.id}')">Conscript</button>
	<button onclick="armyPolicy('train', '${army.value.id}')">Train - $${army.value.trainingCost}k</button>
	<button onclick="armyPolicy('deconscript', '${army.value.id}')">Deconscript</button>
</li>