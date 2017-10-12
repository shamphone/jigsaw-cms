<%@ page contentType="text/html; charset=UTF-8" %>
<%/**
* 用来封装tableXrepeaterEditor.jsp的，fckeditor机制要求必须使用frame来封装，否则将出脚本错误。
**/%>
<html>
  <head>
    <title>表格重复器片断编辑器</title>
  </head>
  <body>
    <iframe id="editor" src="tableXrepeaterEditor.jsp?<%= request.getQueryString() %>" frameborder="0" style="width:100%;height:100%"></iframe>
  </body>
</html>
