<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<meta http-equiv="refresh" content="10;GetAllConferenceInfo.do" />
<title>视频会议后台管理系统</title>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table bgcolor="#FFFFFF" border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td height="480" valign="top" width="100%">
			<table width="98%" border="1" align="center" cellpadding="2" cellspacing="0" bordercolorlight="#CCCCCC" bordercolordark="#FFFFFF">
			<tr>
				<th bgcolor="#d1e3fb">会议名称</th>
				<th bgcolor="#d1e3fb">会议时间段</th>
				<th bgcolor="#d1e3fb">会议模式</th>
				<th bgcolor="#d1e3fb">在线人数</th>
			</tr>
			<logic:iterate name="conferences" id="conference" indexId="index">
				<tr>
					<td><bean:write name="conference" property="title" /></td>
					<td>
						<bean:write name="conference" property="startTime" /> 到
						<bean:write	name="conference" property="endTime" />
					</td>
					<td><bean:write name="conference" property="model" /></td>
					<td><bean:write name="conference" property="userNum" /></td>
				</tr>
			</logic:iterate>
		</table>
		</td>
	</tr>
</table>

</body>
</html>
