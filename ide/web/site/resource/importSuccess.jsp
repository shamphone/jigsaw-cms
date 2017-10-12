<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>保存成功</title>
<script type="text/javascript" language="javascript">
function doClose(){
  var oRet = new Object();  
  oRet.path = '<bean:write name="contextPath"  ignore="true" />';
  window.returnValue=oRet;
  window.close();
}
</script>
</head>
<body bgcolor="#ffffff" onload="doClose()">
</body>
</html>
