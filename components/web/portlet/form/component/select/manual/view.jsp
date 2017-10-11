<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<%
String value = preferences.getValue("default-value","");
%>
<select name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
<logic:iterate id="option" name="preferences" property="values(values)">
<logic:present name="option">
 <bean:define id="opt" name="option" type="String"/>
 <option <%if(value.equals(opt.split(",")[0])){out.print("selected='selected'");} %> value="<%=opt.split(",")[0]%>>"><%=opt.split(",")[1]%></option>
</logic:present>
</logic:iterate>
</select>
