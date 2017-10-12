<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="Generator" name="Beijing Zhongke Fulong Computer Tch.LTD">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title><bean:write name="com.fulong.longcon.site" property="name"/>——<bean:write name="com.fulong.portlet.page" property="title"/></title>
<logic:iterate id="script" name="com.fulong.portlet.page" property="scriptFiles">
  <logic:notEmpty name="script"><script type="text/javascript" src="<html:rewrite page="/sites" module=""/>/<bean:write name="com.fulong.longcon.site" property="root"/><bean:write name='script'/>"></script>  </logic:notEmpty>
</logic:iterate>
<script type="text/javascript" src="<html:rewrite page="/portal/script.js"/>"></script>
<logic:iterate id="css" name="com.fulong.portlet.page" property="styleFiles">
<link rel="stylesheet" href="<html:rewrite page="/sites" module=""/>/<bean:write name="com.fulong.longcon.site" property="root"/><bean:write name='css'/>"/>
</logic:iterate>
<link rel="stylesheet" href="<html:rewrite page="/portal/style.css.jsp"/>"/>
<script type="text/javascript" language="javascript">
var current_portlet_name="<%= request.getParameter("portlet_name") %>";
var enableToggle=true;
</script>
</head>
<body>
<tiles:insert beanName="com.fulong.longcon.template"/>
</body>
</html>
