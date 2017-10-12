<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="javascript" src="<html:rewrite module="" page='/scripts/script.js'/>" type="text/javascript"></script>
<tiles:importAttribute name="scripts" ignore="true"/><logic:present name="scripts"><logic:iterate id="script" name="scripts">
<script language="javascript" src="<bean:write name='script'/>" type="text/javascript"></script></logic:iterate>
</logic:present>
<link type="text/css" rel="stylesheet" href="<html:rewrite module="" page='/styles/noframe.css'/>" />
<tiles:importAttribute name="stylesheets"  ignore="true"/><logic:present name="stylesheets"><logic:iterate id="stylesheet" name="stylesheets">
<link type="text/css" rel="stylesheet" href="<bean:write name="stylesheet"/>" /></logic:iterate>
</logic:present>
    <title><tiles:getAsString name="title" ignore="true"/>-<bean:message key="product.name"/></title>
  </head>
  <body>
    <tiles:getAsString name="content"/>
  </body>
</html>
