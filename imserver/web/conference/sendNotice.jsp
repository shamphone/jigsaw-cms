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
		<input type="hidden" name="conferenceId" value="<%=request.getParameter("conferenceId")%>" />
		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">发送会议通知</font>
					</td>
					<td align="right" >
						<img src="<html:rewrite page="/images/viewrightimg.gif"/>" />
					</td>
				</tr>
			</table>
		</div>
		
		<html:form action="/sendNotice.do" focus="title" method="post" onsubmit="return validateSendNoticeForm(this);">
			
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="3" bgcolor="#F9FBFE">
				<tr bgcolor="#ECF4FC">
					<td align="right"><font color="#173891">通知标题</font><font color="#FF0000">*</font></td>
					<td align="left"><html:text property="title"/></td>
				</tr>
				<tr bgcolor="#ECF4FC">
					<td align="right"><font color="#173891">通知内容</font><font color="#FF0000">*</font></td>
					<td align="left"><html:textarea property="content" rows="4" cols="45"/></td>
				</tr>
				
			</table>
		
			<table align="center" width="100%" cellspacing="0" cellpadding="0">
				<tr bgcolor="#ECF4FC">
					<td colspan="2" height="45" bgcolor="#F9FBFE" align="center">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="submit" name="opea" value="发布">&nbsp;&nbsp;&nbsp;
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" id="ButtonOK" value="取消" >
					</td>
			   </tr>
			</table>
		</html:form>
	</body>
	<html:javascript formName="/sendNotice"/>	
</html>
