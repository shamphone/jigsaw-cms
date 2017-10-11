<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>推荐次数累加</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/service" page="/styles.css"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/service" page="/scripts.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script type="text/javascript" language="javascript">
		var config = window.dialogArguments.parameters;
		function doInit(){
			config.populateForm(configForm);
		}
		function ok(){
			config.updateFromForm(configForm);
			window.returnValue = config;
			window.close();   
		}
		function selectCategory(objID,objName){
			  var definition=CMSDialog.NodeDefinitionSelector();
				if(definition!=null){
			   if(definition.length>0){
			       for(var j=0;j<definition.length;j++){
			    	   objID.value=definition[j].ID;
			    	   objName.value=definition[j].name;
			       }
			     }
			  }
			}
		function selectProperty(categoryID,objID,objName,type){
			if(categoryID==null){
				alert("请选择分类！");
				return;
				}
			
			    var result=CMSDialog.PropertyDefinitionSelector(categoryID,'',type);
			    if(result!=null){
			    	objID.value=result.ID;
			    	objName.value=result.name;
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
			<div
				style="height: 200px;">
				<table>
				<tr>
			<td>内容分类<td>
			<td>
			<input type="hidden" name="CategoryID" />
			<input type="text" name="CategoryName" readonly="readonly" />
				</td>
				<td>
			<button onclick="selectCategory(document.getElementById('CategoryID'),document.getElementById('CategoryName'))">选择...</button>
			</td>
			</tr>
			<tr>
			<td>属性<td>
			<td>
			<input type="hidden" name="propID" />
			<input type="text" name="propName" readonly="readonly" />
				</td>
				<td>
			<button onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('propID'),document.getElementById('propName'),['3'])">选择...</button>
			</td>
			</tr>
			</table>
				</div>
			</fieldset>
			</form>
			<div id="toolbar">
		    <button onclick="ok()" id="btnOK">确定</button>
		    <button onclick="window.close()" id="btnCancel">取消</button>
			</div>
			</td>
		</tr>
</table>
</body>
</html>
