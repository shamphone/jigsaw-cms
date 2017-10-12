<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
	<head>
    	<script type="text/javascript">
			var ret= new Object();
			ret.ID = '<bean:write name="template" property="ID"/>';
			ret.name = '<bean:write name="template" property="name"/>';
			ret.displayName	= '<bean:write name="template" property="displayName"/>';
			ret.path = '<bean:write name="path"/>';
			window.parent.returnValue = ret;
			window.parent.close();
		</script>
	</head>
</html>
