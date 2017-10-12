<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="EXPIRES" content="0"/>
    <meta http-equiv="Pragma" Content="No-cach" />
    <title><bean:parameter id="title" name="title" value="" /><bean:write name="title" ignore="true" /></title>
  </head>
  <body style="border:0px;padding:0 0 0 0;margin:0 0 0 0;" scroll="no">
    <table cellpadding="0" cellspacing="0" border="0"  width="100%" height="100%"  style="table-layout: fixed">
      <tr>
        <td valign="top">
          <input type="hidden" value="<%= request.getParameter("url")%>"/>
            <iframe id="theFrame" frameborder="0" src="<%= request.getParameter("url")%>" scrolling="no" style="height:100%; width:100%"></iframe>
          </td>
        </tr>
      </table>
    </body>
    <script type="text/javascript">
    var oObject = window.dialogArguments;
      function  document.all.theFrame.contentWindow.onload(){
        document.title=document.all.theFrame.contentWindow.document.title;
      }
      </script>
      </html>
