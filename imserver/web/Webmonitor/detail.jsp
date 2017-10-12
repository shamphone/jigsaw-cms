<%@page contentType="text/html; charset=ISO8859_1"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@page language="java"%>
<html>
<body>
<div align="center" class="style1">人员详细信息
</div>
<div align="center"></div>
<hr>
<p>&nbsp;</p>
<table width="316" border="1" align="center">
  <tr>
    <th width="150" height="30" valign="middle" scope="col">用户名</th>
    <th width="150" scope="col"><div align="center"><%=request.getParameter("username")%></div></th>
  </tr>
  <tr>
    <td height="30" valign="middle"><div align="center"><strong>姓氏</strong></div></td>
    <td ><div align="center"><strong><%=request.getParameter("lastname")%></strong></div></td>
  </tr>
  <tr>
    <td height="30" valign="middle"><div align="center"><strong>名字</strong></div></td>
    <td><div align="center"><strong><%=request.getParameter("firstname")%></strong></div></td>
  </tr>
  <tr>
    <td height="30" valign="middle"><div align="center"><strong>电子邮件</strong></div></td>
    <td><div align="center"><a href="mailto:<%=request.getParameter("email")%>"><%=request.getParameter("email")%></a></div></td>
  </tr>
</table>
</body>
</html>
