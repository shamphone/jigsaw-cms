<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table<logic:notEmpty  name="preferences" property="value(noContentStyle)"> class="<bean:write name="preferences" property="value(noContentStyle)" ignore="true"/>"</logic:notEmpty>>
<tr>
<td><bean:write name="preferences" property="value(noContentWord)" ignore="true"/></td>
</tr>
</table>