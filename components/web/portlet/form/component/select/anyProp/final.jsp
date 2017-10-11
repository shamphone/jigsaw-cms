<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
<bean:define id="referenceType" name="preferences" property="value(referenceType)" type="String"/>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<bean:define id="defaultValue" name="defaultValue" type="java.lang.String"/>
<% String refNodeID = preferences.getValue("userID", "false");
String optionValue = "";
if(refNodeID.equals("false")){
	optionValue = preferences.getValue("optionValue", "ID");
}else{
	optionValue = "ID";
}
%>
<select id='<%=(String)request.getAttribute("javax.portlet.id")%>anySelect' name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>"<logic:notEmpty name="preferences" property="value(size)"> size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(style)"> class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> onblur="Validator.ValidateComponent(this, this.name)">
<logic:notEqual value="false" name="preferences" property="value(hasNull)">
  <option value="<bean:write name="preferences" property="value(nullValue)"/>"><bean:write name="preferences" property="value(nullText)"/></option>
</logic:notEqual>
<logic:iterate id="node" name="nodes"><bean:define id="optValue" type="String"><cms:node name="node" propertyName="<%=optionValue%>" /></bean:define>
  <option value="<%= optValue %>" <logic:equal value="<%= defaultValue%>" name="optValue">selected="selected"</logic:equal>><cms:node name="node" propertyName="<%=propertyId%>" ignore="true"/></option>
</logic:iterate>
</select>
<script type="text/javascript" language="javascript">
<logic:notEqual value="false" name="preferences" property="value(hasNull)">
document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>anySelect').value='<bean:write name="defaultValue" ignore="true"/>';
</logic:notEqual>
</script>