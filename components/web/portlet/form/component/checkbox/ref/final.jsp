<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<ul <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
<logic:empty name="preferences" property="value(value)">  
    <logic:iterate id="node" name="nodes" indexId="ind">
      <li>
        <input id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="checkbox" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
        <label for="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></label>
      </li>
    </logic:iterate>
</logic:empty>
<logic:notEmpty name="preferences" property="value(value)">
  <bean:define id="values" name="preferences" property="values(value)" type="String[]"/>  
    <logic:iterate id="node" name="nodes" indexId="ind">
    <bean:define id="theNodeID" name="node" property="ID" type="String"/>
      <li>
        <% boolean hasthis = false;
      	for(int i=0; i<values.length;i++){
      		if(theNodeID.equals(values[i])){
      			hasthis = true;
      		}
      	}
      	if(hasthis){%>
      		<input checked="true" id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="checkbox" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
      	<%}else{%>
      		<input id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="checkbox" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
      	<%}%>    
        <label for="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></label>
      </li>
    </logic:iterate>
</logic:notEmpty>
</ul>
