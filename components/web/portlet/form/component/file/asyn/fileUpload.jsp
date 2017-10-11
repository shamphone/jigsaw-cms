<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@page import="javax.portlet.PortletPreferences" %>
<html>
<head>
<script type="text/javascript" language="javascript">
function addAsynFile(oButton){
	oForm = oButton.form;
	if(oForm!=null){
		oForm.submit();
	}
}
<logic:present name="path">
var portletID = '<bean:write name="portletID" ignore="true"/>';
var path = '<bean:write name="path" ignore="true"/>';
var fileName = '<bean:write name="fileName" ignore="true"/>';
parent.addPathToSelect(portletID,path,fileName);
</logic:present>
function init(){
  if(document.body.clientWidth>60){
	document.getElementById("fileInput").style.width=(document.body.clientWidth - 60)+"px";
  }
}
</script>
<link href="" rel="stylesheet" type="text/css">
</head>
<body style="margin:0px;padding:0px;border-width:0px;scroll:no" onload="init()"><table width="100%" cellpadding="0" cellspacing="0" border="0">
<form action='/ide/cms/resource/addAsynFile.do' method="post" enctype="multipart/form-data">
<input type="hidden" name="portletID" value="<bean:write name="portletID" ignore="true"/>"/>
<tr>
<td><input type="file" name="file[0]"  id="fileInput"/></td>
<td><button id='<bean:write name="portletID" ignore="true"/>AddButton' type="button" onclick="addAsynFile(this)" style="font-size:12px;width:55px;height:23px;margin-left:5px;line-height:18px;">添加</button>
</td>
</tr></form>
</table>
</body>
</html>