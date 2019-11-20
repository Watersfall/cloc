<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/side.jsp" %>
<%@ include file="includes/toggle.jsp"%>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<script>
	function loadCity(id)
	{
		let url = new URL(window.location.href);
		if(url.pathname.indexOf(id) === -1)
		{
			document.getElementById("city").innerHTML = "<p>Loading...</p>";
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", "/city/" + id, true);
			xhttp.send();
			xhttp.onreadystatechange = function()
			{
				if(xhttp.readyState === 4 && xhttp.status === 200)
				{
					document.getElementById("city").innerHTML = xhttp.responseText;
					window.history.replaceState("id: " + id, "", window.location.href.substring(0, window.location.href.indexOf("cities/") + 7) + id + "/");
				}
			};
		}
	}
</script>
<div class="container"><%@ include file="includes/results.jsp"%><div class="main">
	<div class="categories">
		<ul>
			<c:forEach var="i" items="${home.cities.cities}">
				<li onclick="loadCity('${i.key}')">
					<a>
						<div style="width: 100%"><c:out value="${i.value.name}"/></div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<%@include file="includes/city.jsp"%>
</div>
<%@ include file="includes/header.jsp" %></div>
</body>
</html>