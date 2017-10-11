<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>图片压缩服务</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css"	href="/components/service/styles.css">
<script language="Javascript" type="text/Javascript"	src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript"	src="/ide/service/scripts.js.jsp""></script>
<script language="Javascript" type="text/Javascript"	src="/ide/cms/classes/cmsdialog.js""></script>
<script language="Javascript" type="text/Javascript"	src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript"	src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	var definitionID = window.dialogArguments.definitionID;
	
	function doInit(){
		config.populateForm(configForm);
	}
	function ok(){
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
		<td class="pannelDiv" valign="top"><select id="pannelSelect"
			name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
			<option value="0">基本信息</option>
		</select></td>
		<td id="tdFieldsets" valign="top" align="center">
		<form name="configForm">
		<fieldset class="fieldPanel">
		<div style="height: 200px;">
		<table>
			<tr>
				<td>大图片<td>
				<td>
					<input type="hidden" name="srcProp" />
				    <input type="text" name="srcPropName" readonly="readonly"  style="width: 150px;"/>
				</td>
				<td>
					<button type="button" onclick="selectProperty(document.getElementById('srcProp'),document.getElementById('srcPropName'),[0,1,2,3,4,5,6,7,9,10])">选择...</button>
				</td>
			</tr>
			<tr>
				<td>小图片<td>
				<td>
					<input type="hidden" name="destProp" />
				    <input type="text" name="destPropName" readonly="readonly"  style="width: 150px;"/>
				</td>
				<td>
					<button type="button" onclick="selectProperty(document.getElementById('destProp'),document.getElementById('destPropName'),[0,1,2,3,4,5,6,7,9,10])">选择...</button>
				</td>
			</tr>
			<tr>
				<td>小图片宽<td>
				<td colspan="2">
				    <input type="text" name="width" style="width: 150px;"/>
			</tr>
			<tr>
				<td>小图片高<td>
				<td colspan="2">
				    <input type="text" name="height" style="width: 150px;"/>
				</td>
			</tr>
			<tr>
				<td>压缩质量<td>
				<td colspan="2">
				    <input type="text" name="quality" style="width: 150px;"/>%
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
