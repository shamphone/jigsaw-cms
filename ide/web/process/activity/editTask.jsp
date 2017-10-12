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
<link rel="stylesheet" type="text/css" href="../style.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="../script.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/service" page="/scripts.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript">
	var PMEditor = parent.frames["mainFrame"].PMEditor;
	var activity = PMEditor.selectedObject;
	window.onload = function(){
		actForm["ID"].value = activity.getId();
		actForm["name"].value = activity.getName();
		actForm["description"].value = activity.getDescription();
		if(activity.getServiceId()==null||activity.getServiceId()==""){
			actForm["service"].value = 0;
		}else{
			actForm["service"].value = activity.getServiceId();
		}
		
	};
	function updateName(name){
			activity.setName(name);
			//parent.frames["leftFrame"].PMTree.Tree.getSelected().setTitle(name);
	}
	function updateDesc(desc){
		activity.setDescription(desc);
	}
	function updateService(oSel){
		activity.setService(oSel.options[oSel.selectedIndex].value,oSel.options[oSel.selectedIndex].text);
	}

	function editService(){
		var serviceID = document.all.service.value;
		if(serviceID!=null&&serviceID!=""&&serviceID!="0"){
			var url= 'serviceDialog.jsp?timeStamp=' + new Date().getTime();
			url = "modalWrapper.jsp?title="+encodeURIComponent('服务配置')+"&url="+encodeURI('serviceDialog.jsp?timeStamp='+new Date().getTime());
			var apps= activity.element.getElementsByTagName("TaskApplication");
			var app;
			if(apps.length == 0){
				app =activity.element.getOwnerDocument().createElement("TaskApplication");
				activity.element.appendChild(app);              
			}else
				app = apps[0]; 
			var params = app.getElementsByTagName("ActualParameters");
			var param;
			if(params.length == 0){
				param =activity.element.getOwnerDocument().createElement("ActualParameters");
				app.element.appendChild(param);              
			}else
				param = params[0]; 
			var oObject = new Object();	
			oObject.serviceID = serviceID;
			oObject.serviceValue = param.xml;
			oObject.element = param;
			oObject.definitionID = activity.definition.getParameters().getValue("definitionID");
			oObject.parameters = new Parameters(param);
		    showDialog(url,oObject,450,360);	    
		}else{
			alert("请选择一个服务！");
		}
	}	
</script>
</head>
<body>
<table cellpadding="2" cellspacing="0" border="0">
<form name="actForm">
<tr>
<td valign="top">
<table cellpadding="2" cellspacing="0" border="0">
<tr><th>活动ID：</th></tr>
<tr><td><input type="text" name="ID" size="25" readonly="readonly" disabled="disabled"/></td></tr>
<tr><th>活动名称：</th></tr>
<tr><td><input type="text" name="name" size="25" onblur="updateName(this.value)"/></td></tr>
</table>
</td>
<td valign="top">
<table cellpadding="2" cellspacing="0" border="0">
<tr><th>关联活动：</th></tr>
<tr><td><select name="service" style="width:200px" onchange="updateService(this)">
	<option value="0">无</option>
		<logic:iterate id="category" name="manager" property='<%="moduleServiceContexts(components)"%>'>
			<optgroup label="<bean:write name='category' property='name'/>" title="<bean:write name='category' property='name'/>">
				<logic:iterate id="serviceConfig" name="category" property="serviceConfigs">
					<option value='<bean:write name="serviceConfig" property="id"/>' title="<bean:write name="serviceConfig" property="name"/>"><bean:write name="serviceConfig" property="name"/></option>
				</logic:iterate>
			</optgroup>
		</logic:iterate>
	<logic:notEmpty name="template">
		<bean:define id="template" name="template" type="com.fulong.longcon.site.SiteTemplate"/>
		<logic:iterate id="category" name="manager" property='<%="moduleServiceContexts("+template.getName()+")"%>'>
			<optgroup label="<bean:write name='category' property='name'/>" title="<bean:write name='category' property='name'/>">
				<logic:iterate id="serviceConfig" name="category" property="serviceConfigs">
					<option value='<bean:write name="serviceConfig" property="id"/>' title="<bean:write name="serviceConfig" property="name"/>"><bean:write name="serviceConfig" property="name"/></option>
				</logic:iterate>
			</optgroup>
		</logic:iterate>
	</logic:notEmpty>
	</select>&nbsp;
</td></tr>
<tr>
<td align="right"><button id="serviceConf" onclick="editService()">配置...</button></td>
</tr>
</table>
</td>
<td valign="top">
<table cellpadding="2" cellspacing="0" border="0">
<tr><th>活动描述：</th></tr>
<tr><td><textarea rows="4" cols="40" name="description" onblur="updateDesc(this.value)"></textarea></td></tr>
</table>
</td>
</tr>
</form>
</table>
</body>