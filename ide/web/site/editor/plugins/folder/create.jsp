<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建文件夹</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="property.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
<style type="text/css">
		input {width: 225px}
		body{over-flow:hidden}
</style>
<script type="text/javascript" language="javascript">
	var siteFolder = window.dialogArguments;
	var oTemplate  = siteFolder.template;
	window.onload = function(){
		document.getElementById("parentDisplayName").value = siteFolder.displayName;
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
	function checkName(name){
		if(!validateRequired(name)){
			alert("文件夹名称不能为空！");
			return false;
		}
		if(!validateName(name)){
			alert("文件夹名称只能包含字母、数字和下划线！");
			return false;
		}
		return true;
	}
  	function selectFolder(oDisplayName){
		var folder = SiteDialog.selectFolder(oTemplate);
		if(folder!=null){
			oDisplayName.value = folder.displayName;
			siteFolder = folder;
		}
	}
	function doOK(oForm) {
		var parent = siteFolder;
		var fileName = oForm.newName.value;
		if(!checkName(fileName)){
			oForm.newName.select();
			return false;
		}
		var folder = parent.getChild(fileName);
		if (folder.exists()) {
			alert("该名称已被使用，请更名后重试");
			oForm.newName.select();
			return false;
		}
		folder.create();
		var param = new Object();
		param.folder = folder;
		param.parent = siteFolder;
		window.returnValue = param;
		window.close();
		return true;
	}
</script>
<base target="_self" />
</head>
<body>
<form method="GET" action="#">
		<table width="100%" border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td width="180" valign="middle"><html:img
					page="/images/paddingleft.jpg" module="/common"
					style="border:2px inset"></html:img></td>
				<td valign="bottom">
				<div>
					<table  width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>文件夹名称</td>
							<td><input id="fileName" type="text" name="newName" size="32" maxlength="20" /></td>
						</tr>
						<tr>
				          <td >父文件夹</td>
				          <td>
				            <input style="width: 159px;" type="text" id="parentDisplayName" readonly="true"/>
				         	<input  type="button" class="btnMore"  onclick="selectFolder(document.getElementById('parentDisplayName'))" value="选择...">
				          </td>
				        </tr>
					</table>
				</div>
				<fieldset style="padding: 5px 5px 5px 5px; height: 180px;width: 100%;">
				<legend>说明</legend>
				<div id="description"></div>
				</fieldset>
				</td>
			</tr>
		</table>
<div class="operation">
<button class="commonbut" type="button"  onclick="doOK(this.form)">确定</button>
<button class="commonbut" type="button" onclick="window.close();">取消</button>
</div>
</form>
</body>
</html>

