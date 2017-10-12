<%@ page contentType="text/javascript; charset=UTF-8" %>
var Globals = new Object();
Globals.contextPath = "<%= request.getContextPath()%>";
Globals.redirector = Globals.contextPath+"/common/redirect.do?url="; 