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
<base href="<fulong:rewriteURL page='/index.do' module='/cms'/>"/>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/toolbar.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/service" page="/scripts.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cms.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cmsexporter.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cmsimporter.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cmsplugin.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cmstoolbarbutton.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/classes/cmstoolbaritems.js"/>"></script>
<script language="Javascript" type="text/Javascript">
	CMS.BasePath	= 	"<html:rewrite page='/' module='/cms'/>";
	CMS.EditorPath	=	"<html:rewrite page='/' module='/cms/manage'/>";
	CMS.ContextPath	= 	"<html:rewrite page='/' module=''/>";
	CMS.PluginsPath = CMS.EditorPath+'plugins/';
	CMS.repository = top.repository;
</script>	
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms/manage" page="/cmsconfig.js"/>"></script>
<script language="Javascript" type="text/Javascript">
function doInit(){
	CMS.Start(document.getElementById('DLGToolbar20'));
	window.top.CMS = CMS;
}
	//登出系统
	function doLogout() {
		window.top.location = "<html:rewrite page='/security/j_spring_security_logout' module=''/>";
	}
</script>
</head>
<body onload="doInit()">
<div id="DLGToolbar20"></div>
</body>
</html>

