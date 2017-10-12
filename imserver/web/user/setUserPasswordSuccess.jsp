<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/demos.css"/>">
		<title>修改密码成功</title>
	</head>
	
	<body>
		
		<h1>修改密码成功！</h1>
		
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">
					<html:link href="setUserPassword.do"><font color="#0000FF">继续修改其他成员的密码</font></html:link>
				</td>
			</tr>
		</table>
		
		<br>
		
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">
					<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" id="ButtonOK" value="关闭" >
				</td>
			</tr>
		</table>
		
	</body>
</html>