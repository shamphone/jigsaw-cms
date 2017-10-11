<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择投递的属性</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<link rel="stylesheet" type="text/css" href="styles.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript">
var parameters = window.dialogArguments.parameters;
function doInit(){
	var destID = parameters.getValue("deliverCategoryID");
	if(destID!=null){
		document.getElementById("deliverCategoryID").value = destID;
		document.getElementById("desCategoryName").value = CMSDialog.GetDefinitionName(destID);
	}
	if(!document.all){
		document.getElementById("toolbar").style.top = document.body.clientHeight-40;
		document.getElementById("toolbar").style.left = 105;
		document.getElementById("toolbar").style.textAlign = "center";
		document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
		document.body.style.overflow = "hidden"
	}	
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
function doOK(){
	parameters.setValue("deliverCategoryID",document.getElementById("deliverCategoryID").value);
	parameters.setValue("desCategoryName",document.getElementById("desCategoryName").value);
	window.returnValue = parameters;
	window.close();
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
		<table cellpadding="2" width="100%"  cellspacing="0" border="0">
			<input type="hidden" name="deliverCategoryID" id="deliverCategoryID"/>
			<tr>
				<td>目标内容分类<td>
				<td><input type="text" name="desCategoryName"  id="desCategoryName" size="30" /></td>
				<td><button type="button" onclick="selectCategory(document.getElementById('deliverCategoryID'),document.getElementById('desCategoryName'))">选择...</button>			</td>
			</tr>
		</table>
		</fieldset>
		<div id="toolbar">
		<button type="button" onclick="doOK()">确定</button>
		<button type="button" onclick="window.close()">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>