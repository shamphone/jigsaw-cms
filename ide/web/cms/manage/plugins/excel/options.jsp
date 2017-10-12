<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>导出设置</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script type="text/javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script type="text/javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script type="text/javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script type="text/javascript" src="<html:rewrite module="/cms" page="/classes/pmstree.js"/>"></script>
<script type="text/Javascript" language="Javascript">
var PMS;
function doInit(){
	var definitionID = window.dialogArguments.definitionID;
	PMS = new PMSTree(definitionID);
	document.getElementById("container").innerHTML =PMS.toString();	
};
PMS_Select= function(id){
	var node = PMSTreeNodes[id];
	PMS.selectedValue.name = node.getDisplayName();
	PMS.selectedValue.ID = id;	
};
/**
 * 添加全部的节点
 */
function addAll(oSelect){
	var nodes = PMS.childNodes;
	for(var i=0;i<nodes.length;i++){
		addOption(oSelect, nodes[i].uuid, nodes[i].getDisplayName());
	}
};

function doOK(){
	var ret = new Object();
	var properties = new Array();
	var selectedColumns=document.getElementById("selectedColumns");
	for(var i=0;i<selectedColumns.options.length;i++)
		properties[i]=selectedColumns.options[i].value;
	ret.properties = properties;
	ret.selected = (document.getElementById("selectedNodes").checked);
	window.returnValue = ret;
	window.close();
}
</script>
<style>
.opbutton{height:23px;width:70px;}
</style>
</head>
<body onload="doInit()">
<form action="#">
<table width="100%" cellpadding="2" cellspacing="0" border="0" id="tb">
	<tr bgcolor="buttonface">
		<th width="100px" align="left">从分类属性:</th>
		<th align="left">到Excel列:</th>
	</tr>
	<tr>
		<td><div id="container" class="insetDiv" style="height:200px;width:230px"></div></td>
		<td><select size="15" style="height:200px;width:230px" id="selectedColumns"></select></td>
	</tr>
	<tr>
	<td colspan="2" height="30px" align="center">
	<button type="button" class="opbutton" onclick="addAll(selectedColumns)">全部添加</button>
	<button type="button" class="opbutton" onclick="removeAll(selectedColumns)">全部删除</button>
	<button type="button" class="opbutton" onclick="addOption(selectedColumns,PMS.selectedValue.ID,PMS.selectedValue.name)">添加</button>
	<button type="button" class="opbutton" onclick="deleteOption(selectedColumns)">删除</button>
	<button type="button" class="opbutton" onclick="upperShift(selectedColumns)">上移</button>
	<button type="button" class="opbutton" onclick="lowerShift(selectedColumns)">下移</button>
	</td>
	</tr>
</table>
<table width="100%" cellpadding="2" cellspacing="0" border="0" id="tb">
<tr><td>导出选项:</td></tr>
<tr><td><input type="radio" name="range" checked="checked" value="selectedNodes" id="selectedNodes"/><label for="selectedNodes">导出选中的内容</label> </td></tr>
<tr><td><input type="radio" name="range" checked="checked" value="allNodes" id="allNodes"/><label for="allNodes">导出分类下所有内容</label></td></tr>
</table>
<div class="operation">
	<button type="button" id="btnInsert" class="commonbut"  onclick="doOK()">确认</button>
	<button type="button" onclick="window.close();" class="commonbut">取消</button>
</div>
</form>
</body>
</html>
