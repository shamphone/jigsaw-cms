<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="propertyId" name="preferences" property="value(propertyId)" type="String"/>
<logic:iterate id="property" name="properties">
	<input type="hidden" name="<bean:write name="property" property="label"/>" value="">
</logic:iterate>
<select id='<%=request.getAttribute("javax.portlet.id")%>1' name="<%=request.getAttribute("javax.portlet.id")%>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> onblur="Validator.ValidateComponent(this, this.name)">
<logic:equal value="on" name="preferences" property="value(hasNull)">
  <option value="<bean:write name="preferences" property="value(nullValue)"/>"><bean:write name="preferences" property="value(nullText)"/></option>
</logic:equal>
<logic:present name="nodes">
<logic:iterate id="node" name="nodes">
	<logic:present name="preferences" property="value(IDValue)">
		<option nodeId="<bean:write name="node" property="ID"/>" value="<bean:write name="node" property="ID"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
	</logic:present>
	<logic:notPresent name="preferences" property="value(IDValue)">
		<bean:define id="value" name="preferences" property="value(value)" type="String"/>
 		<option nodeId="<bean:write name="node" property="ID"/>" value="<cms:node name="node" propertyName="<%=value%>"/>"><cms:node name="node" propertyName="<%=propertyId%>"/></option>
	 </logic:notPresent>
 </logic:iterate>
</logic:present>
</select>
<script type="text/javascript" src="/ide/cms/classes/objects.js"></script>
<script type="text/javascript" src="/components/portlet/form/component/select/propsCascade/script.js"></script>
<script type="text/javascript" language="javascript">
	var propertes = new Array();
	var property;
	<logic:iterate id="property" name="properties">
		property = new Object(); 
		property.name = '<bean:write name="property" property="label"/>';
		<logic:notPresent name="property" property="value">
			property.value = null;
		</logic:notPresent>
		<logic:present name="property" property="value">
			property.value = '<bean:write name="property" property="value" ignore="true"/>';
		</logic:present>
		propertes.push(property);
	</logic:iterate>

	<logic:present name="preferences" property="value(IDValue)">
	var container = new  Container(propertes,null,'<%=propertyId%>',document.getElementById('<%=request.getAttribute("javax.portlet.id")%>1'),'<bean:write name="preferences" property="value(hasNull)"/>','<bean:write name="preferences" property="value(nullValue)"/>','<bean:write name="preferences" property="value(nullText)"/>','<%=request.getAttribute("javax.portlet.id")%>','<bean:write name="preferences" property="value(compositeProp)" ignore="true"/>');
	</logic:present>
	<logic:notPresent name="preferences" property="value(IDValue)">
	<bean:define id="value" name="preferences" property="value(value)" type="String"/>
	var container = new  Container(propertes,'<%=value%>','<%=propertyId%>',document.getElementById('<%=request.getAttribute("javax.portlet.id")%>1'),'<bean:write name="preferences" property="value(hasNull)"/>','<bean:write name="preferences" property="value(nullValue)"/>','<bean:write name="preferences" property="value(nullText)"/>','<%=request.getAttribute("javax.portlet.id")%>','<bean:write name="preferences" property="value(compositeProp)" ignore="true"/>');
	</logic:notPresent>
	
</script>