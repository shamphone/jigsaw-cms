<%@ page contentType="text/html; charset=UTF-8" %>
<% request.getSession().invalidate(); %>
<html>
<head>

  <script type="text/javascript">
  window.location="<%= ""+request.getParameter("path") %>";
  </script>
</head>
</html>
