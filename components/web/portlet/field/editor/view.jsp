<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table border="0" cellpadding="2" cellspacing="2" width="100%" class="<bean:write name="preferences" property="value(table-style)"/>">
<input type="hidden" name="category" value="<bean:write name='category' property='ID'/>"/>
<logic:iterate id="property" name="fields">
  <bean:define id="propID" name="property" property="ID" type="java.lang.String" />
  <tr>
    <td>
      <span   class="<bean:write name="preferences" property="value(field-style)"/>"> <bean:write name="property" property="name"/></span>
      <logic:present name='<%= "errors."+propID %>'><span class="<bean:write name="preferences" property="value(errors-style)"/>" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="name"/> </span></logic:present>
      <logic:notPresent name='<%= "errors."+propID %>'><span class="<bean:write name="preferences" property="value(tips-style)"/>" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="description"/> </span></logic:notPresent>
    </td>
  </tr>
  <tr>
    <td  class="<bean:write name="preferences" property="value(component-style)"/>">
    <cms:propertyEditor definition="property" propValues='<%= propID+".values" %>'/>
    </td>
  </tr>
</logic:iterate>
</table>
<div class="operation">
  <input type="button" value="保存" class="<bean:write name="preferences" property="value(button-style)"/>"/>
</div>
