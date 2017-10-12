<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <script type="text/javascript">
  var ret=new Object();
  ret.css="<%= request.getParameter("css") %>";
  ret.selector="<%= request.getParameter("selector") %>";
  ret.style="<%= request.getParameter("style") %>";
  window.returnValue=ret;
  window.close();
  </script>
</head>
<body bgcolor="#ffffff">
</body>
</html>
