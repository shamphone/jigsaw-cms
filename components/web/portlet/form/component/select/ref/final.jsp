<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<select <logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(referenceId)" ignore="true"/>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> onblur="Validator.ValidateComponent(this, this.name)">
<logic:equal value="on" name="preferences" property="value(hasNull)">
  <option value="<bean:write name="preferences" property="value(nullValue)"/>"><bean:write name="preferences" property="value(nullText)"/></option>
</logic:equal>
  <bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
  <logic:empty name="preferences" property="value(defaultValues)">
    <logic:iterate id="node" name="nodes">
      <option value="<bean:write name="node" property="ID"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
    </logic:iterate>
  </logic:empty>
  <logic:notEmpty name="preferences" property="value(defaultValues)">
    <bean:define id="value" name="preferences" property="value(defaultValues)" type="String"/>
    <logic:iterate id="node" name="nodes">
     <logic:equal value="<%=value%>" name="node" property="ID">
      <option selected="true" value="<bean:write name="node" property="ID"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
     </logic:equal>
     <logic:notEqual value="<%=value%>" name="node" property="ID">
      <option value="<bean:write name="node" property="ID"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
    </logic:notEqual>
    </logic:iterate>
  </logic:notEmpty>
</select>
