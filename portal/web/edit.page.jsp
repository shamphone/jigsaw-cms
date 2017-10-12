<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="Generator" name="Beijing Zhongke Fulong Computer Tch.LTD" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><bean:write name="com.fulong.longcon.site" property="name"/>——<bean:write name="com.fulong.portlet.page" property="title"/></title>
<script type="text/javascript" src="<html:rewrite page="/portal/script.js"/>"></script>
<link rel="stylesheet" href="<html:rewrite page="/portal/style.css.jsp"/>"/>
</head>
<body style="border:0px;padding:0 0 0 0;margin:0 0 0 0;background-color:menu" scroll="no">
<logic:iterate id="css" name="com.fulong.portlet.page" property="styleFiles">
  <input type="hidden" id="longcon.css" value="<bean:write name='css'/>"/>
  <input type="hidden" id="javax.portlet.page.mode" value="edit" />
</logic:iterate>
<%
  JspWriter writer=pageContext.getOut();
  request.setAttribute("com.fulong.longcon.writer",writer);
  pageContext.pushBody(); %>
<tiles:insert beanName="com.fulong.longcon.template" flush="false"/>
<% pageContext.popBody(); %>
</body>
</html>
