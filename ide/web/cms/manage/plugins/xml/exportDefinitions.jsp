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
<script type="text/javascript" src="<html:rewrite module="/cms" page="/classes/cmstree.js"/>"></script>
<script type="text/Javascript" language="Javascript">
var repository = window.dialogArguments;
var definition = repository.getNodeDefinition("no-properties-scheme");
var CMS;
function doInit(){
	CMS = new CMSTree(definition);
	document.all("container").innerHTML = CMS.toString();
	CMS.expand();	
};

WebFXTreeAbstractNode.prototype.getText = function(){
	return this.text;
};

function doOK(){
	window.close();
	var ret = new Object();
	var definitions = new Array();
	var selectedDefinitions=document.all("category");
	for(var i=0;i<selectedDefinitions.length;i++)
		if(selectedDefinitions[i].checked)
			definitions.push(selectedDefinitions[i].value);
	ret.definitions = definitions;
	ret.exportNodes = (document.all("exportNodes").checked);
	window.returnValue = ret;
	window.close();
}

WebFXTreeAbstractNode.prototype.getText = function(){
	if(this.element){
    	var id = this.element.getID();
    	var name = this.element.getName();
    	var text ='<input type="checkbox" style="height:12px;width:14px" name="category" ';
    	text +=' value="'+id+ '" id="'+id+ '" />';
    	text +='<label for="'+id+'">'+name+'</label>';
    	return text;
	}else{
    	return this.text;
	}
};
</script>
<style>
.opbutton{height:23px;width:70px;}
</style>
</head>
<body onload="doInit()">
<form action="#">
<table width="100%" cellpadding="2" cellspacing="0" border="0" id="tb">
	<tr>
	  <td width="180" valign="middle"><html:img page="/images/paddingleft.jpg" module="/common" style="border:2px inset"></html:img> </td>
		<td valign="top"><table width="100%" cellpadding="3" cellspacing="0" border="0">
		<tr><td>选择待导出的分类：</td></tr>
		<tr><td><div id="container" class="insetDiv" style="height:300px;width:230px"></div></td></tr>
		<tr><td><input type="checkbox" name="exportNodes" value="true"/><label for="exportNodes">导出分类下所有内容</label> </td></tr>
		</table>
		</td>
	</tr>
</table>
<div class="operation">
	<button type="submit" id="btnInsert" class="commonbut"  onclick="doOK()">确认</button>
	<button onclick="window.close();" class="commonbut">取消</button>
</div>
</form>
</body>
</html>
