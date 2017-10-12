<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
  <head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 2999 21:29:02 GMT" />
    <link rel="stylesheet" href="<html:rewrite page='/portal/style.css' module=''/>"/>
    <title>保存成功!</title>
    <script type="text/javascript" language="javascript">
      function closeWindow(){
        window.close();
      }
      </script>
      </head>
      <body background="menu" style="border:0px;padding:0 0 0 0;margin:0 0 0 0;background-color:menu">
        <table width="100%" border="0">
          <tr>
            <td align="center" height="300px">保存成功!</td>
          </tr>
          <tr>
            <td align="center">  <input type=button value="确定" onclick="closeWindow()"/></td>
          </tr>
        </table>
      </body>
    </html>
