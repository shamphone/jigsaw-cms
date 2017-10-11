<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<bean:define id="viewPropertyId" name="preferences" property="value(viewPropertyId)" type="java.lang.String"/>
<table cellpadding="0" cellspacing="0" border="0" >
<tr><td>
<select multiple="multiple" <logic:notEmpty name="preferences" property="value(listStyle)">class="<bean:write name="preferences" property="value(listStyle)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)"/>" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>>
  <logic:iterate id="value" name="values">
    <option value="<bean:write name="value" property="ID"/>"><cms:node name="value" propertyName="<%=viewPropertyId%>"/></option>
  </logic:iterate>
</select>
</td>
<td  valign="top">
<!--<button id="addNodeButton" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> onclick="nodeSelect.addFixNode('<bean:write name="parentNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(propertyId)" />',this.form['<bean:write name="preferences" property="value(propertyId)" />'],'<bean:write name="preferences" property="value(viewPropertyId)" />')">增加</button>-->
<button type="button" id="addNodeButton" <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> onclick="nodeSelect.createFixNode('<bean:write name="parentNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(propertyId)" />','<bean:write name="preferences" property="value(category)" />',this.form['<bean:write name="preferences" property="value(propertyId)" />'],'<bean:write name="preferences" property="value(viewPropertyId)" />')">增加</button><br/>
<logic:equal value="on"  name="preferences" property="value(hasModify)"><button type="button" onclick="nodeSelect.modifyNode(this.form['<bean:write name="preferences" property="value(propertyId)" />'],'<bean:write name="preferences" property="value(viewPropertyId)" />')">修改</button></logic:equal><br/>
<logic:equal value="on"  name="preferences" property="value(hasUp)"><button type="button" onclick="nodeSelect.upShiftNode('<bean:write name="parentNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(propertyId)" />',this.form['<bean:write name="preferences" property="value(propertyId)" />'])">上移</button></logic:equal><br/>
<logic:equal value="on"  name="preferences" property="value(hasDown)"><button type="button" onclick="nodeSelect.downLowerNode('<bean:write name="parentNode" property="ID" ignore="true"/>','<bean:write name="preferences" property="value(propertyId)" />',this.form['<bean:write name="preferences" property="value(propertyId)" />'])">下移</button></logic:equal><br/>
<button type="button" onclick="nodeSelect.delFixNode(this.form['<bean:write name="preferences" property="value(propertyId)" />'])">删除</button>
</td>
</tr>
</table>
<script type="text/javascript" language="javascript">
var aForm = document.getElementById("addNodeButton").form;
var fun<bean:write name='javax.portlet.id'/>=function(){selectAll(aForm['<bean:write name="preferences" property="value(propertyId)" />'])};
if(aForm.attachEvent){
	aForm.attachEvent("onsubmit",fun<bean:write name='javax.portlet.id'/>);
}else{
	aForm.addEventListener("onsubmit",fun<bean:write name='javax.portlet.id'/>);
}
</script>