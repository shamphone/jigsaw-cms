<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
  	<head>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			var ret= new Object();
			ret.ID = '<bean:write name="channel" property="ID"/>';
			ret.name = '<bean:write name="channel" property="name"/>';
			ret.type = '<bean:write name="channel" property="type"/>';
			ret.displayName	= '<bean:write name="channel" property="displayName"/>';
			ret.path = '<bean:write name="path"/>';
			ret.state = '<bean:write name="channel" property="state"/>';
		    window.parent.returnValue=ret;
	    	window.parent.close();
    	</script>
	</head>
</html>
