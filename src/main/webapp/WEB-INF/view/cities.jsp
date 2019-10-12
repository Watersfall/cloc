<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<script>
	function loadCity(id)
	{
		let url = new URL(window.location.href);
		if((url.searchParams.get("id") !== id))
		{
			document.getElementById("city").innerHTML = "<p>Loading...</p>";
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", "/city.jsp?id=" + id, true);
			xhttp.send();
			xhttp.onreadystatechange = function()
			{
				if(xhttp.readyState === 4 && xhttp.status === 200)
				{
					document.getElementById("city").innerHTML = xhttp.responseText;
					window.history.replaceState("id: " + id, "", window.location.href.substring(0, window.location.href.indexOf("id=") + 3) + id);
				}
			};
		}
	}
</script>
<div class="container"><div class="main">
	<%@ include file="includes/results.jsp" %>
	<div class="categories">
		<ul>
			<c:forEach var="i" items="${home.cities.cities}">
				<li style="min-width: ${100 / home.cities.cities.size()}%" onclick="loadCity('${i.key}')">
					<a>
						<div style="width: 100%">${i.value.name}</div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<%@include file="includes/city.jsp"%>
</div>
</div>
</body>
</html>