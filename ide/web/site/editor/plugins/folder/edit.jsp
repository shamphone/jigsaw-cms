<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>重命名</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="property.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script type="text/javascript" language="javascript">
	var folder = window.parent.dialogArguments.folder;
	function doInit(){
		document.getElementsByName("newName")[0].value = folder.getName();
	}
	function checkFileName($input) {
		var fileName = $input.value = $input.value.trim();
		if (!fileName) {
			alert("文件名不能为空");
			return null;
		}
		var reg1 = /^[^\\\/:\*\?\"<>\|&#]+$/;
		if (!reg1.test(fileName)) {
			alert("文件名不能包含以下字符：\n \\ / : * ? < > | & #");
			return null;
		}
		var reg2 = /^[^\.]+.*$/;
		if (!reg2.test(fileName)) {
			alert("文件名不能为空");
			return null;
		}
		var reg3 = /^(.+?)\.+$/;
		var temp = fileName.match(reg3);
		if (temp)
			fileName = temp[1];
		return fileName;
	};
	function doOK(oForm) {
		var oldFolder = folder;
		var fileName = checkFileName(oForm.newName);
		if (fileName == null) {
			oForm.newName.select();
			return false;
		}
		var newFolder = oldFolder.getParent().getChild(fileName);
		if (newFolder.exists()) {
			alert("该名称已被使用，请更名后重试");
			oForm.newName.select();
			return false;
		}
		oldFolder.rename(fileName);
		window.parent.returnValue = oldFolder;
		window.parent.close();
		return true;
	}
</script>
<base target="_self" />
</head>
<body onload="doInit()" style="over-flow:hidden">
<form method="GET" action="#">
<table style="padding-left: 20px;" width="100%" cellpadding="2" cellspacing="2" border="0">
	<tr>
		<td>新建文件夹名称:</td>
	</tr>
	<tr>
		<td><input id="fileName" type="text" name="newName" size="32" maxlength="20" /></td>
	</tr>
</table>
<div class="operation">
<button class="commonbut" id="submit" type="button" onclick="doOK(this.form)">确定</button>
<button class="commonbut" onclick="window.parent.close();">取消</button>
</div>
</form>
</body>
</html>

