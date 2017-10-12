<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统管理</title>
<base target="content">
<link rel="stylesheet" type="text/css" href="css/left.css" />
<script language="Javascript" type="text/Javascript" src="/ide/common/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="js/ajax.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/module/modules.js.jsp"></script>
<script type="text/javascript">
function doInit(){
	var oUL = document.getElementById("tabnav");
	var tree = WebFXTree_ConvertUL(oUL);
	tree.text = "网站服务器管理";
	document.getElementById("navcontainer").style.height = document.body.clientHeight - 52;
	document.getElementById("navcontainer").innerHTML=tree.toString();
	CModuleCollection.render(document.getElementById("modules"),"system");
}
</script>

</head>
<body onload="doInit()">
<div id="blockTitle">系统监控</div>
<div id="navcontainer">
<ul id="tabnav">
	<li><a class="${navTabApps}" href="<c:url value="/system/probe/appsummary.htm"/>?webapp=${pageContext.request.contextPath}&size=${param.size}"><spring:message	code="probe.jsp.menu.applications" /></a>
	<ul>
		<li><a class="${appTabSessions}" href="<c:url value="/system/probe/sessions.htm"/>?webapp=${pageContext.request.contextPath}&size=${param.size}"><spring:message	code="probe.jsp.app.nav.sessions" /></a></li>
		<li><a class="${appTabAttributes}" href="<c:url value="/system/probe/appattributes.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.attributes" /></a></li>
		<li><a class="${appTabJSPs}" href="<c:url value="/system/probe/app/jsp.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.jsps" /></a></li>
		<li><a class="${appTabResources}" href="<c:url value="/system/probe/resources.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.resources" /></a></li>
		<li><a class="${appTabContextDescriptor}" href="<c:url value="/system/probe/adm/viewcontextxml.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.contextxml" /></a></li>
		<li><a class="${appTabDeploymentDescriptor}" href="<c:url value="/system/probe/app/viewwebxml.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message	code="probe.jsp.app.nav.webxml" /></a></li>
		<li><a class="${appTabServlets}" href="<c:url value="/system/probe/appservlets.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.servlets" /></a></li>
		<li><a class="${appTabFilters}" href="<c:url value="/system/probe/appfilters.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.filters" /></a></li>
		<li><a class="${appTabInitParams}" href="<c:url value="/system/probe/appinitparams.htm"/>?webapp=${pageContext.request.contextPath}"><spring:message code="probe.jsp.app.nav.initParams" /></a></li>
	</ul>
	</li>
	<li><a class="${navTabDatasources}" href="<c:url value="/system/probe/resources.htm?webapp=${pageContext.request.contextPath}"/>"><spring:message code="probe.jsp.menu.datasources" /></a></li>
	<li><a class="<c:out value="${navTabLogs}"/>" href="<c:url value="/system/probe/logs/index.htm"/>"><spring:message code="probe.jsp.menu.logs" /></a></li>
	<li><a class="<c:out value="${navTabThreads}"/>" href="<c:url value="/system/probe/threads.htm"/>"><spring:message code="probe.jsp.menu.threads" /></a></li>
	<li><a class="<c:out value="${navTabCluster}"/>" href="<c:url value="/system/probe/cluster.htm"/>"><spring:message code="probe.jsp.menu.cluster" /></a></li>
	<li><a class="<c:out value="${navTabSystem}"/>" href="<c:url value="/system/probe/sysinfo.htm"/>"><spring:message code="probe.jsp.menu.sysinfo" /></a>
	 <ul>
        <li><a class="<c:out value="${systemTabMemory}"/>" href="<c:url value="/system/probe/memory.htm"/>"><spring:message code="probe.jsp.decorator.system.memory"/></a></li>
        <li><a class="<c:out value="${systemTabSysProps}"/>" href="<c:url value="/system/probe/sysprops.htm"/>"><spring:message code="probe.jsp.decorator.system.props"/></a></li>
        <li><a class="<c:out value="${systemTabOsInfo}"/>" href="<c:url value="/system/probe/adm/osinfo.htm"/>"><spring:message code="probe.jsp.decorator.system.os"/></a></li>
        <li><a class="<c:out value="${systemTabWrapper}"/>" href="<c:url value="/system/probe/wrapper.htm"/>"><spring:message code="probe.jsp.decorator.system.wrapper"/></a></li>
    </ul>
	</li>
	<li><a class="<c:out value="${navTabStatus}"/>" href="<c:url value="/system/probe/status.htm"/>"><spring:message code="probe.jsp.menu.status" /></a></li>
	<li><a class="<c:out value="${navTabCharts}"/>" href="<c:url value="/system/probe/charts.htm"/>"><spring:message code="probe.jsp.menu.charts" /></a></li>
	<li><a class="<c:out value="${navTabCharts}"/>" href="/ide/cms/cache.do">缓存管理</a></li>
	<li><a class="<c:out value="${navTabQuickCheck}"/>" href="<c:url value="/system/probe/adm/quickcheck.htm"/>"> <spring:message code="probe.jsp.menu.quickcheck" /></a></li>
</ul>
</div>
<div id="modules" align="right"></div>
</body>
</html>