<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请选择待加密的属性</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script language="Javascript" type="text/Javascript">
var config = window.dialogArguments.parameters;
function doInit(){
	var property = config.getValue("property");
	if(property!=null){
		document.all("property").value = property;
		document.all("propertyName").value = CMSDialog.GetPropertyDefinitionName(window.dialogArguments.definitionID, property);
	}
}

function ok(){
	config.setValue("property",document.all("property").value); 
	window.returnValue = config;
	window.close();  
}

function doSelectProperty(){
	var ret = CMSDialog.PropertyDefinitionSelector(window.dialogArguments.definitionID,window.dialogArguments.definitionID);
	if(ret!=null){
		document.all("property").value = ret.ID;
		document.all("propertyName").value = ret.name;
	}
}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
			<option value="0">基本设置</option>
		</select></td>
		<td id="tdFieldsets" valign="top" align="center">
		<fieldset class="fieldPanel">
		<table width="100%" border="0" cellpadding="2" cellspacing="0"><input type="hidden" name="property" />
		<tr>
		<td>加密属性</td>
		<td><input type="text" name="propertyName" readonly="readonly" size="30" /></td>
		<td><button type="button" class="commonbut" id="search" onclick='doSelectProperty()'>选择...</button></td>
		</tr>
		</table>	 		
		</fieldset><div id="toolbar">
		<button type="button" onclick="ok()" id="btnOK">确定</button>
		<button type="button" onclick="window.close()" id="btnCancel">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>