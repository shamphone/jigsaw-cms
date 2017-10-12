<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改node结束</title>
  </head>

  <body>
  <logic:present name="proID">
	  <bean:define id="propID" name="proID"/>
	  <script language="JavaScript" type="text/Javascript">
	  var ret=new Object();
	  ret.id = '<bean:write name="oNode" property="ID" ignore="true"/>';
	  ret.displayName='<cms:node name="oNode" propertyName="<%=(String)propID %>" ignore="true"/>';
	  window.returnValue=ret;
	  window.close();
	  </script>
  </logic:present>
  </body>
</html>
