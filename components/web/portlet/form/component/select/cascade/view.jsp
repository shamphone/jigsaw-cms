<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<input type="hidden"
	name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>1" />
<select
	<logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal>
	<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
	name='<bean:write name="preferences" property="value(referenceId)" ignore="true"/>'
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
	<option>选项1</option>
	<option>选项2</option>
	<option>选项3</option>
	<option>选项4</option>
	<option>选项5</option>
	<option>选项6</option>
</select>