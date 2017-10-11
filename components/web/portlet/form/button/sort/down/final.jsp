<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib
	uri="/WEB-INF/fulong-portal.tld" prefix="pt"%><button
	<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
	onclick="downNode('<bean:write name="preferences" property="value(channel)"/>','<bean:write name="node" property="ID"/>','<bean:write name="preferences" property="value(process)"/>','<bean:write name="preferences" property="value(activity)"/>')"
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>><bean:write
	name="preferences" property="value(value)" /></button>