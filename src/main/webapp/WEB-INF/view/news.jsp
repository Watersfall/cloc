<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:if test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<h1>National News</h1>
		<c:forEach items="${home.news.news.entrySet()}" var="news">
			<jsp:useBean id="newsDate" class="java.util.Date" />
			<jsp:setProperty name="newsDate" property="time" value="${news.value.time}" />
			<div class="specialTable">
				<div class="full">
					<div class="newsContent">
						<p class="halfPad">${news.value.content}</p>
					</div>
					<div class="newsButton">
						<button class="right" onclick="newsDelete(${news.value.id})">Delete</button>
					</div>
				</div>
				<div class="full">
					<div class="date">
						<p class="halfPad"><fmt:formatDate value="${newsDate}" pattern="yyyy-MM-dd hh:mm:ss"/></p>
					</div>
				</div>
			</div>
			<br>
		</c:forEach>
		<button onclick="newsDelete('all')">Delete All</button>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>