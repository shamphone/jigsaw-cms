<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
<head>
<script language="javascript">
window.navigate('<bean:write name="redirectLocation"/>');
</script>
</head>

<body>
</body>
</html>
