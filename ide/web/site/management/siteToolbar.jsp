<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>网站管理工具栏</title>
<base href="<fulong:rewriteURL page='/index.do' module='/site'/>"/>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/toolbar.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
<script type="text/javascript">
SITE.EditorPath	= 	"<html:rewrite page='/' module='/site'/>";
SITE.CommonPath	= 	"<html:rewrite page='/' module='/common'/>";
SITE.SitePath = "<html:rewrite page='/' module='/site'/>";
SITE.SitesPath = "<html:rewrite page='/sites' module=''/>/";
SITE.ServerPort = "<%=request.getServerPort()%>";
SITE.SiteURL = '<html:rewrite module="/site" page="/management/sites.do"/>?timeStamp='+new Date().getTime();
</script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/siteconfig.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitetoolbar.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitebuttons.js"/>"></script>
<script language="Javascript" type="text/Javascript">
	window.onload = function(){
		SITEToolbar.Start(document.getElementById('DLGToolbar20'),'site');
		window.top.SITE = SITE;
	}
</script>
</head>
<body>
<div id="DLGToolbar20"></div>
</body>
</html>


