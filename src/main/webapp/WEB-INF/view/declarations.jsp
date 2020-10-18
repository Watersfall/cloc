<%@ include file="includes/top.jsp" %>
	<div class="title">
		Declarations
	</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<%--@elvariable id="declarations" type="java.util.List"--%>
				<%--@elvariable id="declaration" type="net.watersfall.clocgame.model.message.Declaration"--%>
				<c:forEach items="${declarations}" var="declaration">
					<div class="subtitle">
						<img src="/user/flag/${declaration.sender.cosmetic.flag}" alt="flag" class="small_flag"/>
						${declaration.sender.nationUrl} Declared:
					</div>
					<div class="description left_text"><c:out escapeXml="false" value="${declaration.content}"/></div>
				</c:forEach>
			</div>
			<div class="tile">
				<div class="title">
					Post Declaration
				</div>
				<div class="description">
					<form onsubmit="postDeclaration(document.getElementById('content').value); return false;">
						<label>Message<br>
							<textarea id="content"></textarea>
						</label><br>
						<button class="blue" type="submit">Post</button>
					</form>
				</div>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>