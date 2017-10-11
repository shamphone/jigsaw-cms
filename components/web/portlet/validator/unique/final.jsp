<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<logic:present name="validatorNode">
<cms:unique name="validatorNode">
<input type="hidden" value="<bean:write name="validatorNode" property="ID" ignore="true"/>" name="nodeID">
</cms:unique>
</logic:present>
<logic:notPresent name="validatorNode">
<input type="hidden" value="" name="nodeID">
</logic:notPresent>
<span id="<bean:write name='javax.portlet.id'/>_0" style="display:none" <logic:notEmpty name='preferences' property='value(error-style)'>class=<bean:write name='preferences' property='value(error-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(error-tips)"/></span>
<span id="<bean:write name='javax.portlet.id'/>_1" <logic:notEmpty name='preferences' property='value(tips-style)'>class=<bean:write name='preferences' property='value(tips-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(tips)"/></span><script type="text/javascript" language="javascript">
Validator.AddRule('<bean:write name="preferences" property="value(form)"/>','<bean:write name="preferences" property="value(propertyId)"/>','<bean:write name="javax.portlet.id"/>_1','<bean:write name="javax.portlet.id"/>_0','Unique','<bean:write name="preferences" property="value(category)"/>');
</script>
