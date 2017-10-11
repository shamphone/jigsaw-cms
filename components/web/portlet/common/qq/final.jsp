<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib
	uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:present name="qqNum">
<bean:define id="qqNum" name="qqNum" type="java.lang.String" />
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<div class='<bean:write name="preferences" property="value(style)"/>'>	
<a target="_blank" href="http://wpa.qq.com/msgrd?v=1&uin=<%=qqNum %>&site=qq&menu=yes"><img	src="<%if(!preferences.getValue("defaultImage", "").equals("")) {%><bean:write name="preferences" property="value(defaultImage)"/><%}else{ %>http://wpa.qq.com/pa?p=2:<%=qqNum %>:41<%} %>" 
width="<bean:write name="preferences" property="value(width)"/>" height="<bean:write name="preferences" property="value(height)"/>" align="<bean:write name="preferences" property="value(align)"/>" border="<bean:write name="preferences" property="value(border)"/>" alt="<bean:write name="preferences" property="value(text)" ignore="true"/>"	title="<bean:write name="preferences" property="value(text)" ignore="true"/>"></a>
</div></logic:present>
<logic:notPresent name="qqNum">
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<div class='<bean:write name="preferences" property="value(style)"/>'>	
<a target="_self" href="#"><img style="filter:gray" src="<%if(!preferences.getValue("defaultImage", "").equals("")) {%><bean:write name="preferences" property="value(defaultImage)"/><%}%>" 
width="<bean:write name="preferences" property="value(width)"/>" height="<bean:write name="preferences" property="value(height)"/>" align="<bean:write name="preferences" property="value(align)"/>" border="<bean:write name="preferences" property="value(border)"/>" alt="<bean:write name="preferences" property="value(text)" ignore="true"/>"	title="<bean:write name="preferences" property="value(text)" ignore="true"/>"></a>
</div></logic:notPresent>
