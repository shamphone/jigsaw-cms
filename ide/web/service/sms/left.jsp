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
<title>服务列表</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="classes/svtree.js"></script>
<script type="text/javascript">
<bean:parameter id="processID" name="id" value=""/> 
function doInit(){
	if(!document.all){
		document.getElementById("navcontainer").style.height = document.body.clientHeight-55;
	}
	SVTree.Start(document.getElementById("navcontainer"));
	CModuleCollection.render(document.getElementById("modules"),"service");
}
</script>
</head>
<body onload="doInit()">
<div id="blockTitle">服务管理</div>
<div id="navcontainer">
<ul id="processList">
	<li id='components'>
		<a>公共服务</a>
		<ul>
			<bean:define id="contexts" type="java.util.Collection" name="serviceManager" property='<%="moduleServiceContexts(components)"%>'/>
			<logic:iterate id="context" name="contexts">
				<li id="<bean:write name="context" property="id"/>">
					<a href="javascript:SVTree._OnSelect('<bean:write name="context" property="id"/>')"><bean:write name="context" property="name"/></a>
				</li>
			</logic:iterate>
		</ul>
	</li>
	<logic:iterate id="template" name="templates">
		<bean:define id="name" name="template" property="name" type="java.lang.String"></bean:define>
		<li id='<bean:write name="name"/>'><a><bean:write name="template" property='displayName' ignore="true"/></a>
			<ul>
				<bean:define id="contexts" type="java.util.Collection" name="serviceManager" property='<%="moduleServiceContexts("+name+")"%>'/>
				<logic:iterate id="context" name="contexts">
					<li id="<bean:write name="context" property="id"/>">
						<a href="javascript:SVTree._OnSelect('<bean:write name="context" property="id"/>')"><bean:write name="context" property="name"/></a>
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

