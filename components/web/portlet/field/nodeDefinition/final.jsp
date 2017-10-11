<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="target" name="preferences" property="value(newWindows)" type="java.lang.String"/>
<logic:present name="com.fulong.longcon.nodeDefinition">
	<logic:empty name="preferences" property="value(channel)"><span id='<%=(String)request.getAttribute("javax.portlet.id")%>' <logic:notEmpty name="preferences" property="value(style)">class='<bean:write name="preferences" property="value(style)"/>'</logic:notEmpty>><bean:write name="com.fulong.longcon.nodeDefinition" property="name" ignore="true"/></span></logic:empty>
	<logic:notEmpty name="preferences" property="value(channel)">
		<a target="<%=target%>" href="<bean:write name="preferences" property="value(channel)"/>?definition=<bean:write name="com.fulong.longcon.nodeDefinition" property="ID" ignore="true"/>&parentDefinition=<bean:write name="com.fulong.longcon.nodeDefinition" property="superDefinition.ID" ignore="true"/>&definitionName=<bean:write name="com.fulong.longcon.nodeDefinition" property="name" ignore="true"/>" <logic:notEmpty name="preferences" property="value(style)">class='<bean:write name="preferences" property="value(style)"/>'</logic:notEmpty>><bean:write name="com.fulong.longcon.nodeDefinition" property="name" ignore="true"/></a>
	</logic:notEmpty>
</logic:present>
<logic:notPresent name="com.fulong.longcon.nodeDefinition">内容分类名称</logic:notPresent>