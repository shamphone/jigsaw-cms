<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>活动属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="style.css" />
<script language="Javascript" type="text/Javascript" src="script.js"></script>
<script language="Javascript" type="text/Javascript">
	var PMEditor = parent.frames["mainFrame"].PMEditor;
	var PMDefinition = PMEditor.definition;
	window.onload = function(){
		defForm["processID"].value = PMDefinition.getID();
		defForm["processName"].value = PMDefinition.getName();
		defForm["description"].value = PMDefinition.getDescription();
	};
	function updateName(name){
			PMDefinition.setName(name);
			parent.frames["leftFrame"].PMTree.Tree.getSelected().setTitle(name);
	}
	function updateDesc(desc){
		PMDefinition.setDescription(desc);
	}
</script>
</head>
<body>
<table width="100%" cellpadding="2" cellspacing="0" border="0" datasrc="#oXML" datafld="Activity">
<tr><th>活动ID：</th></tr>
<tr><td><input type="text" name="activityID" datasrc="#oXML" datafld="Id" size="25"/></td></tr>
<tr><th>活动名称：</th></tr>
<tr><td><input type="text" name="activityName" datasrc="#oXML" datafld="Name" size="25"/></td></tr>
<tr><th>活动描述：</th></tr>
<tr><td><textarea rows="7" cols="20" datasrc="#oXML" datafld="Description" name="activityDescription" style="width:180px;"></textarea>
<label id="serviceLabel" style="display:none">选择服务:<br /></label>
<select name="service" id="service" style="display:none;width:200px;">
		<option value="0">无</option>
		<logic:iterate id="service" name="services">
			<option title='<bean:write name="service" property="value"/>' value='<bean:write name="service" property="key"/>'><bean:write name="service" property="value"/></option>
		</logic:iterate>
		</select>&nbsp;<button id="serviceConf" onclick="editService()" style="display:none">配置参数</button>
<input type="hidden" id="paramsInput" name="paramsInput" value=""/>
<input type="hidden" id="activityType" name="activityType">
<input type="hidden" id="Height" name="Height">
<input type="hidden" id="Width" name="Width">
<input type="hidden" id="XCoordinate" name="XCoordinate">
<input type="hidden" id="YCoordinate" name="YCoordinate">
</td></tr>
</table>
</body>