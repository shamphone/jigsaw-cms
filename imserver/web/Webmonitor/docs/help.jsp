<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>视频会议管理系统：帮助</title>
<link href="../css/stylejq.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="WHITE">
	<tr>
		<td valign="top"><iframe src="/Webmonitor/header.do"
			frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
			width="100%" height="60px"></iframe> <br />
		<br />
		<table width="890" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top"><iframe id=IFrameHelp
					width="100%" height="440px" frameborder=0 class="line1"
					scrolling=auto src="help.htm"></iframe></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<tiles:insert page="../common/footer.jsp" />
</body>
</html>
