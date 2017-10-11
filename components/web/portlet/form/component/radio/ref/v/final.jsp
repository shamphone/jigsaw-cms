<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
<bean:define id="value" name="preferences" property="value(value)" type="String"/>
<ul <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
    <logic:iterate id="node" name="nodes" indexId="ind">
      <li>
        <input <logic:equal value="<%=value%>" name="node" property="ID">checked="true"</logic:equal> id="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>" type="radio" value="<bean:write name="node" property="ID"/>" name="<bean:write name="preferences" property="value(referenceId)"/>" />
        <label for="<bean:write name="preferences" property="value(referenceId)"/><bean:write name="ind"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></label>
      </li>
    </logic:iterate>
</ul>