<%--@elvariable id="description" type="java.lang.String"--%>
<%--@elvariable id="home" type="net.watersfall.clocgame.model.nation.Nation"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="net.watersfall.clocgame.model.alignment.Alignments" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cloc" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<head>
	<title>&ltCLOC - Online Nation Sim</title>
	<c:set value="22" var="version"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css?v=${version}">
	<script src="${pageContext.request.contextPath}/js/main.js?v=${version}"></script>
	<meta name="description" content="${description}">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1">
	<script>
		window.onload = function(){
			document.addEventListener("click", test);
		};
		function test(event)
		{
			if(!anyParentIsToggleTab(event.target))
			{
				toggleUITab(null);
			}
		}
	</script>
</head>
	<body>
		<header>
			<c:if test="${home != null}">
				<a href="${pageContext.request.contextPath}/main/">
					<img src="/user/flag/<c:out escapeXml="false" value="${home.cosmetic.flag}"/>" alt="flag">
				</a>
			</c:if>
			<a href="#" onclick="toggle('nav')">
				<p>&#9776;</p>
			</a>
		</header>
		<nav id="nav">
			<c:if test="${home != null}">
				<a href="${pageContext.request.contextPath}/main/">
					<img src="/user/flag/${home.cosmetic.flag}" alt="flag"/>
				</a>
				<ul>
					<li>
						<a href="${pageContext.request.contextPath}/messages/">Messages</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/news/">News</a>
					</li>
					<li>
						<a href="#" onclick="toggle('nation_sublist')">Nation</a>
						<ul id="nation_sublist" class="toggleable-default-off sublist">
							<li>
								<a href="${pageContext.request.contextPath}/policy/">Policy</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/decisions/all">Decisions</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/production/">Production</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/technology/tree">Technology</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/military/">Military</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/alignment/">Alignment</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/cities/">Cities</a>
					</li>
					<li>
						<a href="#" onclick="toggle('realpolitik_sublist')">Realpolitik</a>
						<ul id="realpolitik_sublist" class="toggleable-default-off sublist">
							<li>
								<a href="${pageContext.request.contextPath}/rankings/">World Rankings</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/map/">World Map</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/declarations/">Global Declarations</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/worldnews/">World News</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#" onclick="toggle('treaties_sublist');">Treaties</a>
						<ul id="treaties_sublist" class="toggleable-default-off sublist">
							<c:if test="${home.treaty != null}">
								<li>
									<a href="${pageContext.request.contextPath}/treaty/${home.treaty.id}">My Treaty</a>
								</li>
							</c:if>
							<li>
								<a href="${pageContext.request.contextPath}/treaties/">Treaties</a>
							</li>
						</ul>
					</li>
				</ul>
				<div class="nav-bottom">
					<a href="${pageContext.request.contextPath}/logout/">Logout</a>
					<a href="${pageContext.request.contextPath}/settings/">Settings</a>
				</div>
			</c:if>
			<c:if test="${home == null}">
				<ul>
					<li>
						<a href="${pageContext.request.contextPath}/">
							<h1>&ltCLOC</h1>
						</a>
					</li>
					<li>
						<label for="login_form"><a>Login</a></label>
						<form id="login_form" onsubmit="login(); return false;" method="POST">
							<label> Username
								<input id="username" type="text" name="username" placeholder="Username">
							</label><br>
							<label> Password
								<input id="password" type="password" name="password" placeholder="Password">
							</label><br><br>
							<button class="light_text_button" type="submit">Login</button>
							<div id="login_error" class="toggleable-default-off error">

							</div>
						</form>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/register/">Register</a>
					</li>
					<li>
						<a href="https://discord.gg/x2VwYkS">Discord</a>
					</li>
					<li>
						<a href="https://github.com/Watersfall/cloc">Github</a>
					</li>
				</ul>
			</c:if>
		</nav>
		<div id="results" ${not empty result ? 'style="display:block"' : ''}>
			<div id="results_content">${result}</div>
			<button onclick="toggle('results')" class="red">Close</button>
		</div>
		<div id="container">
			<main>