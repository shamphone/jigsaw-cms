<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>选择格式</title>
  </head>

  <body>
  <script language="JavaScript" type="text/Javascript">
  var ret=new Object();
  ret.ID = '<bean:write name="property" property="ID"/>';
  <logic:equal value="true" name="property" property="protected">
  ret.isProtected='<bean:write name="property" property="protected"/>';
  </logic:equal>
  ret.name = '<bean:write name="property" property="name"/>';
  ret.definition = '<bean:write name="property" property="declaringNodeDefinition.ID"/>';
  ret.type = <bean:write name="property" property="type"/>;
  <logic:present name="property" property="nodeDefinition">
  ret.childType = '<bean:write name="property" property="nodeDefinition.ID"/>';
  </logic:present>
  ret.referenceType='<bean:write name="property" property="referenceType" ignore="true"/>';
  window.returnValue=ret;
  window.close();
  </script>

  </body>
</html>
