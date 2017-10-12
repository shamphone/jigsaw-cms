<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>视频会议管理系统：登录</title>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />
</head>

<body bgcolor="WHITE">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="WHITE">
	<tr>
		<td valign="top">
		<iframe src="header.do"
			frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
			width="100%" height="60px">
		</iframe>
		</td>
	</tr>
	<tr>
		<td>
		<table width="874" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="WHITE">
			<tr>
				<td width="275" align="right"><img src="images/left5.gif"
					width="275" /></td>
				<td width="602" background="images/left6.gif">
				<form id="loginform" name="loginform" method="post" action="Login.do">
				<table width="90%" border="0" align="center" cellpadding="8"
					cellspacing="0">
					<tr>
						<td width="14%" class="white">用户名：</td>
						<td width="86%"><input class="line" id="username"
							name="username" type="text" size="20" /></td>
					</tr>
					<tr>
						<td class="white">密 码：</td>
						<td><input name="password" type="password" class="line"
							size="20" /></td>
					</tr>
					<tr>
						<td height="43">&nbsp;</td>
						<td><html:image src="images/login.png" /></td>
					</tr>
					<logic:present name="invaild">
						<tr>
							<td height="24" colspan="2"><font color="red"><bean:write
								name="invaild" /></font></td>
						</tr>
					</logic:present>
				</table>
				</form>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table width="877" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="WHITE">
			<tr>
				<td  height="45" valign="top"><img src="images/shodw.gif"
					width="874"/></td>
			</tr>
			<tr>
				<td valign="top" height="50px"><img src="images/kh.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<tiles:insert page="common/footer.jsp" />
<script language="javascript">
document.getElementById("username").focus();
</script>
</body>
</html>
