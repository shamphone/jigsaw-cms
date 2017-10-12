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
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/toolbar.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="pmsconfig.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pms.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmtoolbar.js"></script>
<script language="Javascript" type="text/Javascript">
	PMToolbar.EditorPath='<html:rewrite page="/" module="/process/visual"/>';	
</script>
<script language="Javascript" type="text/Javascript" src="classes/pmbuttons.js"></script>
<script language="Javascript" type="text/Javascript">
function doInit(){
	PMToolbar.Start(document.getElementById("DLGToolbar20"));
}
</script>
</head>
<body onload="doInit()">
<div id="DLGToolbar20" style="height: 22px;border-bottom: none;">
<span class="ToogleButton ToogleButtonDown" style="float:right;margin-right:10px" id="DesignMode" onclick="PMToolbar.Toogle(1)"><img src="icons/design.png"  border="0"><span>设计</span></span>  
<span class="ToogleButton" style="float:right;margin-right:10px" id="CodeMode" onclick="PMToolbar.Toogle(2)"><img src="icons/code.png"  border="0"><span>代码</span></span>  
</body>
</html>

