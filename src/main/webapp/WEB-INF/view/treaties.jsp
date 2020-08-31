<%@ include file="includes/top.jsp" %>
<div class="title">Treaties</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<%--@elvariable id="treaties" type="java.util.List"--%>
				<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
				<c:forEach items="${treaties}" var="treaty">
					<div class="subtitle"><img src="/user/treaty/${treaty.flag}" alt="flag" class="small_flag"/> ${treaty.treatyUrl}: ${treaty.memberCount} Member${treaty.memberCount == 1 ? '' : 's'}</div>
					<div class="description left_text">
						<c:out value="${treaty.description}"/>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<%@ include file="includes/pagination.jsp"%>
<%@ include file="includes/bottom.jsp" %>