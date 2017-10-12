<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="../style.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="../script.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript">
	var PMEditor = parent.frames["mainFrame"].PMEditor;
	var PMDefinition = PMEditor.definition;
	window.onload = function(){
		defForm["processID"].value = PMDefinition.getID();
		defForm["processName"].value = PMDefinition.getName();
		defForm["description"].value = PMDefinition.getDescription();
		var definitionID = PMDefinition.getParameters().getValue("definitionID");
		if(definitionID == null)
			definitionID = "no-properties-scheme";
		defForm("definitionID").value = definitionID;
		defForm("definitionName").value = CMSDialog.GetDefinitionName(definitionID);
		
	};
	function updateName(name){
		if ((trim(name) == "")) {
			alert("流程名称不能为空");
		} else {
			PMDefinition.setName(name);
		}
	};
	function updateDesc(desc){		
		PMDefinition.setDescription(desc);
	};


	function selectCategory(objID, objName) {
		var definition = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);
		if (definition != null) {
			objID.value = definition.ID;
			objName.value = definition.name;
			PMDefinition.getParameters().setValue("definitionID",definition.ID);
		}
	}
</script>
</head>
<body><table cellpadding="2" cellspacing="0" border="0">
<form name="defForm"><input type="hidden" name="definitionID"/>
<tr>
<td valign="top">
<table  cellpadding="2" cellspacing="0" border="0">
<tr><th align="left">流程ID：</th><td align="left"><input type="text" name="processID" size="25" readonly="readonly" disabled="disabled"/> </td></tr>
<tr><th align="left">流程名称：</th><td align="left"><input type="text" name="processName" size="25" onblur="updateName(this.value)"/></td></tr>
<tr><th align="left">关联分类：</th><td align="left">
<input type="text" disabled name="definitionName" size="25" />
<button onclick="selectCategory(document.all('definitionID'),document.all('definitionName'))">选择...</button>
</td></tr>
</table>
</td>
<td valign="top">
<table cellpadding="2" cellspacing="0" border="0">
<tr><th align="left">流程描述：</th></tr>
<tr><td align="left"><textarea rows="4" cols="40" name="description" onchange="updateDesc(this.value)"></textarea>
</td></tr>
</table>
</td>
</tr>
</form>
</table>
</body>
</html>
