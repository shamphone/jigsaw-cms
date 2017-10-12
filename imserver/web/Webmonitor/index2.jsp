<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>视频会议管理系统：首页</title>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="WHITE">
	<tr>
		<td valign="top">
		<iframe src="header.do" frameborder="0" scrolling="no" marginwidth="0" marginheight="0" width="100%" height="60px"></iframe>

	  	<table width="876" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
		  		<td align="right"><img src="images/left1.gif" width="280" height="329" /></td>
		  		<td><img src="images/left2.gif" width="215" height="329" border="0" usemap="#Map" /></td>
		  		<td><img src="images/left3.gif" width="186" height="329" border="0" usemap="#Map2" /></td>
		  		<td><img src="images/left4.gif" width="195" height="329" border="0" usemap="#Map3Map" /></td>
			</tr>
		</table>
		<map name="Map" id="Map">
			<area shape="rect" coords="73,102,180,246" href="/download/LongConVCS_Setup.exe" />
		</map>
		<map name="Map2" id="Map2">
			<area shape="rect" coords="40,98,152,245" href="<html:rewrite page='/docs/help.jsp'/>" />
		</map>
		<map name="Map3Map" id="Map3Map">
			<area shape="rect" coords="42,103,144,243" href="login.jsp" />
		</map>
		<table width="877" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="45" valign="top"><img src="images/shodw.gif" width="874" height="19" /></td>
			</tr>
			<tr>
				<td height="50" valign="top"><img src="images/kh.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<tiles:insert page="common/footer.jsp" />
</body>
</html>
