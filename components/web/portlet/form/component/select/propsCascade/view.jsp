<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:iterate id="property" name="preferences" property="values(propertyIds)">
<select name="<bean:write name="property"/>" <logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name='<%=request.getAttribute("javax.portlet.id")%> ' <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
  <option>选项1</option><option>选项2</option><option>选项3</option><option>选项4</option><option>选项5</option><option>选项6</option>
</select>
</logic:iterate>
