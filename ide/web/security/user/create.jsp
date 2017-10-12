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
<title>新增用户</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script type="text/javascript" src='<html:rewrite page="/script/common.js" module="/common"/>'></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
</head>
<style>
.pswsty{
	width:430px;
	width:383px\9;
	*width:383px;
	_width:383px;
}
</style>
<body style="text-align: center">
<html:form action="save.do" enctype="multipart/form-data" method="POST">
	<table class="sheetClass" width="100%" id="contentEditor">
		<html:hidden  property="nodeID" />
		<tr>
			<th>用户名</th>
			<td><html:text property="username" size="70" onblur="checkUsername(this.form)" maxlength="16"/></td>
		</tr>
		<tr>
			<th>密码</th>
			<td><html:password property="password"  styleClass="pswsty"/></td>
		</tr>
		<tr>
			<th>确认密码</th>
			<td><input name="confirmPassword" type="password" onblur="checkCPW()"  class="pswsty"/></td>
		</tr>
		<tr>
			<th>真实姓名</th>
			<td><html:text property="commonname" size="70" maxlength="16"/></td>
		</tr>
		<tr>
			<th>系统权限</th>
			<td>
			<html:checkbox property="roleDefs" value="cmsMgrs">数据管理</html:checkbox>&nbsp;&nbsp;
			<html:checkbox property="roleDefs" value="siteMgrs">网站管理</html:checkbox>&nbsp;&nbsp;
			<html:checkbox property="roleDefs" value="serviceMgrs">服务管理</html:checkbox>&nbsp;&nbsp;
			<html:checkbox property="roleDefs" value="processMgrs">流程管理</html:checkbox>&nbsp;&nbsp;
			<html:checkbox property="roleDefs" value="roleMgrs">组织管理</html:checkbox>
			</td>
		</tr>
	</table>
	<div class="operation">
	<button id="btnOk" type="button" onclick="doSumit(this.form)" class="commonbut">保存</button>
	<button id="btnCancel" type="button" onclick="_close();" class="commonbut">取消</button>
	</div>
</html:form>
<html:javascript formName="userForm"/>
<script type="text/javascript">
function checkUsername(oform){
	var username = oform.username.value;
			var xmlhttp = getXMLHttpRequest();
			var urlTemp = '<html:rewrite module="/common" page="/validateUnique.do"/>?categoryID=principal-scheme' + '&value=' + username + '&property=user-username&contentID=';
			xmlhttp.open("Post",urlTemp,false);
			xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
			xmlhttp.send(null);
			if(xmlhttp.responseText == "true"){
				return true;
			}else{
				alert("此用户名已存在！");
				return false;
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
	if(validateUserForm(oform)){
		if(checkUsername(oform)){
			if(checkCPW()){
				oform.submit();
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}else{
		return false;
	}
}
function _close(){
	if(document.all){
		window.close();
	}else{
		window.parent.close();
	}
}
</script>
</body>
</html>
