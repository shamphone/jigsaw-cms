<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>缓存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate">
  	<LINK rel="shortcut icon" href="${pageContext.request.contextPath}/system/css/favicon.gif">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/system/css/classic/tables.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/system/css/classic/main.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/system/css/classic/mainnav.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/system/css/classic/messages.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/system/css/classic/tooltip.css" type="text/css"/>        </head>
        <body id="contentBody">
        <h3>缓存管理</h3>
        <table class="genericTbl"  cellspacing="0" cellpadding="2" >
<thead>
<tr>
<th>对象</th>
<th>个数</th>
<th>操作</th>
</tr></thead>
<tbody>        
        <bean:define id="cacheFactory" name="factory" type="com.fulong.common.cache.CacheFactory"/>
        <logic:iterate name="factory" property="caches" id="cache" type="Class">
        <tr>
        <td class="leftmost"><bean:write name="cache" property="name"/></td>
        <td><%= cacheFactory.getCache(cache).getSize() %></td>
         <td><input type="button" value="清空" onclick="window.location='clearCache.do?class=<bean:write name="cache" property="name"/>'"/> </td>
        </tr>
        </logic:iterate>
        </table>
         </body>
</html>