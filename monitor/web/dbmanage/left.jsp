<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang='cn'>
<head>
<meta name="robots" content="noindex,nofollow">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>数据库维护</title>
<link rel="stylesheet" type="text/css" href="/ide/common/style/left.css" />
<link rel="stylesheet" type="text/css" href="/ide/common/xtree/xtree.css" />
<script language="Javascript" type="text/Javascript" src="/ide/common/xtree/xtree.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/xtree/tree.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/module/modules.js.jsp"></script>
<script type="text/javascript">
function doInit(){
	var oUL = document.all("tabnav");
	var tree = WebFXTree_ConvertUL(oUL);
	tree.text = "数据库优化与备份";
	document.all("navcontainer").innerHTML=tree.toString();
	CModuleCollection.render(document.all("modules"),"db");
}
</script>
<base target="mainright" />
</head>
<body onload="doInit()">
<div id="blockTitle">数据库维护</div>
<div id="navcontainer">
<ul id="tabnav">
<li><a href="dbuphold/optimizeDb.jsp" target="mainright">数据库一键优化</a></li>
<li><a href="dbuphold/ananlyzeAllTables.jsp" target="mainright">数据库表分析</a></li>
<li><a href="dbuphold/backUpDb.jsp" target="mainright">备份数据库到dmp</a></li>
</div>
<div id="modules" align="right"></div>
</body>
</html>