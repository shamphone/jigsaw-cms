<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<bean:define id="linkDate" name="linkDate"/>
<logic:equal value="item"  name="preferences" property="value(type)">
<logic:notEmpty name="date"><span class='<bean:write name="preferences" property="value(table-style)" ignore="true"/>'><bean:write name="date"/></span></logic:notEmpty>
<logic:empty name="date"><span class='<bean:write name="preferences" property="value(table-style)" ignore="true"/>'><script type="text/javascript">_today.render('<%=linkDate%>','<bean:write name="preferences" property="value(dateFormat)"/>')</script></span></logic:empty>
</logic:equal>
<logic:equal value="link"  name="preferences" property="value(type)">
<bean:define id="path" name="preferences" property="value(channel)" type="java.lang.String"/>
<logic:equal value="false"  name="preferences" property="value(openInNewWindow)">
<logic:notEmpty name="date"><a href="<%=path%>?dateParameter=<%=linkDate%>" <logic:notEmpty name="preferences" property="value(table-style)">class="<bean:write name="preferences" property="value(table-style)"/>"</logic:notEmpty>><bean:write name="date"/></a></logic:notEmpty>
<logic:empty name="date"><a href="<%=path%>?dateParameter=<%=linkDate%>" <logic:notEmpty name="preferences" property="value(table-style)">class="<bean:write name="preferences" property="value(table-style)"/>"</logic:notEmpty>><script type="text/javascript">_today.render('<%=linkDate%>','<bean:write name="preferences" property="value(dateFormat)"/>')</script></a></logic:empty>
</logic:equal>
<logic:notEqual value="false"  name="preferences" property="value(openInNewWindow)">
<logic:notEmpty name="date"><a href="<%=path%>?dateParameter=<%=linkDate%>" target="_blank" <logic:notEmpty name="preferences" property="value(table-style)">class="<bean:write name="preferences" property="value(table-style)"/>"</logic:notEmpty>><bean:write name="date"/></a></logic:notEmpty>
<logic:empty name="date"><a href="<%=path%>?dateParameter=<%=linkDate%>" target="_blank" <logic:notEmpty name="preferences" property="value(table-style)">class="<bean:write name="preferences" property="value(table-style)"/>"</logic:notEmpty>><script type="text/javascript">_today.render('<%=linkDate%>','<bean:write name="preferences" property="value(dateFormat)"/>')</script></a></logic:empty>
</logic:notEqual>
</logic:equal>

