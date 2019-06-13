<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cloc" uri="/WEB-INF/custom.tld" %>
<cloc:database var="database"/>
<c:set var="sess" scope="session">
    ${pageContext.session.id}
</c:set>
<c:set var="id" value="-1"/>
<sql:query dataSource="${database}" var="check" scope="page">
    SELECT * FROM cloc_login WHERE sess=?
    <sql:param value="${sess}"/>
</sql:query>
<c:if test="${check.rowCount >= 0}">
    <c:set var="id" value="${check.rows[0].id}"/>
</c:if>
<c:set var="mobile" scope="session">
    ${fn:containsIgnoreCase(header['User-Agent'],'iphone')
        || fn:containsIgnoreCase(header['User-Agent'], 'android')
        || fn:containsIgnoreCase(header['User-Agent'], 'mobile') }
</c:set>
<!DOCTYPE html>