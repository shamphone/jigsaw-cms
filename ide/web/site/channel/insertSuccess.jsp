<%@page contentType="text/xml; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<channel>
	<name><bean:write name="channel" property="name"/></name>
	<contextPath><bean:write name="channel" property="contextPath"/></contextPath>
	<type><bean:write name="channel" property="type"/></type>
	<displayName><bean:write name="channel" property="displayName"/></displayName>
</channel>
