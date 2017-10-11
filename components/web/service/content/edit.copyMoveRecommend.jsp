<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置服务</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script language="Javascript" type="text/Javascript">
var config = window.dialogArguments.parameters;
function doInit(){
	config.populateForm(configForm);
	var definitions = config.getValues("definitions");
	if(definitions!=null){
		for(var i=0;i<definitions.length;i++){
			var opt = document.createElement("option");
			opt.value = definitions[i];
			if(document.all){
				opt.text = CMSDialog.GetDefinitionName( definitions[i]);
				configForm.elements['definitions'].options.add(opt);
			}else{
				opt.textContent = CMSDialog.GetDefinitionName( definitions[i]);
				configForm.elements['definitions'].add(opt,null);
			}
		}
	}
}

function addDefinition(oSelect) {
	var definition=CMSDialog.NodeDefinitionSelector();
	if(definition!=null){
	   if(definition.length>0){
	       for(var j=0;j<definition.length;j++){
	    	    var newOption = document.createElement("option");
		   	    newOption.value = definition[j].ID;
		   	    if(document.all){
		   	    	newOption.text = definition[j].name;
					oSelect.add(newOption);
		   	    }else{
		   	    	newOption.textContent = definition[j].name;
					oSelect.add(newOption,null);
		   	    }
	       }
	     }
	 }
}

function removeDefinition(oSelect){
	deleteOption(oSelect);
}

function doOK(){
	selectAll(configForm.elements['definitions']);
	config.updateFromForm(configForm);
	window.returnValue = config;
	window.close();
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
		<fieldset class="fieldPanel">
			<form name="configForm">
				<table cellpadding="3" cellspacing="0" border="0">
					<tr>
						<td>操作类型<td>
						<td colspan="2">
							<select style="width:190px;" name="type">
								<option value="copy">复制</option>
								<option value="move">转移</option>
								<option value="recommend">推荐</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>目标内容分类<td>
						<td  colspan="2">
								 <table cellpadding="0" cellspacing="0" border="0">
					                  <tr>
						                  <td>
							                   <select multiple="multiple" name="definitions" size="5" style="width:190px;">
							                   </select>
						                  </td>
						                  <td valign="top">
					                      	   <button type="button" class="commonbut" onclick="addDefinition(form.elements['definitions'])">添 加</button><br/>
					                      	   <button type="button" class="commonbut" onclick="removeDefinition(form.elements['definitions'])">删 除</button><br/>
						                  </td>
					                  </tr>
				                </table>
							</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<div id="toolbar">
		<button type="button" onclick="doOK()">确定</button>
		<button type="button" onclick="window.close()">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>