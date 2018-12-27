<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cloc" uri="/WEB-INF/custom.tld" %>
<sql:setDataSource var = "database" driver = "com.mysql.cj.jdbc.Driver" url = "jdbc:mysql://localhost/cloc" user = "root"  password = "***REMOVED***"/>
<c:set var="sess" scope="session">
    ${pageContext.session.id}
</c:set>
<sql:query dataSource="${database}" var="result" scope="session">
    SELECT * FROM cloc_main WHERE sess=?
    <sql:param value="${sess}" />
</sql:query>
<c:set var="mobile" scope="session">
    ${fn:containsIgnoreCase(header['User-Agent'],'iphone')
        || fn:containsIgnoreCase(header['User-Agent'], 'android')
        || fn:containsIgnoreCase(header['User-Agent'], 'mobile') }
</c:set>
<!DOCTYPE html>