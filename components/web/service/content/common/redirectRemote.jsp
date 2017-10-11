<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <title>添加远程分类</title>
      <script language="javascript" type="text/Javascript">
      </script>
</head>
<body>
  <iframe style="width:100%;height:100%;scrollbars:no;" src='http://<bean:write name="remoteDomain"/>/service/content/common/listCategory.do?fromURL=http://<%=request.getServerName()%><%=request.getContextPath()%>/'>
  </iframe>
</body>
</html>
