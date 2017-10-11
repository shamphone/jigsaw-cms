<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@page import="javax.portlet.PortletPreferences" %>
<ul <logic:notEmpty name="preferences" property="value(listStyle)">class="<bean:write name="preferences" property="value(listStyle)"/>"</logic:notEmpty>>
<%
  PortletPreferences pps = (PortletPreferences) request.getAttribute("preferences");
  String tmp = pps.getValue("fileCount", null);
  int fileCount = Integer.parseInt(tmp);
  for (int i = 0; i < fileCount; i++) {%>
  <li><input type="file" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> disabled="true" name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>" <logic:notEmpty name="preferences" property="value(fileStyle)">class="<bean:write name="preferences" property="value(fileStyle)"/>"</logic:notEmpty> /></li>
<%} %>
</ul>
