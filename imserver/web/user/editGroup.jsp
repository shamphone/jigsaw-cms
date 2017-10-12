<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<body style="height: 100%; margin: 0; padding: 0;">
		
		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">修改公共联系人组的名称</font>
					</td>
					<td align="right" >
						<img src="<html:rewrite page="/images/viewrightimg.gif"/>" />
					</td>
				</tr>
			</table>
		</div>
	
		<br/>
		<html:messages id="message">
			<font color="#FF0000"><bean:write name="message"/></font>
		</html:messages>
		
		<html:form action="/modifyGroup.do" focus="groupName" method="post" onsubmit="return validateEditGroupForm(this);">
		
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="3" bgcolor="#F9FBFE">
				<tr>
					<td align="right"><font color="#173891">要修改的用户组</font><font color="#FF0000">*</font></td>
					<td>
						<html:select property="groupId" style="width: 156px;">
							<html:optionsCollection name="groups" label="name" value="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">修改后的组名称</font><font color="#FF0000">*</font></td>
					<td><html:text property="groupName" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">描述文字</font></td>
					<td><html:textarea property="groupDesc" cols="48" rows="5" /></td>
				</tr>
			</table>
		
			<table align="center" width="100%" cellspacing="0" cellpadding="8">
				<tr>
					<td align="center">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="submit" value="提交" class="blueButton">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="reset" name="Submit2" value="重置" class="blueButton">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" name="member" id="ButtonOK" value="取消">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	
	<html:javascript formName="/modifyGroup"/>
	
</html>
