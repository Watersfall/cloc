<c:if test="${empty page}">
	<c:set var="page" value="1"/>
</c:if>
<div class="pagination">
	<c:if test="${page == 1}">
		<a>&lt;</a>
	</c:if>
	<c:if test="${page != 1}">
		<a href="${pageContext.request.contextPath}/${url}/${page - 1}/">&lt;</a>
	</c:if>
	<c:if test="${page > 4}">
		<a href="${pageContext.request.contextPath}/${url}/1/">1</a>
	</c:if>
	<c:forEach begin="${(page < 4 ? 1 : page - 3)}" end="${page + 3}" var="index" varStatus="status">
		<c:if test="${status.first && index > 2}">
			<a class="small">...</a>
		</c:if>
		<c:if test="${index >= 1 && index <= maxPage}">
			<c:if test="${index == page}">
				<a><b style="font-size: 1.05em;">${page}</b></a>
			</c:if>
			<c:if test="${index != page}">
				<a href="${pageContext.request.contextPath}/${url}/${index}/">${index}</a>
			</c:if>
		</c:if>
		<c:if test="${index == page + 3 && index < maxPage - 1}">
			<a class="small">...</a>
		</c:if>
	</c:forEach>
	<c:if test="${page <= maxPage - 4}">
		<a href="${pageContext.request.contextPath}/${url}/${maxPage}/">${maxPage}</a>
	</c:if>
	<c:if test="${page < maxPage}">
		<a href="${pageContext.request.contextPath}/${url}/${page + 1}">&gt;</a>
	</c:if>
	<c:if test="${page >= maxPage}">
		<a>&gt;</a>
	</c:if>
</div>