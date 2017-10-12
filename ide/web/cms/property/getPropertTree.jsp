<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<logic:iterate id="propertyNode" name="propertylist" property="nodes" offset="1">
  <bean:define id="propertyID" name="propertyNode" property="id" type="String"/>
  <bean:define id="property" name="propertyNode" property="node" type="com.fulong.longcon.repository.PropertyDefinition"/>
var item<bean:write name="property" property="ID"/> = new WebFXTreeItem('<input definition="<bean:write name="property" property="declaringNodeDefinition.ID"/>" style="height:13px;" type="radio" value="<%=propertyID%>" id="<%=propertyID.replace("-","_")%>" name="ID"><label for="<%=propertyID.replace("-","_")%>"><bean:write name="property" property="name"/></label>','','','','','');
<bean:write name="root"/>.add(item<bean:write name="property" property="ID"/>);
</logic:iterate>
