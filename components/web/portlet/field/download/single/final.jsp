<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<logic:present name="pathValue"><logic:notEmpty name="pathValue"><a href="<bean:write name="pathValue"/>"><logic:equal value="name" name="preferences" property="value(showWhat)"><bean:define id="pathValue" name="pathValue" type="String"/><%=pathValue.indexOf("/")!=-1 ? pathValue.substring(pathValue.lastIndexOf("/")+1, pathValue.length()) : pathValue %></logic:equal><logic:equal value="text" name="preferences" property="value(showWhat)"><logic:notEmpty name="preferences" property="value(text)"><bean:write name="preferences" property="value(text)" filter="false"/></logic:notEmpty></logic:equal></a></logic:notEmpty></logic:present>