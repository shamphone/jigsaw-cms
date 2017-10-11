<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置复制属性</title>
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
var definitionID = window.dialogArguments.definitionID;
function doInit(){
	disablePropSelect(useID);
	var property = config.getValue("sourcePropID");
	if(property!=null){
		document.all("sourcePropID").value = property;
		document.all("sourcePropName").value = CMSDialog.GetPropertyDefinitionName(definitionID, property);
	}
	property = config.getValue("destinationPropID");
	if(property!=null){
		document.all("destinationPropID").value = property;
		document.all("destinationPropName").value = CMSDialog.GetPropertyDefinitionName(definitionID, property);
	}	
	var useID = ("true" == config.getValue("IDprop"));
	document.all("IDprop").checked = useID;
	document.all("definitionID").value = definitionID;
}

function ok(){
	config.setValue("definitionID",document.all("definitionID").value);	 
	config.setValue("sourcePropID",document.all("sourcePropID").value);	 
	config.setValue("destinationPropID",document.all("destinationPropID").value);
	config.setValue("IDprop",""+document.all("IDprop").checked ); 
	config.setValue("increase",""+document.all("increase").checked ); 
	window.returnValue = config;
	window.close();  
}

function doSelectSourceProperty(){
	var ret = CMSDialog.PropertyDefinitionSelector(definitionID);
	if(ret!=null){
		document.all("sourcePropID").value = ret.ID;
		document.all("sourcePropName").value = ret.name;
	}
}
function doSelectDestProperty(){
	var ret = CMSDialog.PropertyDefinitionSelector(definitionID);
	if(ret!=null){
		document.all("destinationPropID").value = ret.ID;
		document.all("destinationPropName").value = ret.name;
	}
}
function disablePropSelect(disabled){
	document.all("sourcePropID").disabled = disabled;
	document.all("sourcePropName").disabled = disabled;
	document.all("sourcePropName").value = "";
}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
			<option value="0">基本信息</option>
		</select></td>
		<td id="tdFieldsets" valign="top" align="center">
		<fieldset class="fieldPanel">
		<table cellpadding="2" cellspacing="0" border="0">
		<input type="hidden" name="definitionID" value=""/>
			<tr>
				<td>源属性</td>
				<td><input type="hidden" name="sourcePropID" /><input type="text" name="sourcePropName" readonly="readonly" /></td>
				<td>
				<button type="button" id="sourceButton" onclick="doSelectSourceProperty(definitionID,document.getElementById('sourcePropID'),document.getElementById('sourcePropName'))">选择...</button>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="IDprop" onclick="disablePropSelect(this.checked)"><label for="IDprop">使用内容ID</label></td>
				<td></td>
			</tr>
			<tr>
				<td>目标属性</td>
				<td><input type="hidden" name="destinationPropID" /><input type="text" name="destinationPropName"  readonly="readonly"/></td>
				<td>
				<button type="button" id="btnDest" onclick="doSelectDestProperty(definitionID,document.getElementById('destinationPropID'),document.getElementById('destinationPropName'))">选择...</button>
				</td>
			</tr>
			<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan="2"><input type="checkbox" name="increase">增量拷贝</td></tr>
		</table>
		</fieldset>
		<div id="toolbar">
		<button type="button" onclick="ok()" id="btnOK">确定</button>
		<button type="button" onclick="window.close()" id="btnCancel">取消</button>
		</div>
		</td>
	</tr>	
</table>
</body>
</html>