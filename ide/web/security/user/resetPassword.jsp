<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fl"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>重置密码</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
</head>
<body style="text-align: center">
<form method="POST">
	<table class="sheetClass" width="100%" id="contentEditor">
		<logic:iterate id="ID" name="IDs">
		<input type="hidden" name="IDs" value="<bean:write name='ID'/>"/>
		</logic:iterate>
		<tr>
			<th>密码</th>
			<td><input name="password" type="password" maxlength="16" style="width:280px;"/></td>
		</tr>
		<tr>
			<th>确认密码</th>
			<td><input name="confirmPassword" type="password" maxlength="16" style="width:280px;"/></td>
		</tr>
	</table>
	<div class="operation">
	<button id="btnOk" type="button" onclick="doSumit(this.form)" class="commonbut">保存</button>
	<button id="btnCancel" type="button" onclick="window.close();" class="commonbut">取消</button>
	</div>
</form>

<script type="text/javascript">
function checkUsername(oform){
	var password = oform.password.value;
	if(password==null||password==""){
		alert("密码不能为空！");
		return false;
	}else{
		return true;
	}
}
function checkCPW(){
	var oForm = document.forms[0];
	var pw = oForm.password.value;
	var cpw = oForm.confirmPassword.value;
	if(pw!=cpw){
		alert("确认密码不正确！");
		return false;
	}else{
		return true;
	}
}
function doSumit(oform){
	if(checkUsername(oform)) {
	if(checkCPW()){
		var IDs = new Array();
		IDs = oform.IDs;
		var str;
		if (IDs[0] == null)
		{
			str='IDs='+IDs.value;
		}  
		else {
			str='IDs='+IDs[0].value;
			for (i=1;i<IDs.length;i++)
		    {
				str+='&IDs='+IDs[i].value;
			}
		}
		var password = oform.password.value;
		var xmlhttp = getXMLHttpRequest();
		var urlTemp = '<html:rewrite module="/security/user" page="/savePassword.do"/>?' + str +'&password=' + password ;
		xmlhttp.open("Post",urlTemp,false);
		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
		xmlhttp.send(null);
		if(xmlhttp.responseText == "true"){
			alert("修改成功！");
	    	window.returnValue="true";
			window.close();
		}
		}
	}
}
</script>
</body>
</html>
