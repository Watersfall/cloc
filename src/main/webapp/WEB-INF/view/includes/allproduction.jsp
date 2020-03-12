<%@ include file="taglibs.jsp" %>
<script>
	document.addEventListener("DOMContentLoaded", function(){
		freeFactories = ${home.freeFactories};
	});
</script>
<h1>Production</h1>
<h2 id="factories">${home.freeFactories} Free Factories</h2>
<c:forEach var="production" items="${home.production}">
	<div class="element">
		<%@include file="production.jsp" %>
	</div>
</c:forEach>
