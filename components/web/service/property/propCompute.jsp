<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>多属性值计算</title>
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
		var properties = config.getValues("properties");
		if(properties!=null){
			for(var i=0;i<properties.length;i++){
				var opt = document.createElement("option");
				opt.value = properties[i];
				opt.text = CMSDialog.GetPropertyDefinitionName(definitionID, properties[i]);
				configForm.elements('properties').options.add(opt);
			}
		}
	}
	function ok() {
		selectAll(configForm.elements('properties'));
		config.updateFromForm(configForm);
		window.returnValue = config;
		window.close();
	}
	function addPropertyDefinition(oSelect) {
		if (definitionID == null) {
			alert("请选择分类！");
			return;
		}
		var arr = CMSDialog.PropertyDefinitionSelector(definitionID, null ,[1,3,4,5]);
		if (arr != null) {
			var newOption = document.createElement("option");
			newOption.value = arr.ID;
			newOption.type = arr.type;
			newOption.text = arr.name;
			oSelect.add(newOption);
		}
	}
	function removePropertyDefinition(oSelect){
		deleteOption(oSelect);
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
					<td valign="top">计算属性<td>
					<td  colspan="2">
						 <table cellpadding="0" cellspacing="0" border="0">
			                  <tr>
				                  <td>
					                   <select multiple="multiple" name="properties" size="5" style="width:150px;">
					                   </select>
				                  </td>
				                  <td valign="top">
			                      	   <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements('properties'))">添 加</button><br/>
			                      	   <button type="button" class="commonbut" onclick="removePropertyDefinition(form.elements('properties'))">删 除</button><br/>
				                  		<button type="button" class="commonbut" onclick="upperShift(form.elements('properties'))">上 移</button><br/>
				                  		<button type="button" class="commonbut" onclick="lowerShift(form.elements('properties'))">下 移</button>
				                  </td>
			                  </tr>
		                </table>
					</td>
				</tr>
				<tr>
					<td>计算方式<td>
					<td colspan="2">
						<select name="computType" style="width: 150px;">
							<option value="plus">加</option>
							<option value="minus">减</option>
							<option value="avg">平均</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>时间单位<td>
					<td colspan="2"> 
					    <select name="dateUnit" style="width: 150px;">
							<option value="second">秒</option>
							<option value="minute">分</option>
							<option value="hour">时</option>
							<option value="date">天</option>
							<option value="date">月</option>
							<option value="year">年</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>目标属性<td>
					<td>
						<input type="hidden" name="destProp" />
					    <input type="text" name="destPropName" readonly="readonly"  style="width: 150px;"/>
					</td>
					<td>
						<button type="button" onclick="selectProperty(document.getElementById('destProp'),document.getElementById('destPropName'),[0,2,6,7,8,9,10])">选择...</button>
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
