<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>CooLink即时通讯模块后台管理系统</title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/demos.css"/>">
	</head>
	
	<body>
		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">CooLink即时通讯模块后台管理系统</font>
					</td>
					<td align="right" >
						<img src="<html:rewrite page="/images/viewrightimg.gif"/>" />
					</td>
				</tr>
			</table>
		</div>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="addGroup.do"><font color="#0000FF">添加公共联系人组</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="deleteGroup.do"><font color="#0000FF">删除公共联系人组</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="editGroup.do"><font color="#0000FF">修改公共联系人组名</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="moveGroup.do"><font color="#0000FF">移动公共联系人组</font></html:link>
			</tr>
		</table>
		
		<hr>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="addMember.do"><font color="#0000FF">添加公共联系人</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="deleteMember.do"><font color="#0000FF">删除公共联系人</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="setUserPassword.do"><font color="#0000FF">修改公共联系人密码</font></html:link>
			</tr>
		</table>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<html:link href="moveMember.do"><font color="#0000FF">移动公共联系人</font></html:link>
			</tr>
		</table>
		
		<hr>
		<br>
		<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">	
			<tr>
				<html:link href="setAdminPassword.do"><font color="#0000FF">修改管理员密码</font></html:link>
			</tr>
		</table>
	</body>
	
</html>