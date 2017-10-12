<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务配置</title>

</head>
<body>
<form id="oServiceForm" action="<html:rewrite page="/service/edit.do" module=""/>" target="_self" method="post">
<input type="hidden" id="serviceID" name="serviceID" value=""/>
<input type="hidden" id="serviceParam" name="serviceParam" value=""/>
</form>
<script type="text/javascript">
	var oObject1 = parent.window.oObject;
	var oForm = document.getElementById("oServiceForm");
	if(oForm!=null){
		if(oObject1!=null){
			document.getElementById("serviceID").value=oObject1.serviceID;
			document.getElementById("serviceParam").value=oObject1.serviceValue;
			oForm.submit();
		}
		
	}
</script>
</body>
</html>