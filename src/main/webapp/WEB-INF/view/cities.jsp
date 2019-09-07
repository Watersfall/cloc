<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="${pageContext.request.contextPath}/js/policies.js"></script>
<script>
    function loadCity(id)
    {
        let url = new URL(window.location.href);
        if((url.searchParams.get("id") != id))
        {
            document.getElementById("city").innerHTML = "<p>Loading...</p>";
            let xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/city.jsp?id=" + id, true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState === 4 && xhttp.status === 200) {
                    document.getElementById("city").innerHTML = xhttp.responseText;
                    window.history.replaceState("id: " + id, "", window.location.href.substring(0, window.location.href.indexOf("id=") + 3) + id);
                }
            };
        }
    }
</script>
<div class="main">
    <%@ include file="includes/results.jsp" %>
    <table id="nation">
        <tr>
            <c:forEach var="i" items="${home.cities.cities}">
                <th style="width: ${100 / home.cities.cities.size}%" onclick="loadCity(${i.key})">${i.value.name}</th>
            </c:forEach>
        </tr>
    </table>
    <%@ include file="includes/city.jsp" %>
</div>
</body>
</html>