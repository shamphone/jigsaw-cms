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

<title>over</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page='/css/demos.css'/>" />

</head>

<body>
<input type="button" class="blueButton" id="ButtonOK" value="确定">
<bean:define id="msg" name="overmessage"></bean:define>
<script type="text/javascript">
showMsg();
function showMsg()
{
	document.getElementById('ButtonOK').click();
	var contents='<style>body,td{font:menu}img{cursor:hand}</style>';
	contents+='<title>操作完成</title>';
	contents+='<body>';
	contents+='<table width=100% height=100% border=0 background=url("..\images\tcck.gif")>';
	contents+='<tr><td align=center>';
	contents+='<%=msg%>';
	contents+='<br />';
	contents+='<input type=button onclick=self.close() value="关闭">';
	contents+='</td></tr></table>';
	showModalDialog("about:"+contents+"","","dialogHeight:120px;dialogWidth:240px;help:no;center:yes;status:no;resizable:No")
}
</script>
</body>
</html:html>
