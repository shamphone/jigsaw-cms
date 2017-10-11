<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="value" name="value" type="String"/>
<select <logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>"  onblur="Validator.ValidateComponent(this, this.name)"  id="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>1" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
<logic:iterate id="option" name="preferences" property="values(values)">
<bean:define id="opt" name="option" type="String"/>
 <option <%if(value.equals(opt.split(",")[0])){out.print("selected='selected'");} %> value="<%=opt.split(",")[0]%>"><%=opt.split(",")[1]%></option>
</logic:iterate>
</select>
