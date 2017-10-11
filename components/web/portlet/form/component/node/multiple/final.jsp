<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="viewPropertyId" name="preferences" property="value(viewPropertyId)" type="java.lang.String"/>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<%
String oLength = preferences.getValue("length","");
int strLength = 0 ;
try{
	strLength = Integer.parseInt(oLength) ;
}catch(Exception e){
	strLength = 0 ;
}

%>
<table cellpadding="0" cellspacing="0" border="0" style="display:inline;">
<tr><td>
<input type="hidden" value="asfileforform" name="asfileforform"/><select multiple="multiple" <logic:notEmpty name="preferences" property="value(listStyle)">class="<bean:write name="preferences" property="value(listStyle)" ignore="true"/>"</logic:notEmpty> id="<bean:write name='preferences' property='value(propertyId)' ignore="true"/>" name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)" ignore="true"/>"</logic:notEmpty><logic:empty name="preferences" property="value(size)">size="5"</logic:empty>>
  <logic:iterate id="value" name="values">
    <option title="<cms:node name="value" propertyName="<%=viewPropertyId%>" ignore="true"/>" value="<bean:write name="value" property="ID" ignore="true"/>">
    	<%if(strLength>0){%>
    	<cms:node name="value" propertyName="<%=viewPropertyId%>" length="<%=strLength%>" ignore="true"/>
    	<%}else{%>
    	<cms:node name="value" propertyName="<%=viewPropertyId%>" ignore="true"/>
    	<%} %>
    </option>
  </logic:iterate>
</select>
</td>
<td valign="top">
<logic:equal value="defaultPage" name="preferences" property="value(selectChannel)"><button type="button"  type="button" id="addNodeButton" style="position:relative;left:5px;" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)" ignore="true"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty> onclick="nodeSelect.multipleNodeSelect('<bean:write name="preferences" property="value(category)" ignore="true" />',this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>'],'<bean:write name="preferences" property="value(viewPropertyId)"  ignore="true"/>','<bean:write name="preferences" property="value(left)" ignore="true" />','<bean:write name="preferences" property="value(searchText)" ignore="true" />','<bean:write name="preferences" property="value(orderfield)" ignore="true" />','${viewPropertyId}','<bean:write name="preferences" property="value(sort)" ignore="true" />','<bean:write name="preferences" property="value(length)" ignore="true" />')">选择...</button><br/></logic:equal>
<logic:equal value="customerPage" name="preferences" property="value(selectChannel)"><button type="button"  id="addNodeButton" style="position:relative;left:5px;" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)" ignore="true"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty> onclick="nodeSelect.multipleCustomerSelect(this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>'],'<bean:write name="preferences" property="value(length)" ignore="true" />','<bean:write name="preferences" property="value(channel)"  ignore="true"/>','<bean:write name="preferences" property="value(width)" ignore="true" />','<bean:write name="preferences" property="value(height)" ignore="true" />')">选择...</button><br/></logic:equal>
<logic:equal value="on" name="preferences" property="value(hasUp)"><button type="button"  <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty> style="position:relative;left:5px;" onclick="upperShift(this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>'])">上&nbsp&nbsp&nbsp移</button></logic:equal><br/>
<logic:equal value="on" name="preferences" property="value(hasDown)"><button type="button"  <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty> style="position:relative;left:5px;" onclick="lowerShift(this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>'])">下&nbsp&nbsp&nbsp移</button></logic:equal><br/>
<logic:equal value="on" name="preferences" property="value(delete)"><button type="button"  style="position:relative;left:5px;" <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)" ignore="true"/>"</logic:notEmpty> onclick="deleteOption(this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>']);selectAll(this.form['<bean:write name="preferences" property="value(propertyId)"  ignore="true"/>']);">删&nbsp&nbsp&nbsp除</button></logic:equal><br/>
</td>
</tr>
</table>
<script type="text/javascript" language="javascript">
var oValue<bean:write name='javax.portlet.id'/> = '';
<logic:iterate id="selectedProp" name="selectedProps">
oValue<bean:write name='javax.portlet.id'/> = oValue<bean:write name='javax.portlet.id'/> + '<bean:write name="selectedProp" property="ID" ignore="true"/>'+ '*' ;
</logic:iterate>
var aForm = document.getElementById("addNodeButton").form;
var fun<bean:write name='javax.portlet.id'/>=function(){
		if(aForm['<bean:write name="preferences" property="value(propertyId)" ignore="true" />'].options[0]!=null){
			selectAll(aForm['<bean:write name="preferences" property="value(propertyId)" ignore="true" />']);
		}else{
			addOption(aForm['<bean:write name="preferences" property="value(propertyId)" ignore="true" />'],"clear","");	
			selectAll(aForm['<bean:write name="preferences" property="value(propertyId)" ignore="true" />']);
		}
	};
aForm.attachEvent("onsubmit",fun<bean:write name='javax.portlet.id'/>);
</script>
