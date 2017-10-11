<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%><pt:actionURL portletMode="final" var="formURL"><pt:param name="portlet_action_name">deleteNode</pt:param> </pt:actionURL>
<logic:empty name="preferences" property="value(imgsrc)">
	<button  type="submit" id='<%=(String) request.getAttribute("javax.portlet.id")+"mulDelButton"%>'
		<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
		<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
		<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
		onclick="return DeletePortlet.doDelete(this.form,'<%=formURL%>&definition=<bean:write name="definition"/>&selfURL='+window.location.href)"
		<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>><bean:write
		name="preferences" property="value(value)" /></button>
</logic:empty>
<logic:notEmpty name="preferences" property="value(imgsrc)">
	<image id='<%=(String) request.getAttribute("javax.portlet.id")+"mulDelButton"%>'  src="<bean:write name="preferences" property="value(imgsrc)"/>" 
		<logic:notEmpty name="preferences" property="value(imgstyle)">class="<bean:write name="preferences" property="value(imgstyle)"/>"</logic:notEmpty>
		<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
		onclick="return DeletePortlet.doDelete(this.form,'<%=formURL%>&definition=<bean:write name="definition"/>&selfURL='+window.location.href)"
		style="width:<bean:write name="preferences" property="value(imgwidth)" ignore="true"/>; height:<bean:write name="preferences" property="value(imgheight)" ignore="true"/>">
</logic:notEmpty>
