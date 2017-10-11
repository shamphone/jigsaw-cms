<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<logic:notEmpty name="date"><bean:write name="date"/></logic:notEmpty>
<logic:empty name="date">
<logic:equal value="shortDate" name="preferences" property="value(dateFormat)">
三月十四日
</logic:equal>
<logic:equal value="fullDate" name="preferences" property="value(dateFormat)">
二○○一年三月十四日
</logic:equal>
</logic:empty>


