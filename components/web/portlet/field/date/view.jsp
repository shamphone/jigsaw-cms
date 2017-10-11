<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<logic:notEmpty name="date"><bean:write name="date"/>&nbsp;</logic:notEmpty><logic:notEmpty name="time"><bean:write name="time"/></logic:notEmpty>
