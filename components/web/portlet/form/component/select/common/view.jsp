<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<select <logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>">
  <option>选项1</option><option>选项2</option><option>选项3</option><option>选项4</option><option>选项5</option><option>选项6</option>
</select>
