<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:present name="com.fulong.longcon.nodeDefinition">
	<logic:empty name="preferences" property="value(channel)"><span <logic:notEmpty name="preferences" property="value(style)">class='<bean:write name="preferences" property="value(style)"/>'</logic:notEmpty>><bean:write name="com.fulong.longcon.nodeDefinition" property="name" ignore="true"/></span></logic:empty>
	<logic:notEmpty name="preferences" property="value(channel)">
		<a href="javascript:void(0)" <logic:notEmpty name="preferences" property="value(style)">class='<bean:write name="preferences" property="value(style)"/>'</logic:notEmpty>><bean:write name="com.fulong.longcon.nodeDefinition" property="name" ignore="true"/></a>
	</logic:notEmpty>
</logic:present>
<logic:notPresent name="com.fulong.longcon.nodeDefinition">内容分类名称</logic:notPresent>
