<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<c:if test="${empty home}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<h1>National News</h1>
		<c:if test="${home.eventCount <= 0}">
			<button onclick="newsDelete('all')">Delete All</button>
		</c:if>
		<c:if test="${home.eventCount > 0}">
			<c:forEach var="event" items="${home.events}">
				<div class="specialTable">
					<div class="full">
						<div class="event">
							<c:out value="${fn:replace(event.description, '{event.id}', event.id)}" escapeXml="false"/>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${home.eventCount <= 0}">
			<c:forEach items="${news}" var="news">
				<jsp:useBean id="newsDate" class="java.util.Date" />
				<jsp:setProperty name="newsDate" property="time" value="${news.time}" />
				<div class="specialTable" id="news_${news.id}">
					<div class="full">
						<div class="newsContent">
							<p class="halfPad">${news.content}</p>
						</div>
						<div class="newsButton">
							<button class="right" onclick="newsDelete(${news.id})">Delete</button>
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
		</c:if>
		<c:if test="${home.eventCount <= 0}">
			<%@include file="includes/pagination.jsp"%>
			<br>
			<button onclick="newsDelete('all')">Delete All</button>
		</c:if>
		<br>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>