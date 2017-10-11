<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@page import="javax.portlet.PortletPreferences" %>
<table cellpadding="0" cellspacing="0" border="0" >
<tr><td>
<select <logic:notEmpty name="preferences" property="value(listStyle)">class="<bean:write name="preferences" property="value(listStyle)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)"/>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>>
</select>
</td>
<td>
<button class="commonbut"  <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> >选择...</button><br/>
<logic:equal value="on" name="preferences" property="value(hasUp)"><button class="commonbut" >上   移</button></logic:equal><br/>
<logic:equal value="on" name="preferences" property="value(hasDown)"><button class="commonbut" >下   移</button></logic:equal><br/>
<logic:equal value="on" name="preferences" property="value(delete)"><button class="commonbut" >删   除</button></logic:equal><br/>
</td></tr>
</table>