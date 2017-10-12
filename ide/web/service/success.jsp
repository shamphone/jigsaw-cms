<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="Javascript" type="text/Javascript" src="scripts.js.jsp"></script>
<title>保存成功</title>
<xml id="oXML" >
<bean:write name="serviceParam" ignore="true" filter="false"/>
</xml>
</head>
<body>
<script type="text/javascript">
	var oXML = document.getElementById("oXML");
	var root =window.dialogArguments.element;
	var old = root.getElementsByTagName("ActualParameters");
	if(old.length == 0) 
		root.appendChild(oXML.documentElement);
	else
		root.replaceChild(oXML.documentElement, old[0]);	
	window.returnValue = new Parameters(root);
	window.close();
</script>
</body>
</html>