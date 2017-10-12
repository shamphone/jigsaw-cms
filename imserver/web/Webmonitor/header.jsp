<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="60" valign="bottom" background="images/graybg.gif">
			<table width="877" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					  <td class="title"><bean:write property="property(title)" name="serverLogic" /></td>
					  <td><a href="/download/LongConVCS_Setup.exe" target="new">下载客户端</a> |
						  <a href="<html:rewrite page='/docs/help.jsp'/>" target="_top">阅读使用说明</a> |
						  <a href="login.jsp" target="_top">服务器设置</a></td>
				</tr>
				<tr>
					  <td colspan="2" height="20" align="right"><img src="images/fgx.gif" width="870" height="8" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
