<%@ include file="includes/defaultTop.jsp" %>
	<c:if test="${empty user}">
		<p>You must be logged in to view this page!</p>
	</c:if>
	<c:if test="${not empty home}">
		<h1>Settings</h1>
		<div class="specialTable">
			<div class="policy">
				<div class="name">
					<label for="flag">
						<p>Flag</p>
					</label>
				</div>
				<div class="settings">
					<form id="flagForm" enctype="multipart/form-data" method="POST" action="${pageContext.request.contextPath}/settings/" >
						<input type="file" accept="image/png" max="1" name="flag" id="flag"/>
						<p>PNG Files only! 1024x768 max resolution, 1MB max file size</p>
					</form>
				</div>
				<div class="button">
					<button onclick="document.getElementById('flagForm').submit();">Update Flag</button>
				</div>
			</div>
			<div class="policy">
				<div class="name">
					<label for="portrait">
						<p>Portrait</p>
					</label>
				</div>
				<div class="settings">
					<form id="portraitForm" enctype="multipart/form-data" method="POST" action="${pageContext.request.contextPath}/settings/">
						<input type="file" accept="image/png" max="1" name="portrait" id="portrait"/>
						<p>PNG Files only! 768x1024 max resolution, 1MB max file size</p>
					</form>
				</div>
				<div class="button">
					<button onclick="document.getElementById('portraitForm').submit();">Update Portrait</button>
				</div>
			</div>
			<div class="policy">
				<div class="name">
					<label for="nationTitle">
						<p>Nation Title</p>
					</label>
				</div>
				<div class="settings">
					<input type="text" maxlength="128" name="nationTitle" id="nationTitle" value="<c:out value="${home.cosmetic.nationTitle}"/>" placeholder="Nation Title"/>
				</div>
				<div class="button">
					<button onclick="settings('nationTitle', document.getElementById('nationTitle').value)">Update Nation Title</button>
				</div>
			</div>
			<div class="policy">
				<div class="name">
					<label for="leaderTitle">
						<p>Leader Title</p>
					</label>
				</div>
				<div class="settings">
					<input type="text" maxlength="128" name="leaderTitle" id="leaderTitle" value="<c:out value="${home.cosmetic.leaderTitle}"/>" placeholder="Leader Title"/>
				</div>
				<div class="button">
					<button onclick="settings('leaderTitle', document.getElementById('leaderTitle').value)">Update Leader Title</button>
				</div>
			</div>
			<div class="policy">
				<div class="name">
					<label for="description">
						<p>Description</p>
					</label>
				</div>
				<div class="settings">
					<textarea maxlength="65536" name="description" id="description" placeholder="Description"><c:out value="${home.cosmetic.description}"/></textarea>
				</div>
				<div class="button">
					<button onclick="settings('description', document.getElementById('description').value)">Update Description</button>
				</div>
			</div>
		</div>
		<button onclick="settingsAll(document.getElementById('flag').value, document.getElementById('portrait').value, document.getElementById('nationTitle').value, document.getElementById('leaderTitle').value, document.getElementById('description').value)">Update All</button>
	</c:if>
<%@ include file="includes/defaultBottom.jsp" %>
