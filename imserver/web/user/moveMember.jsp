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
		
	<script type="text/javascript">
		function getUsersAndGroups() {
			var selectedGroupId = document.getElementsByTagName("*").groupId.value;
			document.getElementsByTagName("*").userId.length=0;
			
			document.location.href='moveMember.do?groupid='+selectedGroupId;
		}
	</script>
	
	<body style="margin: 0; padding: 0;">
		
		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">移动公共联系人</font>
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
		
		<html:form style="margin:0px; " action="/shiftMember.do">
		
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="3" bgcolor="#F9FBFE">
				<tr>
					<td align="right"><font color="#173891">用户移动前所在的组</font><font color="#FF0000">*</font></td>
					<td>
						<html:select property="groupId" style="width: 156px;" onchange="getUsersAndGroups()">
							<html:optionsCollection name="groups" label="name" value="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">选择用户</font><font color="#FF0000">*</font></td>
					<td>
						<html:select property="userId" style="width: 156px;">
							<html:optionsCollection name="users" label="name" value="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">用户移动后所在的组</font><font color="#FF0000">*</font></td>
					<td>
						<html:select property="newGroupId" style="width: 156px;" >
							<html:optionsCollection name="newGroups" label="name" value="id" />
						</html:select>
					</td>
				</tr>	
			</table>
			
			<table align="center" width="100%" cellspacing="0" cellpadding="8">
				<tr>
					<td align="center">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="submit" value="移动" class="blueButton">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" name="member" id="ButtonOK" value="取消">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>