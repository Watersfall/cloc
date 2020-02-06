<%--@elvariable id="treaties" type="java.util.ArrayList"--%>
<%--@elvariable id="treaty" type="com.watersfall.clocgame.model.treaty.Treaty"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<h1>Treaties</h1>
	<table class="standardTable">
		<tr>
			<th class="hideOnMobile" style="width: 8em;">>Alliance</th>
			<th></th>
			<th>Description</th>
			<th>Members</th>
		</tr>
		<c:forEach var="treaty" items="${treaties}">
			<tr>
				<td class="hideOnMobile"><img class="large" src="${pageContext.request.contextPath}/user/treaty/<c:out value="${treaty.flag}"/>" alt="flag"/></td>
				<td><a href="${pageContext.request.contextPath}/treaty/${treaty.id}"><c:out value="${treaty.name}"/></a></td>
				<td><c:out value="${treaty.description}"/></td>
				<td>${treaty.memberCount}</td>
			</tr>
		</c:forEach>
	</table>
	<%@include file="includes/pagination.jsp"%>
<%@ include file="includes/defaultBottom.jsp" %>