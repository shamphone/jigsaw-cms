<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">

<head>
<html:base />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>操作完成</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/demos.css'/>" />
<script type="text/javascript">
function pageTitle()
{
	return ("操作完成。");
}
</script>
</head>
<body style="margin: 0; padding: 0;">

<table width="100%" cellspacing="0" cellpadding="0" background="<html:rewrite page='/images/tcck.gif'/>">
	<tr>
		<td style="height: 8 px"></td>
	</tr>
	<tr>
		<td align="center"><input type="button" class="blueButton"
			id="ButtonOK" value="确定" onclick="window.close();"></td>
	</tr>
</table>

</body>
</html:html>
