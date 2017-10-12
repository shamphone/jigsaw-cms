<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
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
<script language="Javascript" type="text/Javascript" src="classes/cms.js"></script>
<script language="Javascript" type="text/Javascript" src="cmsconfig.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/cmstoolbar.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/cmsbuttons.js"></script>
<script language="Javascript" type="text/Javascript">
	CMS.BasePath	= 	"<html:rewrite page='/' module='/cms'/>";
	CMS.EditorPath	=	"<html:rewrite page='/exchange' module='/cms'/>";
	CMS.ContextPath	= 	"<html:rewrite page='/' module=''/>";	
</script>	
<script language="Javascript" type="text/Javascript">
function doInit(){
	CMS.Start(document.getElementById('DLGToolbar20'));
	window.top.CMS = CMS;
}
</script>
</head>
<body onload="doInit()">
<div id="DLGToolbar20"></div>
</body>
</html>

