<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cloc" uri="/WEB-INF/custom.tld" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld" %>
<cloc:database var="database"/>
<c:set var="mobile" scope="session">
    ${fn:containsIgnoreCase(header['User-Agent'],'iphone')
        || fn:containsIgnoreCase(header['User-Agent'], 'android')
        || fn:containsIgnoreCase(header['User-Agent'], 'mobile') }
</c:set>
<%--@elvariable id="home" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="nation" type="com.watersfall.clocgame.model.nation.Nation"--%>
<%--@elvariable id="growth" type="java.util.HashMap"--%>
<%--@elvariable id="user" type="java.lang.Integer"--%>
<%--@elvariable id="turn" type="java.lang.Integer"--%>
<%--@elvariable id="production" type="java.util.HashMap"--%>
<%--@elvariable id="food" type="java.util.HashMap"--%>
<%--@elvariable id="population" type="java.util.HashMap"--%>
