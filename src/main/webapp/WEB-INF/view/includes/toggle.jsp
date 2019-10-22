<div class="toggle" onclick="toggle('top')">
	<c:if test="${home != null}">
		<a href="${pageContext.request.contextPath}/main.jsp">
			<img class="toggleFlag" src="https://i.imgur.com/<c:out value="${home.cosmetic.flag}"/>" alt="flag">
		</a>
	</c:if>
	<img class="toggleButton" src="${pageContext.request.contextPath}/images/ui/arrow-down.svg" alt="toggle"/>
</div>