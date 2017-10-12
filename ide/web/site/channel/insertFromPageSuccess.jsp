<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
  	<head>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script Language='Javascript' src='<html:rewrite page="/classes/site.js" module="/site"/>' type="text/javascript"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smresource.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smchannel.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smfolder.js"/>"></script>
		<script type="text/javascript">
			var name = '<bean:write name="channel" property="name"/>';
			var contextPath = '<bean:write name="channel" property="contextPath"/>';
			var displayName	= '<bean:write name="channel" property="displayName"/>';
			var type = '<bean:write name="channel" property="type"/>';

			var folderContextPath = '<bean:write name="channel" property="folder.contextPath"/>';

			var param = new Object();
			param.contextPath = contextPath
			param.folderContextPath = folderContextPath
			
		    window.parent.returnValue=param;
	    	window.parent.close();
    	</script>
	</head>
</html>
