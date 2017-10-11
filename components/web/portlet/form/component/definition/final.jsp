<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<bean:define id="id" name="definition" property="ID" type="String"/>
<select <logic:equal value="on" name="preferences" property="value(multiple)">multiple="true"</logic:equal> <logic:equal value="on" name="preferences" property="value(submit)"> onchange="this.form.submit()"</logic:equal> <logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> name="definition" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>>
<logic:equal value="on" name="preferences" property="value(hasNull)">
  <option value="<bean:write name="preferences" property="value(nullValue)"/>"><bean:write name="preferences" property="value(nullText)"/></option>
</logic:equal>
<logic:iterate id="def" name="tree" property="nodes">
<option <logic:equal value="<%=id%>" name="def" property="node.ID"> selected="selected"</logic:equal> value="<bean:write name="def" property="node.ID"/>"><bean:write name="def" property="node.name"/></option>
</logic:iterate>
</select>