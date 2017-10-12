<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="robots" content="index,follow" />
	<title></title>
</head>
<body bgcolor="#FFFFFF">
<div id="container">
  这里是结果：
  <fulong:portlet id="html" type="html">
    <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//版权信息.jspf</fulong:value>
          </fulong:preference>
  </fulong:portlet>
<bean:write name="com.fulong.longcon.Content" property="nodeProperty(affair-name).string"/>
</div>
</body>
</html>
