<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>加入组 </title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ListTable.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>

<base target="_self" />
</head>
<body>
<form method="POST">
    <div style="height:220px;overflow-y:auto;background:white;">
	<table border="0" cellpadding="2" cellspacing="0" class="tableClass" width="100%">
	<logic:iterate id="ID" name="userIDs">
		<input type="hidden" name="IDs" value="<bean:write name='ID'/>"/>
	</logic:iterate>
	<logic:iterate id="group" name="groups">
	<tr>
	<td><input type="radio" name="groupsID" value="<bean:write name="group" property="ID" ignore="true"/>"/></td>
	<td style="width:355px;"><cms:node name="group" propertyName="title" ignore="true"/></td>
	</tr>
	</logic:iterate>
	</table>
	</div>
	<div class="operation">
	<button id="btnOk" type="button" onclick="check(this.form)" class="commonbut">确定</button>
	<button id="btnCancel" type="button" onclick="window.close();" class="commonbut">取消</button>
	</div>
</form>
<script type="text/javascript">
function check(form){
	var userIDs = "";
	var IDs = form.IDs;
	var groupsID = GetRadioValue(form.groupsID);
	if(groupsID==null||groupsID==""){
		alert("请选择一个组！");
		return false;
	}else{
		if(IDs.length!=null&&IDs.length>0){
			for(var i=0;i<IDs.length;i++){
				userIDs = userIDs + "&userID=" + IDs[i].value;
			}
		}else{
			userIDs = userIDs + "&userID=" + IDs.value;
		}
		var xmlhttp = getXMLHttpRequest();
		var urlTemp = '<html:rewrite module="" page="/security/user/doJoin.do"/>?toGroupID=' + groupsID + userIDs + '&timestamp='+Math.random();
		xmlhttp.open("Post",urlTemp,false);
		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
		xmlhttp.send(null);
		if(xmlhttp.responseText == "true"){
			alert("加入成功！");
			window.returnValue="true";
			window.close();
		}else{
			alert("加入失败！");
		}
	}
}
</script>
</body>
</html>
