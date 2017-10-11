<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
  <logic:empty name="preferences" property="value(value)">
	<logic:iterate id="node" name="nodes" indexId="ind">
      <span>
        <input id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="radio" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
        <label for="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></label>
      </span>
    </logic:iterate>
  </logic:empty>  
  <logic:notEmpty name="preferences" property="value(value)">
<bean:define id="value" name="preferences" property="value(value)" type="String"/>
    <logic:iterate id="node" name="nodes" indexId="ind">
      <span>
        <input <logic:equal value="<%=value%>" name="node" property="ID">checked="true"</logic:equal> id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="radio" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
        <label for="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></label>
      </span>
    </logic:iterate>
</logic:notEmpty>