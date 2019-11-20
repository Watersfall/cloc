<div class="toggle" onclick="toggle('top')">
	<c:if test="${home != null}">
		<a href="${pageContext.request.contextPath}/main/">
			<img class="toggleFlag" src="${pageContext.request.contextPath}/user/flag/<c:out value="${home.cosmetic.flag}"/>" alt="flag">
		</a>
	</c:if>
	<img class="toggleButton" src="${pageContext.request.contextPath}/images/ui/dropdown.svg" alt="toggle"/>
</div>