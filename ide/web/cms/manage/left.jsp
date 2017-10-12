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
<title>内容管理</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmstree.js"></script>
<script language="Javascript" type="text/Javascript">
var tree;
var repository = window.top.repository;
var definition = repository.getNodeDefinition("no-properties-scheme");
function doInit(){
	if(!document.all){
	 	document.getElementById("navcontainer").style.height = document.body.clientHeight-55;
	 }
	 CModuleCollection.render(document.getElementById("modules"),"cms");
	 tree = new CMSTree(definition);
	 document.getElementById("navcontainer").innerHTML = tree.toString();
	 tree.expand();
}
function CMSTree_OnClick(categoryID){
	tree.definitionID=categoryID;
	window.parent.frames['list'].location='search.do?definitionID='+categoryID;
    window.parent.frames['search'].location='toSearch.do?categoryID='+categoryID;
}
function refresh(){
	window.parent.frames[1].location.reload();
}
</script>
</head>
<body onload="doInit()">
<div id="blockTitle">数据管理</div>
<div id="navcontainer"></div>
<div id="modules" align="right"></div>
</body>
</html>

