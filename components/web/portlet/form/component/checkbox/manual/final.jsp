<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<input 
onclick="javascript:change(this)"
id="<bean:write name="javax.portlet.id"/>" type="checkbox"
	value="<bean:write name="preferences" property="value(value)"/>"
	name="<bean:write name="preferences" property="value(propertyId)"/>"
	<logic:equal value="true" name="preferences" property="value(checked)">checked="checked"</logic:equal> />
<logic:notEmpty name="preferences" property="value(label)">
	<label for="<bean:write name="javax.portlet.id"/>"><bean:write
		name="preferences" property="value(label)" filter="false" /></label>
</logic:notEmpty>
<script language="javascript">
function change(checkbox){
	if(!checkbox.checked){
		var c = document.createElement("input");
		c.value=" ";
		c.name="<bean:write name="preferences" property="value(propertyId)"/>";
		c.id = "<bean:write name="javax.portlet.id"/>h";
		c.type="hidden"
		checkbox.form.appendChild(c);
	}else{
		var delEl = document.getElementById("<bean:write name="javax.portlet.id"/>h");
		if(delEl){
			checkbox.form.removeChild(delEl);
		}
	}
}
</script>