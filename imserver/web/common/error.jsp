<%@page language="java" contentType="text/html; charset=gb2312"%>
<%@page import="java.io.*"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
<html:base />
<title>error.jsp</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
<h1>Action Exception</h1>

<pre>
  <%
  	Exception t = (Exception) request.getAttribute("exception");
  		if (t != null) {
  			t.printStackTrace(new PrintWriter(out));
  			out.println("\nRoot Cause:");
  			Throwable rootCause = t.getCause();
  			if (rootCause != null) {
  				rootCause.printStackTrace(new PrintWriter(out));
  			}
  		}
  %>
  </pre>
</body>
</html:html>
