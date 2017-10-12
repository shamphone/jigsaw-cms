<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>视频会议后台管理系统</title>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table align="center" width="100%" border="0px" cellpadding="0px"
	cellspacing="0px" bgcolor="WHITE">
	<tr>
		<td width="100%"><iframe src="header.do"
			frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
			width="100%" height="60px"></iframe> <br />
		</td>
	</tr>
	<tr>
		<td align="center">
			<table width="850" height="370" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="162" valign="top" bgcolor="#d1e3fb">
				<table width="162" border="1" cellspacing="0" cellpadding="0"
					bordercolordark="#FFFFFF" bordercolorlight="#000099">
					<tr>
						<td height="25" align="center">服务器管理</td>
					</tr>
				</table>
				<table width="162" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="20" height="25" align="center" valign="bottom"
							bgcolor="#2574dc"><img src="images/biao1.gif" width="5"
							height="22" /></td>
						<td width="142" bgcolor="#2574dc" class="white"><a
							target="mainFrame"
							href="getSystemConfig.do">系统管理配置</a></td>
					</tr>
					<tr>
						<td height="3" colspan="2"></td>
					</tr>
					<tr>
						<td height="25" align="center" valign="bottom" bgcolor="#2574dc"><img
							src="images/biao1.gif" width="5" height="22" /></td>
						<td height="25" bgcolor="#2574dc" class="white"><a
							target="mainFrame"
							href="GetAllUserInfo.do?act=first">在线人员信息</a></td>
					</tr>
					<tr>
						<td height="3" colspan="2"></td>
					</tr>
					<tr>
						<td height="25" align="center" valign="bottom" bgcolor="#2574dc"><img
							src="images/biao1.gif" width="5" height="22" /></td>
						<td height="25" bgcolor="#2574dc" class="white"><a
							target="mainFrame"
							href="GetAllConferenceInfo.do">进行中的会议</a></td>
					</tr>
					<tr>
						<td height="3" colspan="2"></td>
					</tr>
					<tr>
						<td height="25" align="center" valign="bottom" bgcolor="#2574dc"><img
							src="images/biao1.gif" width="5" height="22" /></td>
						<td height="25" bgcolor="#2574dc" class="white"><a
							href="index.do">注销</a></td>
					</tr>
				</table>
				</td>
				<td valign="top"><IFRAME FRAMEBORDER=0 SCROLLING=NO
					name="mainFrame" id="mainFrame"
					SRC="getSystemConfig.do" width="100%"
					height="100%"></IFRAME></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<tiles:insert page="common/footer.jsp" />
</body>

</html>
