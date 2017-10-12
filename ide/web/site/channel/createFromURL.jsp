<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加栏目</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
<base target="_self" />
<style type="text/css">
	input {width: 225px}
</style>
<script type="text/javascript">
	var siteFolder = window.dialogArguments.siteFolder;
	var oTemplate  = siteFolder.template;
	function selectFolder(oPath,oDisplayName){
		var folder = SiteDialog.selectFolder(oTemplate);
		if(folder!=null){
			oPath.value = folder.contextPath;
			oDisplayName.value = folder.displayName;
		}
	}
	function validateExist(name) {
		var oChannel = oTemplate.getChannel(siteFolder.contextPath+"/"+name+".jsp");
		return !oChannel.exists();
	}
	function validate(oForm){
		var name = oForm.elements["name"].value;
		if(!validateRequired(name)){
			alert("栏目名称不能为空！");
			return false;
		}
		if(!validateName(name)){
			alert("栏目名称只能包含字母、数字和下划线！");
			return fals;e
		}
		if(!validateLength(name,4,33)){
			alert("栏目名称长度必须在4和32之间！");
			return false;
		}
		if(!validateExist(name)){
			alert("栏目名称已存在！");
			return false;
		}
		var url = oForm.elements["url"].value;
		if(!validateRequired(url)){
			alert("url地址不能为空！");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<html:form action="insertFromURL" enctype="multipart/form-data" method="post" onsubmit="return validate(this)">
<html:hidden property="templateName"/>
<html:hidden property="type"/>
<html:hidden property="folderPath"/>
	<table width="100%" border="0" cellpadding="4" cellspacing="0">
		<tr>
			<td width="180" valign="middle">
				<html:img page="/images/paddingleft.jpg" module="/common" style="border:2px inset"></html:img>
			</td>
			<td valign="bottom">
				<div style="width: 100%;" class="errorTip indent"><html:errors property="url"/></div>
				<table cellpadding="5" cellspacing="0" border="0" width="100%">
					<tr>
						<td>栏目名称</td>
						<td><html:text property="name"/></td>
					</tr>
					<tr>
						<td>url地址</td>
						<td>
							<html:text property="url" style="ime-mode:disabled" title="只能输入半角的字母、数字、小数点和减号"/>
						</td>
					</tr>
					<tr>
			          <td >父文件夹</td>
			          <td>
			          	<html:hidden property="folderPath" styleId="folderPath"/>
			          	<html:text style="width: 156px;" property="parentDisplayName"  styleId="parentDisplayName" disabled="true"/>
			         	<input  type="button" class="btnMore"  onclick="selectFolder(document.getElementById('folderPath'),document.getElementById('parentDisplayName'))" value="选择...">
			          </td>
			        </tr>
				</table>
				<fieldset style="padding: 5px 5px 5px 5px; height: 200px;margin-bottom: 3px;">
					<legend>说明</legend>
					<div id="description">从url地址复制内容</div>
				</fieldset>
			</td>
		</tr>
	</table>
	<div class="operation">
		<button id="btnOk" type="submit">确定</button>
		<button id="btnCancel" onclick="window.close();">取消</button>
	</div>
</html:form>
</body>
</html>
