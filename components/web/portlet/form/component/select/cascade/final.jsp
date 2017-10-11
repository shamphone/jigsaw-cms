<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
<bean:define id="referenceType" name="preferences" property="value(referenceType)" type="String"/>
<input type="hidden" id="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>" name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>"/>
<select id="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>1" onchange="changeCommonLevel(this,'<bean:write name="preferences" property="value(referenceId)" ignore="true"/>','<%= referenceType%>')" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>1" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> onblur="Validator.ValidateComponent(this, '<bean:write name="preferences" property="value(referenceId)" ignore="true"/>')">
<logic:equal value="on" name="preferences" property="value(hasNull)">
  <option value="<bean:write name="preferences" property="value(nullValue)"/>"><bean:write name="preferences" property="value(nullText)"/></option>
</logic:equal>
<logic:present name="nodes">
<logic:iterate id="node" name="nodes">
  <option value="<bean:write name="node" property="ID"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
</logic:iterate>
</logic:present>
</select>
<script type="text/javascript" language="javascript">
<logic:present name="nodes">
changeCommonLevel(document.getElementById('<bean:write name="preferences" property="value(referenceId)" ignore="true"/>1'),'<bean:write name="preferences" property="value(referenceId)" ignore="true"/>','<%= referenceType%>');
<logic:notEmpty name="value">
setCommonValue(document.getElementById('<bean:write name="preferences" property="value(referenceId)" ignore="true"/>1'),'<bean:write name="preferences" property="value(referenceId)" ignore="true"/>','<%= referenceType%>','<bean:write name="value" property="ID"/>');
</logic:notEmpty>
</logic:present>
</script>