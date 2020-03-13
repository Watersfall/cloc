<%--@elvariable id="description" type="java.lang.String"--%>
<!DOCTYPE html>
<head>
	<title>&ltCLOC - Online Nation Sim</title>
	<c:set value="2" var="version"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css?v=${version}">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tech.css?v=${version}">
	<script src="${pageContext.request.contextPath}/js/header.js?v=${version}"></script>
	<script src="${pageContext.request.contextPath}/js/actions.js?v=${version}"></script>
	<script src="${pageContext.request.contextPath}/js/graph.js?v=${version}"></script>
	<script src="${pageContext.request.contextPath}/js/production.js?v=${version}"></script>
	<meta name="description" content="${description}">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1">
	<script>
		setContext("${pageContext.request.contextPath}");
	</script>
</head>