<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="id" name="definition" property="ID" type="String"/>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="java.lang.String"/>
<bean:define id="viewPropertyId" name="preferences" property="value(viewPropertyId)" type="java.lang.String"/>
<select onchange="nodeSelect.selectNodeSelect(this,document.getElementById('<bean:write name="preferences" property="value(propertyId)"/>'),'<bean:write name="preferences" property="value(viewPropertyId)"/>')" id="selectDefinition" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="selectDefinition" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(categoryStyle)">class="<bean:write name="preferences" property="value(categoryStyle)"/>"</logic:notEmpty>>
<logic:iterate id="def" name="tree" property="nodes">
<option <logic:equal value="<%=id%>" name="def" property="node.ID"> selected="selected"</logic:equal> value="<bean:write name="def" property="node.ID"/>"><bean:write name="def" property="node.name"/></option>
</logic:iterate>
</select>
<select <logic:equal value="on" name="preferences" property="value(submit)"> onchange="this.form.submit()"</logic:equal> id="<bean:write name="preferences" property="value(propertyId)"/>" name="<bean:write name="preferences" property="value(propertyId)"/>" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(nodeStyle)"/>"</logic:notEmpty>>
</select>
<script type="text/javascript" language="javascript">
nodeSelect.selectNodeSelect(document.getElementById("selectDefinition"),document.getElementById("<bean:write name="preferences" property="value(propertyId)"/>"),'<bean:write name="preferences" property="value(viewPropertyId)"/>','<%=request.getParameter(propertyId)%>');
</script>