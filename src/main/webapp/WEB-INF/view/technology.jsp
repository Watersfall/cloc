<%@ include file="includes/top.jsp" %>
	<div class="title">Technology Tree</div>
	<br>
	<div class="pagination">
		<c:forEach items="${categories}" var="category">
			<a href="${pageContext.request.contextPath}/technology/tree/${category.name()}">
				${category.name}
			</a>
		</c:forEach>
	</div>
	<div class="tiling" id="tech" style="display: none;">

	</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<%@ include file="api/techtree.jsp"%>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>