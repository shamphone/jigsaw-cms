<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新建</title>
		<style type="text/css">
			body {padding:10px; }
			body * {font-size:12px;}
		</style>
		<script src='<html:rewrite module="/common" page="/script/common.js"/>' type="text/javascript"></script>
		<script src='<html:rewrite module="/common" page="/script/ajax.js"/>' type="text/javascript"></script>
		<script src='create.js.jsp?<%=request.getQueryString()%>>' type="text/javascript"></script>
		<script type="text/javascript">
			window.onload = function() {
				var modul = "<%=request.getParameter("modul")%>";
				if(modul&&modul=="css"){
					document.getElementById("fileName").value = Math.round(Math.random() * 10000000) + ".css";
				}else{
					document.getElementById("fileName").value = Math.round(Math.random() * 10000000) + ".js";
				}
			}
			var relativePath = window.dialogArguments['path'];
		</script>
	</head>
	<body bgcolor="#EFEFDE">
		<table cellpadding="0" cellspacing="0" height="100%" width="98%" align="center">
			<tr>
				<td align="center" nowrap="nowrap">文件名：<input id="fileName" size="30" maxlength="20"/></td>
			</tr>
			<tr>
				<td align="right">
					<button onclick="_Ok()">确定</button>&nbsp;
					<button onclick="window.close();">取消</button>
				</td>
			</tr>
		</table>
	</body>
</html>