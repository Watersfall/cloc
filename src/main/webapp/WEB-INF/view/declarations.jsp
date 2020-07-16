<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="declarations" type="java.util.List"--%>
<%@ include file="includes/defaultTop.jsp" %>
	<h1>Global Declarations</h1>
	<c:forEach var="declaration" items="${declarations}">
		<div class="specialTable">
			<div>
				<a class="full declaration" href="${pageContext.request.contextPath}/nation/${declaration.sender.id}">
					<img class="verySmall floatLeft halfPad" style="padding-right: 0.25em;" src="/user/flag/${declaration.sender.cosmetic.flag}" alt="flag">
					<p class="textLeft halfPad"><b>${declaration.sender.cosmetic.nationName}</b> declares:</p>
				</a>
			</div>
			<div>
				<a class="full declaration">
					<p class="textLeft halfPad">${declaration.content}</p>
				</a>
			</div>
			<br>
		</div>
	</c:forEach>
	<%@include file="includes/pagination.jsp"%>
	<br>
	<c:if test="${home != null}">
		<label for="post">Post Declaration<br></label>
		<textarea style="max-width: 25em; min-width: 75%; height: 5em;" id="post"></textarea><br>
		<button onclick="postDeclaration()">Post - $${home.declarationCost}k</button>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>