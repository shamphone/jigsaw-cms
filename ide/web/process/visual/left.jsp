<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程设计</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmtree.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pms.js"></script>
<script type="text/javascript">
<bean:parameter id="processID" name="id" value=""/> 
function doInit(){
	document.getElementById("navcontainer").style.height = document.body.clientHeight - 52;
	PMTree.Start(document.getElementById("navcontainer")<logic:notEmpty name="processID">,'<bean:write name="processID"/>'</logic:notEmpty>);	
	CModuleCollection.render(document.getElementById("modules"),"process");	
}
</script>
</head>
<body onload="doInit()">
<div id="blockTitle">流程设计</div>
<div id="navcontainer">
<ul id="processList">
	<li id='components'>
		<a>公共流程</a>
		<ul>
			<bean:define id="workflows" type="java.util.Iterator" name="workflowService" property='<%="moduleDefinitions(components)"%>'/>
			<logic:iterate id="workflow" name="workflows">
				<li id="<bean:write name="workflow" property="id"/>">
					<a href="javascript:PMTree._OnSelect('<bean:write name="workflow" property="id"/>')"><bean:write name="workflow" property="name"/></a>
				</li>
			</logic:iterate>
		</ul>
	</li>
	<logic:iterate id="template" name="templates">
		<bean:define id="name" name="template" property="name" type="java.lang.String"></bean:define>
		<li id='<bean:write name="name"/>'><a><bean:write name="template" property='displayName' ignore="true"/></a>
			<ul>
			   
				<bean:define id="workflows" type="java.util.Iterator" name="workflowService" property='<%="moduleDefinitions("+name+")"%>'/>
				<logic:iterate id="workflow" name="workflows">
					<li id="<bean:write name="workflow" property="id"/>">
						<a href="javascript:PMTree._OnSelect('<bean:write name="workflow" property="id"/>')"><bean:write name="workflow" property="name"/></a>
					</li>
				</logic:iterate>
			</ul>
		</li>
	</logic:iterate>
</ul>
</div>
<div id="modules" align="right"></div>
</body>
</html>

