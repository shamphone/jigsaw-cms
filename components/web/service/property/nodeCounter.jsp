<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>结点访问计数</title>
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
	}
	function ok() {
		config.updateFromForm(configForm);
		window.returnValue = config;
		window.close();
	}
	function selectProperty(objID, objName,excludes) {
		if (definitionID == null) {
			alert("请选择分类！");
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
					<option value="0">基本信息</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center">
			<form name="configForm">
			<fieldset class="fieldPanel">
			<div style="height: 200px;">
			<table>
				<tr>
					<td valign="top">所有访问量<td>
					<td>
						<input type="hidden" name="allPropId" />
					    <input type="text" name="allPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('allPropId'),document.getElementById('allPropName'),[0,2,6,7,8,9,10])">选择...</button>
					</td>
				</tr>
				<tr>
					<td>年访问量<td>
					<td>
						<input type="hidden" name="yearPropId" />
					    <input type="text" name="yearPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('yearPropId'),document.getElementById('yearPropName'),[0,2,6,7,8,9,10])">选择...</button>
					</td>
				</tr>
				<tr>
					<td>月访问量<td>
					<td>
						<input type="hidden" name="monthPropId" />
					    <input type="text" name="monthPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('monthPropId'),document.getElementById('monthPropName'),[0,2,6,7,8,9,10])">选择...</button>
					</td>
				</tr>
				<tr>
					<td>周访问量<td>
					<td>
						<input type="hidden" name="weekPropId" />
					    <input type="text" name="weekPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('weekPropId'),document.getElementById('weekPropName'),[0,2,6,7,8,9,10])">选择...</button>
					</td>
				</tr>
				<tr>
					<td>日访问量<td>
					<td>
						<input type="hidden" name="dayPropId" />
					    <input type="text" name="dayPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('dayPropId'),document.getElementById('dayPropName'),[0,2,6,7,8,9,10])">选择...</button>
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
