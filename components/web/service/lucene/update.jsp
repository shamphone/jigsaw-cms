<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
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
				if(document.all){
					opt.text = CMSDialog.GetPropertyDefinitionName(definitionID, properties[i]);
					configForm.elements('properties').options.add(opt);
				}else{
					opt.textContent = CMSDialog.GetPropertyDefinitionName(definitionID, properties[i]);
					configForm.elements['properties'].add(opt,null);
				}
			}
		}
		if(!document.all){
			document.getElementById("toolbar").style.top = document.body.clientHeight-40;
			document.getElementById("toolbar").style.left = 105;
			document.getElementById("toolbar").style.textAlign = "center";
			document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
			document.body.style.overflow = "hidden"
		}
	}
	function ok() {
		selectAll(configForm.elements['properties']);
		config.updateFromForm(configForm);
		//config.setValue("sourcePropID",document.all("sourcePropID").value);	 
		window.parent.returnValue = config;
		window.parent.close();
	}
	function addPropertyDefinition(oSelect) {
		if (definitionID == null) {
			alert("请选择分类！");
			return;
		}
		var arr = CMSDialog.PropertyDefinitionSelector(definitionID, null ,[1]);
		if (arr != null) {
			var newOption = document.createElement("option");
			newOption.value = arr.ID;
			newOption.type = arr.type;
			if(document.all){
				newOption.text = arr.name;
				oSelect.add(newOption);
			}else{
				newOption.textContent = arr.name;
				oSelect.add(newOption,null);
			}
		}
	}
	function removePropertyDefinition(oSelect){
		deleteOption(oSelect);
	}
</script>
</head>
<body onload="doInit()">
			<form name="configForm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
			<option value="0">基本信息</option>
		</select></td>
		<td id="tdFieldsets" valign="top" align="center">
		<fieldset class="fieldPanel">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<td>不切分属性:</td>
				<tr>
				                  <td>
					                   <select multiple="multiple" name="properties" size="10" style="width:150px;">
					                   </select>
				                  </td>
				                  <td valign="top">
			                      	   <button type="button" class="commonbut" onclick="addPropertyDefinition(form.elements['properties'])">添 加</button><br/>
			                      	   <button type="button" class="commonbut" onclick="removePropertyDefinition(form.elements['properties'])">删 除</button><br/>
				                  </td>
			                  </tr>
				</td>
			</tr>
		</table>
		</fieldset>
		<div id="toolbar">
		<button type="button" onclick="ok()" type="button" id="btnOK">确定</button>
		<button type="button" onclick="window.close()" type="button" id="btnCancel">取消</button>
		</div>
		</td>
	</tr>	
</table>
			</form>
</body>
</html>
