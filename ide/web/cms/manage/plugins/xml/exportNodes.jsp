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
<script type="text/Javascript" language="Javascript">
function doOK(){
	var ret = new Object();
	ret.exportDefinition = (document.all("exportDefinition").checked);
	ret.exportAll =(document.all("exportAll").checked);
	ret.exportSelected =(document.all("exportSelected").checked);
	window.returnValue = ret;
	window.close();
}
function funcDefCheck()
{
	var foo=document.getElementById("exportAll");
	if(foo.checked==true)
	{
		document.getElementById("cbExportDef").checked=true;
	}
}

</script>
<style>
.opbutton{height:23px;width:70px;}
</style>
</head>
<body>
<form action="#">
<table width="100%" cellpadding="2" cellspacing="0" border="0" id="tb">
	<tr>
	  <td width="180" valign="middle"><html:img page="/images/paddingleft.jpg" module="/common" style="border:2px inset"></html:img> </td>
		<td valign="top">
		<table width="100%" cellpadding="3" cellspacing="0" border="0">
		<tr><td><input type="checkbox" name="exportDefinition" value="true" id="cbExportDef"><label for="exportDefinition">导出分类信息</label></td></tr>
		<tr><td><input type="radio" name="exportOption" value="all" id="exportAll" title="请务必同时选择“导出分类信息”选择框" onclick="funcDefCheck()"/><label for="exportAll">导出分类下所有内容</label> </td></tr>
		<tr><td><input type="radio" name="exportOption" value="selected" checked="checked" id="exportSelected"/><label for="exportSelected">导出选中的内容&nbsp;(<font color="red">若没有选中的内容则导出的是空文件</font>)</label> </td></tr>
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
