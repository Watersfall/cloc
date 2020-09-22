<%@ include file="includes/top.jsp" %>
	<div class="tiling">
		<div class="column">
			<div class="title">News</div>
			<%--@elvariable id="news" type="java.util.List"--%>
			<%--@elvariable id="item" type="com.watersfall.clocgame.model.news.News"--%>
			<c:forEach items="${news}" var="item">
				<div class="tile left_text">
					<div class="news">
						<div>${item.content}</div>
						<div>
							<jsp:useBean id="newsDate" class="java.util.Date" />
							<jsp:setProperty name="newsDate" property="time" value="${item.time}" />
							<fmt:formatDate value="${newsDate}" pattern="yyyy-MM-dd hh:mm:ss"/>
							<button onclick="deleteNews('${item.id}')" class="red right">Delete</button>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<%@ include file="includes/pagination.jsp"%>
<%@ include file="includes/bottom.jsp" %>