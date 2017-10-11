<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib
	uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib
	uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="viewPropertyId" name="preferences"
	property="value(viewPropertyId)" type="java.lang.String" />
<input type="text"  onblur="Validator.ValidateComponent(this, this.name.substring(0,this.name.indexOf('show')))"
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
	name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>show"
	id="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>show"
	<logic:notEmpty name="preferences" property="value(textStyle)">class="<bean:write name="preferences" property="value(textStyle)"/>"</logic:notEmpty>
	<logic:present name="valueSousuo">
	value="<bean:write name="valueSousuo" property="value"/>"
	</logic:present>
	<logic:notPresent name="valueSousuo">
	<logic:present name="value">
	value="<cms:node name="value" propertyName="<%=viewPropertyId%>" ignore="true"/>"
	</logic:present>
	</logic:notPresent>
	/>
	<logic:notPresent name="valueSousuo">
	<input type="hidden" name="<bean:write name="preferences" property="value(propertyId)" />" id="<bean:write name="preferences" property="value(propertyId)" />" <logic:present name="value">value="<bean:write name="value" property="ID" />"</logic:present> />
	</logic:notPresent>
	<logic:present name="valueSousuo">
	<input type="hidden" name="<bean:write name="preferences" property="value(propertyId)" />" id="<bean:write name="preferences" property="value(propertyId)" />" <logic:present name="valueid">value="<bean:write name="valueid"/>"</logic:present> />
	</logic:present>
	<logic:equal value="defaultPage"
			name="preferences" property="value(selectChannel)">
			<button type="button"
	<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty>
	onclick="nodeSelect.singleNodeSelect('<bean:write name="preferences" property="value(category)" />',document.getElementById('<bean:write name="preferences" property="value(propertyId)" />show'),document.getElementById('<bean:write name="preferences" property="value(propertyId)" />'),'<bean:write name="preferences" property="value(viewPropertyId)" />','<bean:write name="preferences" property="value(left)" ignore="true" />','<bean:write name="preferences" property="value(searchText)" ignore="true" />','<bean:write name="preferences" property="value(orderfield)" ignore="true" />',oValue<bean:write name='javax.portlet.id'/>,'<bean:write name="preferences" property="value(sort)" ignore="true" />')">选择...</button>
		</logic:equal>
		<logic:equal value="customerPage" name="preferences"
			property="value(selectChannel)">
			<button type="button" id="addNodeButton"
				style="position: relative; left: 5px;"
				<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)" ignore="true"/>"</logic:notEmpty>
				<logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty>
				onclick="nodeSelect. singleCustomerSelect(document.getElementById('<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>show'),document.getElementById('<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>'),'<bean:write name="preferences" property="value(channel)"  ignore="true"/>','<bean:write name="preferences" property="value(width)" ignore="true" />','<bean:write name="preferences" property="value(height)" ignore="true" />','<bean:write name="preferences" property="value(viewPropertyId)" />')">选择...</button>
			</logic:equal>
	<script type="text/javascript" language="javascript">
var oValue<bean:write name='javax.portlet.id'/> = '';
<logic:iterate id="selectedProp" name="selectedProps">
oValue<bean:write name='javax.portlet.id'/> = oValue<bean:write name='javax.portlet.id'/> + '<bean:write name="selectedProp" property="ID" ignore="true"/>'+ '*' ;
</logic:iterate>
</script>