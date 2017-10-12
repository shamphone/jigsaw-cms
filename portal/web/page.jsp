<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="Generator" name="北京中科辅龙计算机技术股份有限公司"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><bean:write name="com.fulong.portal.title"/></title>
<script type="text/javascript" src="<html:rewrite page="/portal/script.js"/>"></script>
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
