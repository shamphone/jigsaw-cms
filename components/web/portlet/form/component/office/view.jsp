<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<span style="<logic:notEmpty name="preferences" property="value(width)">width:<bean:write name="preferences" property="value(width)"/></logic:notEmpty><logic:notEmpty name="preferences" property="value(height)">;height:<bean:write name="preferences" property="value(height)"/></logic:notEmpty>"
<logic:notEmpty name="preferences" property="value(style)">
	class="<bean:write name="preferences" property="value(style)"/>"
</logic:notEmpty>
>
这里是<bean:write name="propertyDef" property="name" ignore="true"/>
</span>
