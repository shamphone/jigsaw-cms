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
  var oRet = {};
  oRet.newName='<bean:write name="newName" ignore="true"/>';
  oRet.newPath = '<bean:write name="newPath" ignore="true"/>';
  oRet.newId = '<bean:write name="newId" ignore="true"/>';
  oRet.parentId = '<bean:write name="parentId" ignore="true"/>';
  if (oRet.newName)
  	window.returnValue=oRet;
  window.close();
}
</script>
</head>
<body bgcolor="#ffffff" onload="doClose()">
</body>
</html>
