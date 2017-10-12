<%--
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://probe.jstripe.com/d/license.shtml
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%--
    Main site decorator. Face of the Probe.

    Author: Vlad Ilyushchenko
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="${lang}" xml:lang="${lang}">
<head>
    <title>Probe - <decorator:title default="Tomcat management"/></title>
    <LINK rel="shortcut icon" href="<c:url value="/system/css/favicon.gif"/>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}<spring:theme code="tables.css"/>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}<spring:theme code="main.css"/>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}<spring:theme code="mainnav.css"/>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}<spring:theme code="messages.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/system/css/classic/tooltip.css"/>" type="text/css"/>
    <decorator:head/>
</head>

<body>
<c:choose>
    <c:when test="${! empty use_decorator}">
        <page:applyDecorator name="${use_decorator}">
            <decorator:body/>
        </page:applyDecorator>
    </c:when>
    <c:otherwise>
        <div id="mainBody">
            <decorator:body/>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>