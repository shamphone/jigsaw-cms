<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<img <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(width)">width="<bean:write name="preferences" property="value(width)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(height)">height="<bean:write name="preferences" property="value(height)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(align)">align="<bean:write name="preferences" property="value(align)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(border)">border="<bean:write name="preferences" property="value(border)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(alt)">alt="<bean:write name="preferences" property="value(alt)"/>"</logic:notEmpty> src="<bean:write name="path" ignore="true"/>"/>