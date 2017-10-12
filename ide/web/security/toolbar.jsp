<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工具栏</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/xtree.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/toolbar.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="umconfig.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/umtoolbar.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/um.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/umleft.js"></script>
<script language="Javascript" type="text/Javascript">
	UMToolbar.EditorPath='<html:rewrite page="/" module="/security"/>';	
	UM.CommonPath = '<html:rewrite page="/" module="/common"/>';
</script>
<script language="Javascript" type="text/Javascript" src="classes/umbuttons.js"></script>
<script language="Javascript" type="text/Javascript">
function doInit(){
	UMToolbar.Start(document.getElementById("DLGToolbar20"));
}
</script>
</head>
<body onload="doInit()">
<div id="DLGToolbar20"></div>
</body>
</html>

