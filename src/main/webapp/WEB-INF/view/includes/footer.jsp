<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="serverTime" value="${now}" pattern="hh:mm" />
<div id="footer">
	<hr style="max-width: 38em;">
	<h3 style="margin: 0.25rem; color: black;">Today's Date: ${time.date}</h3>
	<h4 style="margin: 0.25rem; color: black;">Server Time: ${serverTime}</h4>
	<hr style="max-width: 38em;">
</div>
