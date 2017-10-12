<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>完成：请输入同步设置名称</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/xtree/xtree.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/xtree.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript">
	function doInit() {
		var CMS = window.dialogArguments;
		document.all("labelURL").innerHTML = CMS.remoteURL;
		document.all("labelRemoteDefinition").innerHTML = CMS.remoteDefinitionName;
		document.all("labelLocaleDefinition").innerHTML = CMS.localDefinitionName;
		exchangeForm.remoteURL.value = CMS.remoteURL;
		exchangeForm.remoteDefinition.value = CMS.remoteDefinitionID;
		exchangeForm.localDefinition.value = CMS.localDefinitionID;
		exchangeForm.mapping.value = CMS.mapping;
		exchangeForm.elements["name"].value = CMS.remoteName+"("+CMS.remoteDefinitionName+")-本地("+CMS.localDefinitionName+")";		
	}
	function doPrev(){
		window.location = "step3.mappings.jsp";
	}
</script>
</head>
<body onload="doInit()"><form action="create.do" method="POST" target="_self" name="exchangeForm">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr height="320px">
<td width="120px">&nbsp;</td>
<td valign="middle" align="left">
<table cellpadding="4" cellspacing="0" border="0" align="left">
<input type="hidden" name="remoteURL" /> 
<input type="hidden" name="remoteDefinition" />
<input type="hidden" name="localDefinition" />
<input type="hidden" name="mapping" />
	<tr>
		<th>远程地址：</th>
		<td><label id="labelURL"></label></td>
	</tr>
	<tr>
		<th>远程分类：</th>
		<td><label id="labelRemoteDefinition"></label></td>
	</tr>
	<tr>
		<th>本地分类：</th>
		<td><label id="labelLocaleDefinition"></label></td>
	</tr>
	<tr>
		<th>使用映射：</th>
		<td><label id="mappingName"></label></td>
	</tr>
	<tr>
		<th>设置名称：</th>
		<td><input type="text" size="40" name="name" /></td>
	</tr>
	
</table>
</td>
</tr>
</table>
		<div class="operation">
		<button id="btnInsert" onclick="doPrev();">上一步</button>
		<button onclick="this.form.submit()">完成</button>
		<button  id="btnCancel" onclick="window.close();">取消</button>
		</div></form>
</body>
</html>
