<%@ page contentType="text/html; charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="EXPIRES" content="0"/>
    <meta http-equiv="Pragma" Content="No-cach" />
    <title><% String title=request.getParameter("title");if(title==null){title="";}%><%=java.net.URLDecoder.decode(title,"UTF-8")%></title>
  </head>
  <body style="border:0px;padding:0 0 0 0;margin:0 0 0 0;" scroll="no">
    <table cellpadding="0" cellspacing="0" border="0"  width="100%" height="100%"  style="table-layout: fixed">
      <tr>
        <td valign="top">
          <iframe id="theFrame" frameborder="0" src="<%= request.getParameter("url")%>&category=<%=request.getParameter("categoryID")%>" scrolling="no" style="height:100%; width:100%"></iframe>
        </td>
      </tr>
    </table>
  </body>
  <script type="text/javascript">
  document.getElementById("theFrame").contentWindow.onload = function(){
	  document.title=document.getElementById("theFrame").contentWindow.document.title;
  }
  </script>
</html>
