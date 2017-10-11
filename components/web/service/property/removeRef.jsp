<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>解除引用关系</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	var definitionID = window.dialogArguments.definitionID;
	function doInit() {
		config.populateForm(configForm);
		var typeValue = config.getValues("typeValue");
		if(typeValue){
			document.getElementById("typeValue").value = typeValue;
		}
	}
	function ok() {
		config.updateFromForm(configForm);
		window.returnValue = config;
		window.close();
	}
	function selectCategory(objID,objName){
		var definition=CMSDialog.NodeDefinitionSelector();
		if(definition!=null){
		   if(definition.length>0){
		       for(var j=0;j<definition.length;j++){
		    	   objID.value=definition[j].ID;
		    	   objName.value=definition[j].name;
		       }
		     }
		 }
	}
	function selectProperty(definitionID,objID, objName,excludes) {
		if (definitionID == null) {
			alert("请选引用属性所在的分类！");
			return;
		}
		var result = CMSDialog.PropertyDefinitionSelector(definitionID,excludes);
		if (result != null) {
			objID.value = result.ID;
			objName.value = result.name;
		}
	}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
					<option value="0">解除引用设置</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center">
			<form name="configForm">
			<fieldset  class="fieldPanel">
			<div style="height: 200px;">
			<table>
				<tr>
					<td>引用属性所在的分类<td>
					<td>
						<input type="hidden" name="RefDef" />
					    <input type="text" name="RefDefName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectCategory(document.getElementById('RefDef'),document.getElementById('RefDefName'))">选择...</button>
					</td>
				</tr>
				<tr>
					<td>引用本大纲的属性<td>
					<td>
						<input type="hidden" name="RefDefPro" />
					    <input type="text" name="RefDefProName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('RefDef').value,document.getElementById('RefDefPro'),document.getElementById('RefDefProName'))">选择...</button>
					</td>
				</tr>
			</table>
				</div>
			</fieldset>
			</form>
			<div id="toolbar">
		    <button type="button" onclick="ok()" id="btnOK">确定</button>
		    <button type="button" onclick="window.close()" id="btnCancel">取消</button>
			</div>
			</td>
		</tr>
</table>
</body>
</html>
