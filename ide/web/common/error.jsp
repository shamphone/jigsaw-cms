<%@page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:messages id="message">
<bean:write name="message" />
</html:messages>