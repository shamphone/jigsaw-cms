<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择服务</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script type="text/javascript">
	var selectedService = null;
	function doInit(){
		document.getElementById("categories").selectedIndex = 0;
		changeCategory(document.getElementById("categories"));
	}
	function changeCategory(oSelect){
		var comps = tdServices.getElementsByTagName("select");
		for(var i=0;i<comps.length;i++)
			comps[i].style.display = 'none';
		document.getElementById(oSelect.value).style.display="";
	}
	function changeService(oSelect){
		btnOK.disabled = false;
		selectedService = oSelect.value;	
		document.getElementById("descField").innerHTML = oSelect.options[oSelect.selectedIndex].getAttribute("desc");	
	}
	function ok(oForm){
		window.returnValue = selectedService;
		window.close();
	}
</script>
<base target="_self" />
</head>
<body onload="doInit()">
<table width="100%" cellpadding="2" cellspacing="2" border="0">
<tr>
<td>选择服务类别:</td>
<td>请选择服务:</td>
</tr>
<tr>
<td>
<select id="categories" name="categories" onchange="changeCategory(this)" multiple="multiple" size="12" style="width:160px;">
<logic:iterate id="category" name="categories">
<option value='<bean:write name="category" property="id"/>' title="<bean:write name="category" property="name"/>"><bean:write name="category" property="name"/></option>
</logic:iterate>
</select>
</td>
<td id="tdServices">
<logic:iterate id="category" name="categories">
	<select name="<bean:write name="category" property="id"/>" onchange="changeService(this)" multiple="multiple" size="12" style="display:none;width:260px">
		<logic:iterate id="service" name="category" property="serviceConfigs">
			<option value='<bean:write name="service" property="id"/>' title="<bean:write name="service" property="name"/>" desc='<bean:write name="service" property="description" ignore="true"/>'><bean:write name="service" property="name"/></option>
		</logic:iterate>
	</select>
</logic:iterate>
</td>
</tr>
</table>
<fieldset id="descField" style="width:100%;height:80px;margin:4px 4px 4px 4px;padding:4px 4px 4px 4px">
</fieldset>
    <div class="operation">
        <button type="button" onclick="ok()" class="commonbut" id="btnOK" disabled="disabled">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="btnCancel">取消</button>
      </div>
</body>
</html>
