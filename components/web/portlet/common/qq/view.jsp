<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<bean:define id="qqNum" type="java.lang.String" value="123456"/>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<div class='<bean:write name="preferences" property="value(style)"/>'>
<img src="<%if(!preferences.getValue("defaultImage", "").equals("")) {%><bean:write name="preferences" property="value(defaultImage)"/><%}else{ %>http://wpa.qq.com/pa?p=2:<%=qqNum %>:41<%} %>" 
width="<bean:write name="preferences" property="value(width)"/>" height="<bean:write name="preferences" property="value(height)"/>" align="<bean:write name="preferences" property="value(align)"/>" border="<bean:write name="preferences" property="value(border)"/>" alt="<bean:write name="preferences" property="value(text)" ignore="true"/>"	title="<bean:write name="preferences" property="value(text)" ignore="true"/>">
</div>
