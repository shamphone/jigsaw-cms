<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib
	uri="/WEB-INF/fulong-portal.tld" prefix="pt"%><pt:actionURL
	portletMode="final" var="formURL">
	<pt:param name="portlet_action_name">submitNode</pt:param>
</pt:actionURL>
<input type="hidden" name="redirectURL"
	value="<bean:write name="preferences" property="value(url)" ignore="true"/>" />
<input type="submit"
	<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
	onclick="this.form.action='<%=formURL + "&definition="%>'+this.form.definition+'&contentId=<bean:write name="node" property="ID"/>'"
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> value="<bean:write
	name="preferences" property="value(value)" />"/>
