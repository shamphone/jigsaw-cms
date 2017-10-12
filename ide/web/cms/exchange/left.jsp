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
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/xtree/xtree.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/xtree.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="classes/cmsleft.js"></script>
<xml id="definitionsXML" src="../definition/inheritDefinitionsXML.do?ID=exchange-root&timestamp=<%= System.currentTimeMillis() %>" ></xml>
<script language="Javascript" type="text/Javascript">
	CMSTree.basePath = '<html:rewrite page="/" module="/cms" />';
	CMSTree.rootDefinitionID = 'exchange-root';
	function doInit(){
		CMSTree.loadTree(document.body);
	}
</script>
</head>
<body scroll="yes" style="border: 0px; margin: 0px; padding: 0px;" onload="doInit()">
</body>
</html>

