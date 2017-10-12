<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加公共联系人</title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/demos.css"/>">
		<script type="text/javascript" src="<html:rewrite page='/script/modal-message.js' />"></script>
		<link rel="stylesheet" href="<html:rewrite page='/css/modal-message.css' />" type="text/css"></link>
	</head>	
	
	<body style="margin: 0; padding: 0;">
		
		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">添加公共联系人</font>
					</td>
					<td align="right" >
						<img src="<html:rewrite page="/images/viewrightimg.gif"/>" />
					</td>
				</tr>
			</table>
		</div>
		
		<br/>
		<logic:messagesPresent>
			<ul>
				<html:messages id="message">
					<li><font color="#FF0000"><bean:write name="message"/></font></li>
				</html:messages>
			</ul>
		</logic:messagesPresent>
		
		<html:form action="/saveMember.do" method="post" onsubmit="return validateAddMemberForm(this);">
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="3" bgcolor="#F9FBFE">
				<tr>
					<td align="right"><font color="#173891">所属用户组</font><font color="#FF0000">*</font></td>
					<td><html:select property="groupId" style="width: 156px;">
							<html:optionsCollection name="groups" label="name" value="id" />
						</html:select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">用户名</font> <font color="#FF0000">*</font></td>
					<td><html:text property="accountName" size="24" maxlength="16"/></td>
					
				</tr>
				<tr>
					<td align="right"><font color="#173891">密码</font> <font color="#FF0000">*</font></td>
					<td><html:password property="password" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">确认密码</font> <font color="#FF0000">*</font></td>
					<td><html:password property="confirmPassword" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">姓</font> <font color="#FF0000">*</font></td>
					<td><html:text property="lastName" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">名</font> <font color="#FF0000">*</font></td>
					<td><html:text property="firstName" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">电子邮件</font> <font color="#FF0000">*</font></td>
					<td><html:text property="email" size="24" maxlength="50" /></td>
				</tr>
			</table>
			
			<table align="center" width="100%" cellspacing="0" cellpadding="4">
				<tr>
					<td height="45" align="center">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" name="submit" type="submit" class="blueButton" value="确定"> 
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" class="blueButton" type="reset" name="Submit2" value="重置"> 
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" name="member" id="ButtonOK" value="取消" >
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	
	<html:javascript formName="/saveMember"/>
	
</html>
