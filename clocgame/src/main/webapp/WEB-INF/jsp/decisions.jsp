<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="/js/decisions.js"></script>
<div class="main">
    <%@ include file="includes/results.jsp" %>
    <table id="policy">
        <tr>
            <td>Decision</td>
            <td>Description</td>
            <td>Options</td>
        </tr>
        <tr>
            <td>Manpower</td>
            <td>Raise or lower manpower</td>
            <td>
                <button class="policyButton" type="submit" onclick="decision('raisemanpower')">Increase</button>
                <button class="policyButton" type="submit" onclick="decision('lowermanpower')">Decrease</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>