<div class="categories" id="pagination" style="text-align: center; margin-top: 1em;">
	<ul style="display: inline-block; font-size: 1.25em;">
		<c:if test="${page == 1}">
			<li><a>&lt;</a></li>
		</c:if>
		<c:if test="${page != 1}">
			<li><a href="${pageContext.request.contextPath}/${url}/${page - 1}/">&lt;</a></li>
		</c:if>
		<c:if test="${page > 4}">
			<li><a href="${pageContext.request.contextPath}/${url}/1/">1</a></li>
		</c:if>
		<c:forEach begin="${(page < 4 ? 1 : page - 3)}" end="${page + 3}" var="index" varStatus="status">
			<c:if test="${status.first && index > 2}">
				<li><a>...</a></li>
			</c:if>
			<c:if test="${index >= 1 && index <= maxPage}">
				<c:if test="${index == page}">
					<li><a><b style="font-size: 1.05em;">${page}</b></a></li>
				</c:if>
				<c:if test="${index != page}">
					<li><a href="${pageContext.request.contextPath}/${url}/${index}/">${index}</a></li>
				</c:if>
			</c:if>
			<c:if test="${index == page + 3 && index < maxPage - 1}">
				<li><a>...</a></li>
			</c:if>
		</c:forEach>
		<c:if test="${page <= maxPage - 4}">
			<li><a href="${pageContext.request.contextPath}/${url}/${maxPage}/">${maxPage}</a></li>
		</c:if>
		<c:if test="${page < maxPage}">
			<li><a href="${pageContext.request.contextPath}/${url}/${page + 1}">&gt;</a></li>
		</c:if>
		<c:if test="${page >= maxPage}">
			<li><a>&gt;</a></li>
		</c:if>
	</ul>
</div>