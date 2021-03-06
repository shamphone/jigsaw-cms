<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>最后期限计算</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
		var config = window.dialogArguments.parameters;
		function doInit(){
			config.populateForm(configForm);
		}
		function ok(){
			/**
			 * luobin modified in 2010-3-17
			 * bug
			 * 修改目的：刷新间隔（小时）没有输入限制。
			 * 修改描述：刷新间隔（小时）只能是正数
			 */
			if(!validatePositive(configForm.deadlinetime.value)){
				alert("刷新间隔必须是正数！");
				return;
			}
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
		function validatePositive(time){
			var TIME_PATTERN = /^\d+(\.\d+)?$/;
			if(!TIME_PATTERN.test(time)){
				return false;
			}
			return true;
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
			<input type="hidden" name="deadlineCategoryID" />
			<input type="text" name="deadlineCategoryName" readonly="readonly" />
				</td>
				<td>
			<button type="button" onclick="selectCategory(document.getElementById('deadlineCategoryID'),document.getElementById('deadlineCategoryName'))">选择...</button>
			</td>
			</tr>
			<tr>
			<td>起点时间属性<td>
			<td>
			<input type="hidden" name="startpropID" />
			<input type="text" name="startpropName" readonly="readonly" />
				</td>
				<td>
			<button type="button" onclick="selectProperty(document.getElementById('deadlineCategoryID').value,document.getElementById('startpropID'),document.getElementById('startpropName'),['5'])">选择...</button>
			</td>
			</tr>
			<tr>
			<td>终止时间属性<td>
			<td>
			<input type="hidden" name="deadlinepropID" />
			<input type="text" name="deadlinepropName" readonly="readonly" />
				</td>
				<td>
			<button type="button" onclick="selectProperty(document.getElementById('deadlineCategoryID').value,document.getElementById('deadlinepropID'),document.getElementById('deadlinepropName'),['5'])">选择...</button>
			</td>
			</tr>
			<tr>
			<td>期限属性<td>
			<td>
			<input type="hidden" name="deadreducepropID" />
			<input type="text" name="deadreducepropName" readonly="readonly" />
				</td>
				<td>
			<button type="button" onclick="selectProperty(document.getElementById('deadlineCategoryID').value,document.getElementById('deadreducepropID'),document.getElementById('deadreducepropName'))">选择...</button>	
			</td>
			</tr>
			<tr>
			<td>刷新间隔(小时)<td>
			<td>
			<input type="text" name="deadlinetime" />
				</td>
				<td>
				
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
