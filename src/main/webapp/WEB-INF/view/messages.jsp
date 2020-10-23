<%@ include file="includes/top.jsp" %>
	<div class="tiling">
		<div class="column">
			<c:if test="${sender}">
				<div class="title">Sent Messages</div>
			</c:if>
			<c:if test="${!sender}">
				<div class="title">Received Messages</div>
			</c:if>
			<c:if test="${sender}">
				<c:forEach var="message" items="${messages}">
					<div class="tile">
						<div class="subtitle">Message Sent To: ${message.receiverNation.nationUrl}</div>
						<div class="description left_text">
							<c:out escapeXml="false" value="${message.content}"/>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${!sender}">
				<c:forEach var="message" items="${messages}">
					<div class="tile">
						<div class="subtitle">Message From: ${message.senderNation.nationUrl}</div>
						<div class="description left_text">
							<c:out escapeXml="false" value="${message.content}"/>
						</div>
						<div class="left_text">
							<button onclick="deleteMessage(${message.id})" class="red">Delete</button>
							<a href="${pageContext.request.contextPath}/nation/${message.senderId}#send_message" class="button">Reply</a>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<%@ include file="includes/pagination.jsp"%>
<%@ include file="includes/bottom.jsp" %>