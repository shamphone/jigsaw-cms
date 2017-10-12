<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
<head>
<title>保存成功</title>
<script type="text/javascript" language="javascript">
	function doClose(){
		var ret = {};
		ret.newName = '<bean:write name="newName" ignore="true" />';
		ret.newPath = '<bean:write name="newPath"  ignore="true" />';
		ret.newId = '<bean:write name="newId"  ignore="true" />';
		ret.oldId = '<bean:write name="oldId"  ignore="true" />';
		ret.parentId = '<bean:write name="parentId"  ignore="true" />';
		if (ret.newName)
			window.returnValue = ret;
		window.close();
	}
</script>
</head>
<body bgcolor="#ffffff" onload="doClose()">
</body>
</html>
