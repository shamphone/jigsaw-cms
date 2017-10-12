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
<title>显示模型管理</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/site" page="/style/manage.css"/>" />
<style type="text/css">
	 a:link { color:black; text-decoration: none} 
	 .selectedNode a:visited {color:white;background:highlight ;text-decoration: none} 
	 a:visited {color:black; text-decoration: none} 
	 a:hover { color: blue; text-decoration: underline} 
	 .selectedNode a:active  {color:white;background:highlight ;text-decoration: none}
</style>

<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>

<script language="Javascript" type="text/Javascript">
function showSites(){
	//document.getElementById("sitesDiv").className = "selectedNode";
	top.document.getElementById("toolBarFrame").src = '<html:rewrite module="/site/management" page="/siteToolbar.jsp"/>';
	top.document.getElementById("mainFrame").src = '<html:rewrite module="/site" page="/management/sites.do"/>';
}
function showTemplates(){
	//document.getElementById("sitesDiv").className = "";
	top.document.getElementById("toolBarFrame").src = '<html:rewrite module="/site/management" page="/templateToolbar.jsp"/>';
	top.document.getElementById("mainFrame").src='<html:rewrite module="/site/template" page="/list.do"/>?timeStamp=' + new Date().getTime();
}
window.onload = function(){
	document.getElementById("navcontainer").style.height = document.body.clientHeight-52;

	var container =  document.getElementById("navcontainer");
	var mangementList =  document.getElementById("mangementList");
	var Tree = WebFXTree_ConvertUL(mangementList);
	Tree.text = "显示模型";
	container.innerHTML=Tree.toString();
	CModuleCollection.render(document.getElementById("modules"),"site");
}
</script>
</head>
<body >
<div id="blockTitle">显示模型管理</div>
<div id="navcontainer" >
<ul id="mangementList">
	<li id="sitesDiv">
		<a href="javascript:showSites()">网站管理</a>
	</li>
	<li id="tempalteDiv">
		<a href="javascript:showTemplates()">模板管理</a>
	</li>
</ul>
</div>
<div id="modules" align="right"></div>
</body>
</html>

